package ru.otus.service

import org.springframework.stereotype.Service
import ru.otus.data.BalanceRepository
import ru.otus.data.BalanceRepositoryMemory
import ru.otus.data.model.BalanceItem
import ru.otus.data.model.ValidateItem

@Service
class BalanceService(
    private val balanceRepositoryMemory: BalanceRepositoryMemory
) : BalanceRepository {

    override fun getBalanceList(key: String, isLast: Boolean): MutableList<BalanceItem> {
        return balanceRepositoryMemory.getBalanceList(key, isLast)
    }

    override fun addBalance(key: String, balanceItem: BalanceItem): BalanceItem {
        return balanceRepositoryMemory.addBalance(key, balanceItem)
    }

    override fun validateCode(key: String, code: String): ValidateItem {
        return balanceRepositoryMemory.validateCode(key, code)
    }
}