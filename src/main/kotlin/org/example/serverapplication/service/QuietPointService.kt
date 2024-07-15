package org.example.serverapplication.service

import org.example.serverapplication.controller.QuietPointController
import org.example.serverapplication.domain.QuietPoint
import org.example.serverapplication.repository.QuietPointRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class QuietPointService(private val quietPointRepository: QuietPointRepository) {
    fun addQuietPoint(quietPointData: QuietPointController.QuietPointData) {
        // Проверка наличия точки шума с такими же координатами
        val existingPoint = quietPointRepository.findByLatitudeAndLongitude(
            quietPointData.latitude, quietPointData.longitude
        )
        if (existingPoint == null) {
            // Если точка не найдена, создаем новую
            val newQuietPoint = QuietPoint(
                nameRegion = quietPointData.nameRegion,
                latitude = quietPointData.latitude,
                longitude = quietPointData.longitude,
                volume = quietPointData.volume,
                reason = quietPointData.reason,
                datePoint = LocalDateTime.now()
            )
            quietPointRepository.save(newQuietPoint)
        } else {
            // Если точка найдена, обновляем существующую
            existingPoint.volume = quietPointData.volume
            existingPoint.reason = quietPointData.reason
            existingPoint.datePoint = LocalDateTime.now()
            quietPointRepository.save(existingPoint)
        }
    }

    fun getQuietUpdatesSince(lastSyncTime: Long): List<QuietPoint> {
        return quietPointRepository.findAllByDatePointAfter(LocalDateTime.ofEpochSecond(lastSyncTime, 0, ZoneOffset.UTC))
    }
}