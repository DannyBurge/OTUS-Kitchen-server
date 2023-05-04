package ru.otus.data

import ru.otus.data.model.*
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseRepository {

    fun List<RepositoryItem>.nextId(): Int = this.maxByOrNull { it.id ?: 0 }?.id?.inc() ?: 1

    fun Date.toFullIsoDate(): String = format(FORMAT_FULL_ISO_DATE, this)

    companion object {
        private const val FORMAT_FULL_ISO_DATE = "dd.MM.yyyy HH:mm"

        private fun format(pattern: String, date: Date) =
            SimpleDateFormat(pattern).format(date)
    }
}

interface AuthRepository {
    fun getApiKey(key: String): ValidateKey
}

interface BalanceRepository {
    fun getBalanceList(key: String, isLast: Boolean = false): MutableList<BalanceItem>
    fun addBalance(key: String, balanceItem: BalanceItem): BalanceItem
    fun validateCode(key: String, code: String): ValidateItem
}

interface AddressRepository {
    fun getAddressList(key: String): MutableList<AddressItem>
    fun addAddress(key: String, address: AddressItem): AddressItem
    fun updateAddress(key: String, address: AddressItem): AddressItem
    fun deleteAddress(key: String, id: Int)
}

interface MenuRepository {
    fun getCategoryList(key: String): MutableList<CategoryItem>
    fun getMenuList(key: String, idList: String? = null): MutableList<MenuItem>
}

interface OrderRepository {
    fun getOrderList(key: String): MutableList<OrderItem>
    fun addOrder(key: String, orderItem: OrderItem): OrderItem
}

interface SaleRepository {
    fun getSaleList(key: String): MutableList<SaleItem>
}
