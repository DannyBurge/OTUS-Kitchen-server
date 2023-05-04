package ru.otus.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.otus.OtusKitchenApp
import ru.otus.data.SaleRepository
import ru.otus.data.model.SaleItem
import ru.otus.service.SaleService

@RestController
@RequestMapping("/sale")
class SaleController(private val saleService: SaleService) : SaleRepository {
    @GetMapping("/discount")
    override fun getSaleList(
        @RequestParam("key") key: String,
    ): MutableList<SaleItem> {
        OtusKitchenApp.sleepRandomTime()
        return saleService.getSaleList(key)
    }

}