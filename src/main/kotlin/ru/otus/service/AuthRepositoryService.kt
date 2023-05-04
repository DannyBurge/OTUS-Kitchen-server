package ru.otus.service

import org.springframework.stereotype.Service
import ru.otus.data.AuthRepository
import ru.otus.data.AuthRepositoryMemory
import ru.otus.data.model.ValidateKey

@Service
class AuthRepositoryService(private val authRepositoryMemory: AuthRepositoryMemory) : AuthRepository {


    override fun getApiKey(key: String): ValidateKey {
        return authRepositoryMemory.getApiKey(key)
    }

}