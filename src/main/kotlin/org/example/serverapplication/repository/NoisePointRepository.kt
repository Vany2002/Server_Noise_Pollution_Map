package org.example.serverapplication.repository

import org.example.serverapplication.domain.NoisePoint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface NoisePointRepository : JpaRepository<NoisePoint, Long> {
    fun findByLatitudeAndLongitude(latitude: Double, longitude: Double): NoisePoint?
    fun findAllByDatePointAfter(date: LocalDateTime): List<NoisePoint>
}