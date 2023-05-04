package ru.otus.controller

import ru.otus.data.model.BalanceItem
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.otus.OtusKitchenApp
import ru.otus.data.BalanceRepository
import ru.otus.data.model.ValidateItem
import ru.otus.service.BalanceService

@RestController
@RequestMapping("/balance")
class BalanceController(private val balanceService: BalanceService) : BalanceRepository {

    @PostMapping("/promo")
    override fun addBalance(
        @RequestParam("key") key: String,
        @RequestBody balanceItem: BalanceItem
    ): BalanceItem {
        OtusKitchenApp.sleepRandomTime()
        return balanceService.addBalance(key,balanceItem)
    }

    @GetMapping("/items")
    override fun getBalanceList(
        @RequestParam("key") key: String,
        @RequestParam("isLast") isLast: Boolean
    ): MutableList<BalanceItem> {
        OtusKitchenApp.sleepRandomTime()
        return balanceService.getBalanceList(key,isLast)
    }

    @GetMapping("/validate")
    override fun validateCode(
        @RequestParam("key") key: String,
        @RequestParam("code") code: String
    ): ValidateItem {
        OtusKitchenApp.sleepRandomTime()
        return balanceService.validateCode(key,code)
    }
}