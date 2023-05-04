package ru.otus.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class AddressItem(
    @JsonProperty(value = "id") override var id: Int? = null,
    @JsonProperty(value = "name") val name: String,
    @JsonProperty(value = "address") val address: String,
): RepositoryItem(id)