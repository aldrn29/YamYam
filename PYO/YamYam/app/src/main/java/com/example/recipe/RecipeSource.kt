package com.example.recipe

import com.google.firebase.database.Exclude


class RecipeSource{
    var imageUri : String? = null
    var name : String? = null
    var description: String? = null
    var material : List<String>? = null

    constructor():this("","","", listOf("")){}

    constructor(imageUri: String, name: String, description: String, material: List<String>) {
        this.imageUri = imageUri
        this.name = name
        this.description = description
        this.material = material
    }

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "imageUri" to imageUri,
            "name" to name,
            "description" to description,
            "materialsList" to material
        )
    }

}


