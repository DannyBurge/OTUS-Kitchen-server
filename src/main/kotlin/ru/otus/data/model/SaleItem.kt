package ru.otus.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class SaleItem(
    @JsonProperty(value = "id") override var id: Int? = null,
): RepositoryItem(id)