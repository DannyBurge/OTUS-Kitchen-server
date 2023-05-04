package ru.otus.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class BalanceItem(
    @JsonProperty(value = "id") override var id: Int? = null,
    @JsonProperty(value = "order_id") val orderId: Int,
    @JsonProperty(value = "date") val date: String,
    @JsonProperty(value = "amount_added") val amountAdded: Int,
    @JsonProperty(value = "amount") var amount: Int? = null,
): RepositoryItem(id)