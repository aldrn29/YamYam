package com.example.recipe

import com.google.firebase.database.Exclude


class RecipeSource{
    var img : String? = null
    var name : String? = null
    var description: String? = null
    var material : List<String>? = null

    constructor():this("","","", listOf("")){}

    constructor(img: String, name: String, description: String, material: List<String>) {
        this.img = img
        this.name = name
        this.description = description
        this.material = material
    }

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "img" to img,
            "name" to name,
            "description" to description,
            "materialsList" to material
        )
    }

}


