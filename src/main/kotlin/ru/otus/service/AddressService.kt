package ru.otus.service

import org.springframework.stereotype.Service
import ru.otus.data.AddressRepository
import ru.otus.data.AddressRepositoryMemory
import ru.otus.data.model.AddressItem

@Service
class AddressService(
    private val addressRepositoryMemory: AddressRepositoryMemory
) : AddressRepository {
    override fun getAddressList(key: String, ): MutableList<AddressItem> {
        return addressRepositoryMemory.getAddressList(key)
    }

    override fun addAddress(key: String, address: AddressItem): AddressItem {
        return addressRepositoryMemory.addAddress(key,address)
    }

    override fun updateAddress(key: String, address: AddressItem): AddressItem {
        return addressRepositoryMemory.updateAddress(key,address)
    }

    override fun deleteAddress(key: String, id: Int) {
        addressRepositoryMemory.deleteAddress(key,id)
    }


}