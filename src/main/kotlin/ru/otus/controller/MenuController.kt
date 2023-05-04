package ru.otus.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.otus.OtusKitchenApp
import ru.otus.data.MenuRepository
import ru.otus.data.model.CategoryItem
import ru.otus.data.model.MenuItem
import ru.otus.service.MenuService

@RestController
@RequestMapping("/menu")
class MenuController(private val menuService: MenuService) : MenuRepository {
    @GetMapping("/categories")
    override fun getCategoryList(
        @RequestParam("key") key: String,
    ): MutableList<CategoryItem> {
        OtusKitchenApp.sleepRandomTime()
        return menuService.getCategoryList(key,)
    }

    @GetMapping("/items")
    override fun getMenuList(
        @RequestParam("key") key: String,
        @RequestParam("id_list") idList: String?
    ): MutableList<MenuItem> {
        OtusKitchenApp.sleepRandomTime()
        return menuService.getMenuList(key,idList)
    }
}