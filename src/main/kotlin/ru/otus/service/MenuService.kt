package ru.otus.service

import org.springframework.stereotype.Service
import ru.otus.OtusKitchenApp
import ru.otus.data.MenuRepository
import ru.otus.data.MenuRepositoryMemory
import ru.otus.data.model.CategoryItem
import ru.otus.data.model.MenuItem

@Service
class MenuService(
    private val menuRepositoryMemory: MenuRepositoryMemory
) : MenuRepository {
    override fun getCategoryList(key: String, ): MutableList<CategoryItem> {
        OtusKitchenApp.sleepRandomTime()
        return menuRepositoryMemory.getCategoryList(key,)
    }

    override fun getMenuList(key: String, idList: String?): MutableList<MenuItem> {
        OtusKitchenApp.sleepRandomTime()
        return menuRepositoryMemory.getMenuList(key,idList)
    }
}