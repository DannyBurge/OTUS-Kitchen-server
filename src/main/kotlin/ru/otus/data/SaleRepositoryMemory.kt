package ru.otus.data

import org.springframework.stereotype.Component
import ru.otus.OtusKitchenApp.Companion.dataBase
import ru.otus.data.model.SaleItem

@Component
class SaleRepositoryMemory : BaseRepository(), SaleRepository {

    override fun getSaleList(key: String): MutableList<SaleItem> {
        return dataBase.getSaleList(key,)
    }
}
