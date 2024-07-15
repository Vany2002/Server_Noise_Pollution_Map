package org.example.serverapplication.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class HomeController {
    @GetMapping("/")
    fun home(): String {
        return "Добро пожаловать в мое Spring Boot приложение!"
    }
}