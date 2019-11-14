package com.example.recipe

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class RecipeSource(
    var title: String= "",
    var img : String = "",
    var name : String = ""
)
