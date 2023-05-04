package ru.otus.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ValidateKey(
    @JsonProperty(value = "key") var key: String,
    @JsonProperty(value = "valid") val isValid: Boolean
)
