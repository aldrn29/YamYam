package com.example.recipe

import com.google.firebase.database.Exclude


class RecipeSource{
    var img : String? = null
    var name : String? = null
    var description: String? = null

    constructor():this("","",""){}

    constructor(img: String, name: String, description: String) {
        this.img = img
        this.name = name
        this.description = description

    }

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "img" to img,
            "name" to name,
            "description" to description
        )
    }

}


