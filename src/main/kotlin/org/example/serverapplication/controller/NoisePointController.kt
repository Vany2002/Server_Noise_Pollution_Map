package org.example.serverapplication.controller

import org.example.serverapplication.domain.NoisePoint
import org.example.serverapplication.service.NoisePointService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus

@RestController
@RequestMapping("/noise-points")
class NoisePointController(private val noisePointService: NoisePointService) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(NoisePointController::class.java)
    }

    @PostMapping
    fun addNoisePoint(@RequestBody noisePointData: NoisePointData): ResponseEntity<Any> {
        logger.info("Получен запрос на добавление точки шума: {}", noisePointData)
        return try {
            noisePointService.addNoisePoint(noisePointData)
            logger.info("Точка шума успешно добавлена")
            ResponseEntity.ok().body(mapOf("message" to "Точка шума успешно добавлена"))
        } catch (e: Exception) {
            logger.error("Ошибка при добавлении точки шума: {}", e.message)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapOf("error" to "Ошибка при добавлении точки шума"))
        }
    }

    @GetMapping("/sync")
    fun syncData(@RequestParam("lastSyncTime") lastSyncTime: Long): ResponseEntity<List<NoisePoint>> {
        val updates = noisePointService.getUpdatesSince(lastSyncTime)
        return ResponseEntity.ok(updates)
    }

    data class NoisePointData(
        val nameRegion: String,
        val latitude: Double,
        val longitude: Double,
        val volume: Double,
        val reason: String
    )
}