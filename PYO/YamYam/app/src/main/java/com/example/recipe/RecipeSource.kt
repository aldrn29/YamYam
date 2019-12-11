package com.example.recipe

import com.google.firebase.database.Exclude

/*
Recipe Activity와 RecipeFragment를 위해 Firebase DB에 담을 Data Model
 */
class RecipeSource{
    var imageUri : String? = null
    var name : String? = null
    var description: String? = null
    var materials : List<String>? = null
    var wish : Boolean = false

    constructor():this("","",listOf(),""){}

    constructor(description: String,
                imageUri: String,
                materials: List<String>,
                name: String,
                wish: Boolean = false) {
        this.description = description
        this.imageUri = imageUri
        this.materials = materials
        this.name = name
        this.wish = wish
    }

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "imageUri" to imageUri,
            "name" to name,
            "description" to description,
            "materialsList" to materials,
            "wish" to wish
        )
    }

}


