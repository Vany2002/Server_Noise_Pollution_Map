package org.example.serverapplication.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "quiet_point")
class QuietPoint() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name = "name_region")
    var nameRegion: String = ""

    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var volume: Double = 0.0
    var reason: String = ""

    @Column(name = "date_point")
    var datePoint: LocalDateTime = LocalDateTime.now()

    constructor(nameRegion: String, latitude: Double, longitude: Double, volume: Double, reason: String, datePoint: LocalDateTime) : this() {
        this.nameRegion = nameRegion
        this.latitude = latitude
        this.longitude = longitude
        this.volume = volume
        this.reason = reason
        this.datePoint = datePoint
    }
}