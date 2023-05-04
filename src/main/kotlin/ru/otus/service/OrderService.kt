package ru.otus.service

import org.springframework.stereotype.Service
import ru.otus.data.OrderRepository
import ru.otus.data.OrderRepositoryMemory
import ru.otus.data.model.OrderItem

@Service
class OrderService(
    private val orderRepositoryMemory: OrderRepositoryMemory
) : OrderRepository {
    override fun getOrderList(key: String, ): MutableList<OrderItem> {
        return orderRepositoryMemory.getOrderList(key,)
    }

    override fun addOrder(key: String, orderItem: OrderItem): OrderItem {
        return orderRepositoryMemory.addOrder(key,orderItem)
    }
}