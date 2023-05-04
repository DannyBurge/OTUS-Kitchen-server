package ru.otus.data

import org.springframework.stereotype.Component
import ru.otus.data.model.CategoryItem
import ru.otus.data.model.MenuItem
import ru.otus.OtusKitchenApp.Companion.dataBase

@Component
class MenuRepositoryMemory : BaseRepository(), MenuRepository {


    override fun getCategoryList(key: String, ): MutableList<CategoryItem> {
        return dataBase.getCategoryList(key,)
    }

    override fun getMenuList(key: String, idList: String?): MutableList<MenuItem> {
        return dataBase.getMenuList(key,idList)
    }
}
