package ru.otus.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.otus.OtusKitchenApp
import ru.otus.data.AuthRepository
import ru.otus.data.model.ValidateKey
import ru.otus.service.AuthRepositoryService

@RestController
@RequestMapping("/auth")
class AuthController(private val authRepositoryService: AuthRepositoryService) : AuthRepository {

    @GetMapping
    override fun getApiKey(@RequestParam("key") key: String): ValidateKey {
        OtusKitchenApp.sleepRandomTime()
        return authRepositoryService.getApiKey(key)
    }
}