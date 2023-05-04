package ru.otus.service

import org.springframework.stereotype.Service
import ru.otus.data.*
import ru.otus.data.model.BalanceItem
import ru.otus.data.model.OrderItem
import ru.otus.data.model.SaleItem

@Service
class SaleService(
    private val saleRepositoryMemory: SaleRepositoryMemory
) : SaleRepository {
    override fun getSaleList(key: String): MutableList<SaleItem> {
        return saleRepositoryMemory.getSaleList(key)
    }
}
