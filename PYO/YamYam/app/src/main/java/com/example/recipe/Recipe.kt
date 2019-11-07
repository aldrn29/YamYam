package com.example.recipe

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.yamyam.R

class Recipe : AppCompatActivity() {
    var materialArr = arrayListOf<RecipeSource>(

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val recipeImg = findViewById<ImageView>(R.id.recipeImg)
        val recipeName = findViewById<TextView>(R.id.recipeName)
        val materialArr = findViewById<TextView>(R.id.materialArr)
        val cookingDescription = findViewById<TextView>(R.id.cookingDescription)
    }

}