package ru.otus.controller

import org.springframework.web.bind.annotation.*
import ru.otus.OtusKitchenApp
import ru.otus.data.AddressRepository
import ru.otus.data.model.AddressItem
import ru.otus.service.AddressService

@RestController
@RequestMapping("addresses")
class AddressController(private val addressService: AddressService) : AddressRepository {
    @GetMapping
    override fun getAddressList(
        @RequestParam("key") key: String,
    ): MutableList<AddressItem> {
        OtusKitchenApp.sleepRandomTime()
        return addressService.getAddressList(key,)
    }

    @PostMapping
    override fun addAddress(
        @RequestParam("key") key: String,
        @RequestBody address: AddressItem
    ): AddressItem {
        OtusKitchenApp.sleepRandomTime()
        return addressService.addAddress(key,address)
    }

    @PutMapping
    override fun updateAddress(
        @RequestParam("key") key: String,
        @RequestBody address: AddressItem
    ): AddressItem {
        OtusKitchenApp.sleepRandomTime()
        return addressService.updateAddress(key,address)
    }

    @DeleteMapping
    override fun deleteAddress(
        @RequestParam("key") key: String,
        @RequestParam("id") id: Int
    ) {
        OtusKitchenApp.sleepRandomTime()
        addressService.deleteAddress(key,id)
    }

}