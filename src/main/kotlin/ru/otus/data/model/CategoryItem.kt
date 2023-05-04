package ru.otus.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CategoryItem(
    @JsonProperty(value = "id") override var id: Int? = null,
    @JsonProperty(value = "name") val name: String,
): RepositoryItem(id)