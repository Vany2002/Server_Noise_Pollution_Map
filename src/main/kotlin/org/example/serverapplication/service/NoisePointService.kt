package org.example.serverapplication.service

import org.example.serverapplication.controller.NoisePointController
import org.example.serverapplication.domain.NoisePoint
import org.example.serverapplication.repository.NoisePointRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class NoisePointService(private val noisePointRepository: NoisePointRepository) {
    fun addNoisePoint(noisePointData: NoisePointController.NoisePointData) {
        // Проверка наличия точки шума с такими же координатами
        val existingPoint = noisePointRepository.findByLatitudeAndLongitude(
            noisePointData.latitude, noisePointData.longitude
        )
        if (existingPoint == null) {
            // Если точка не найдена, создаем новую
            val newNoisePoint = NoisePoint(
                nameRegion = noisePointData.nameRegion,
                latitude = noisePointData.latitude,
                longitude = noisePointData.longitude,
                volume = noisePointData.volume,
                reason = noisePointData.reason,
                datePoint = LocalDateTime.now()
            )
            noisePointRepository.save(newNoisePoint)
        } else {
            // Если точка найдена, обновляем существующую
            existingPoint.volume = noisePointData.volume
            existingPoint.reason = noisePointData.reason
            existingPoint.datePoint = LocalDateTime.now()
            noisePointRepository.save(existingPoint)
        }
    }

    fun getUpdatesSince(lastSyncTime: Long): List<NoisePoint> {
        return noisePointRepository.findAllByDatePointAfter(LocalDateTime.ofEpochSecond(lastSyncTime, 0, ZoneOffset.UTC))
    }
}