package ru.otus.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class OrderItem(
    @JsonProperty(value = "id") override var id: Int? = null,
    @JsonProperty(value = "price") val price: Int,
    @JsonProperty(value = "tokens_amount") val tokensAmount: Int,
    @JsonProperty(value = "address") val address: String,
    @JsonProperty(value = "date") val date: String,
    @JsonProperty(value = "active") var isActive: Boolean,
    @JsonProperty(value = "positions") val positions: List<OrderPosition>,
) : RepositoryItem(id) {
    data class OrderPosition(
        @JsonProperty(value = "group_id") val groupId: Int,
        @JsonProperty(value = "size_id") val sizeId: Int,
        @JsonProperty(value = "count") val amount: Int,
    )
}