package ru.otus

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.otus.database.DataBase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

@SpringBootApplication
open class OtusKitchenApp {

    companion object {
        lateinit var dataBase: DataBase

        fun sleepRandomTime() {
            Thread.sleep((Math.random() * 1000).toLong())
        }

        private const val FORMAT_FULL_ISO_DATE = "dd.MM.yyyy HH:mm"
        fun getDateFromIsoString(stringDate: String?): Date {
            val result = SimpleDateFormat(
                FORMAT_FULL_ISO_DATE,
                Locale.forLanguageTag("ru")
            ).parse(stringDate ?: "")
            result?.let {
                return result
            } ?: run {
                return Date()
            }
        }
    }
}

fun main() {
    runApplication<OtusKitchenApp>()
    OtusKitchenApp.dataBase = DataBase()
}