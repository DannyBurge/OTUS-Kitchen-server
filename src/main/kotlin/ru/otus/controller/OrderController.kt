package ru.otus.controller

import org.springframework.web.bind.annotation.*
import ru.otus.OtusKitchenApp
import ru.otus.data.OrderRepository
import ru.otus.data.model.OrderItem
import ru.otus.service.OrderService

@RestController
@RequestMapping("/orders")
class OrderController(private val orderService: OrderService): OrderRepository {
    @GetMapping
    override fun getOrderList(
        @RequestParam("key") key: String,
    ): MutableList<OrderItem> {
        OtusKitchenApp.sleepRandomTime()
        return orderService.getOrderList(key,)
    }

    @PostMapping
    override fun addOrder(
        @RequestParam("key") key: String,
        @RequestBody orderItem: OrderItem): OrderItem {
        OtusKitchenApp.sleepRandomTime()
        return orderService.addOrder(key,orderItem)
    }

}