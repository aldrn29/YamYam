package com.example.recipe

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class RecipeSource(
    var img : String = "",
    var name : String = "",
    var description: String = ""
) {
    @Exclude
    fun toMap() : Map<String, Any?> {
        return mapOf(
            "img" to img,
            "name" to name,
            "description" to description
        )
    }
}
