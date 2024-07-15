package org.example.serverapplication.controller

import org.example.serverapplication.domain.QuietPoint
import org.example.serverapplication.service.QuietPointService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/quiet-points")
class QuietPointController(private val quietPointService: QuietPointService) {

    @PostMapping
    fun addQuietPoint(@RequestBody quietPointData: QuietPointData): ResponseEntity<Any> {
        return try {
            quietPointService.addQuietPoint(quietPointData)
            ResponseEntity.ok().body(mapOf("message" to "Тихая точка успешно добавлена"))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapOf("error" to "Ошибка при добавлении тихой точки"))
        }
    }

    @GetMapping("/sync")
    fun syncQuietData(@RequestParam("lastSyncTime") lastSyncTime: Long): ResponseEntity<List<QuietPoint>> {
        val updates = quietPointService.getQuietUpdatesSince(lastSyncTime)
        return ResponseEntity.ok(updates)
    }

    data class QuietPointData(
        val nameRegion: String,
        val latitude: Double,
        val longitude: Double,
        val volume: Double,
        val reason: String
    )
}