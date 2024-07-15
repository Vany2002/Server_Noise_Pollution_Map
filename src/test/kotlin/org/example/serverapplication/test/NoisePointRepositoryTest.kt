package org.example.serverapplication.test

import org.example.serverapplication.domain.NoisePoint
import org.example.serverapplication.repository.NoisePointRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@SpringBootTest(properties = ["spring.config.name=application-test"])
class NoisePointRepositoryTest(@Autowired val repository: NoisePointRepository) {

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    fun `when saving noise point, it should be retrievable from the database`() {
        val testDate = LocalDateTime.of(2024, 6, 29, 23, 49, 7)

        val noisePoint = NoisePoint().apply {
            nameRegion = "Test Region"
            latitude = 55.7558
            longitude = 37.6173
            volume = 70.0
            reason = "Test Reason"
            datePoint = testDate
        }

        val savedNoisePoint = repository.save(noisePoint)

        val found = repository.findById(savedNoisePoint.id)
        assertTrue(found.isPresent)
        assertEquals("Test Region", found.get().nameRegion)
        assertEquals(testDate, found.get().datePoint)
    }

    @Test
    fun `should find saved noise points`() {
        val testDate = LocalDateTime.of(2024, 6, 29, 23, 49, 7)

        val noisePoint = NoisePoint().apply {
            nameRegion = "Test Region"
            latitude = 55.7558
            longitude = 37.6173
            volume = 70.0
            reason = "Test Reason"
            datePoint = testDate
        }

        val savedNoisePoint = repository.save(noisePoint)

        val found = repository.findById(savedNoisePoint.id!!)
        assertTrue(found.isPresent)

        assertEquals(55.7558, found.get().latitude, "Latitude should match")
        assertEquals(37.6173, found.get().longitude, "Longitude should match")
        // Добавьте дополнительные проверки по необходимости
    }
}