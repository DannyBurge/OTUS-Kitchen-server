package ru.otus.data

import org.springframework.stereotype.Component
import ru.otus.OtusKitchenApp.Companion.dataBase
import ru.otus.data.model.BalanceItem
import ru.otus.data.model.ValidateItem

@Component
class BalanceRepositoryMemory : BaseRepository(), BalanceRepository {

    override fun getBalanceList(key: String, isLast: Boolean): MutableList<BalanceItem> {
        return dataBase.getBalanceList(key,isLast)
    }

    override fun addBalance(key: String, balanceItem: BalanceItem): BalanceItem {
        balanceItem.id = dataBase.getBalanceList(key,true).nextId()
        return dataBase.addBalance(key,balanceItem)
    }

    override fun validateCode(key: String, code: String): ValidateItem {
        return dataBase.validateCode(key,code)
    }
}
