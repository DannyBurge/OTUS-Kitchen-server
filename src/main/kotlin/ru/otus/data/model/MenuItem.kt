package ru.otus.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class MenuItem(
    @JsonProperty(value = "group_id") override var id: Int? = null,
    @JsonProperty(value = "category_id") val categoryId: Int,
    @JsonProperty(value = "picture") val picture: String,
    @JsonProperty(value = "name") val name: String,
    @JsonProperty(value = "description") val description: String,
    @JsonProperty(value = "sub_items") val subItems: List<MenuSubItem>,
    @JsonProperty(value = "food_value") val foodValue: MenuItemFoodValue,
) : RepositoryItem(id) {

    data class MenuSubItem(
        @JsonProperty(value = "id") val id: Int,
        @JsonProperty(value = "price") val price: Int,
        @JsonProperty(value = "display_name") val displayName: String,
        @JsonProperty(value = "weight") val weight: Int,
    )

    data class MenuItemFoodValue(
        @JsonProperty(value = "ccal") val ccal: String,
        @JsonProperty(value = "proteins") val proteins: String,
        @JsonProperty(value = "fats") val fats: String,
        @JsonProperty(value = "carbohydrates") val carbohydrates: String,
    )
}