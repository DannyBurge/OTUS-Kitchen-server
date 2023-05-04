package ru.otus.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ValidateItem(
    @JsonProperty(value = "id") override var id: Int? = null,
    @JsonProperty(value = "code") val code: String,
    @JsonProperty(value = "validated") val validated: Boolean,
    @JsonProperty(value = "amount_added") val amountAdded: Int,
) : RepositoryItem(id)
