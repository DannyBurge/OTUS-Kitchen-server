package ru.otus.data

import org.springframework.stereotype.Component
import ru.otus.data.model.AddressItem
import ru.otus.data.model.BalanceItem
import java.util.Date
import ru.otus.OtusKitchenApp.Companion.dataBase

@Component
class AddressRepositoryMemory : BaseRepository(), AddressRepository {
    override fun getAddressList(key: String): MutableList<AddressItem> {
        return dataBase.getAddressList(key,)
    }

    override fun addAddress(key: String, address: AddressItem): AddressItem {
        address.id = dataBase.getAddressList(key,).nextId()
        dataBase.addAddress(key,address)
        return dataBase.getAddressList(key,).last()
    }

    override fun updateAddress(key: String, address: AddressItem): AddressItem {
        return dataBase.updateAddress(key,address)
    }

    override fun deleteAddress(key: String, id: Int) {
        dataBase.deleteAddress(key,id)
    }
}
