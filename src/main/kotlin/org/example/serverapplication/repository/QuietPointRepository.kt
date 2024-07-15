package org.example.serverapplication.repository

import org.example.serverapplication.domain.QuietPoint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface QuietPointRepository : JpaRepository<QuietPoint, Long> {
    fun findByLatitudeAndLongitude(latitude: Double, longitude: Double): QuietPoint?
    fun findAllByDatePointAfter(date: LocalDateTime): List<QuietPoint>
}