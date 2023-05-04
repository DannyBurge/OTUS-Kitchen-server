package ru.otus.data

import org.springframework.stereotype.Component
import ru.otus.OtusKitchenApp.Companion.dataBase
import ru.otus.data.model.ValidateKey

@Component
class AuthRepositoryMemory: BaseRepository(), AuthRepository {

    override fun getApiKey(key: String): ValidateKey {
        return dataBase.getApiKey(key)
    }
}