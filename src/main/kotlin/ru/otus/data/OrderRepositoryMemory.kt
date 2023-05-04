package ru.otus.data

import org.springframework.stereotype.Component
import ru.otus.data.model.BalanceItem
import ru.otus.data.model.CategoryItem
import ru.otus.data.model.MenuItem
import ru.otus.data.model.OrderItem
import java.util.Date
import ru.otus.OtusKitchenApp.Companion.dataBase

@Component
class OrderRepositoryMemory : BaseRepository(), OrderRepository {

    override fun getOrderList(key: String): MutableList<OrderItem> {
        return dataBase.getOrderList(key)
    }

    override fun addOrder(key: String, orderItem: OrderItem): OrderItem {
        orderItem.id = dataBase.getOrderList(key).nextId()
        dataBase.addBalance(
            key,
            BalanceItem(
                id = dataBase.getBalanceList(key,true).nextId(),
                orderId = orderItem.id!!,
                date = Date().toFullIsoDate(),
                amountAdded = orderItem.tokensAmount,
                amount = dataBase.getBalanceList(key,true).last().amount!! + orderItem.tokensAmount
            )
        )
        return dataBase.addOrder(key, orderItem)
    }

}
