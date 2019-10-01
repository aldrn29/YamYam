package com.example.yamyam

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_input_material_activity.*

class RecipeSerachActivity : AppCompatActivity(){

    var recipeList = arrayListOf<RecipeData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_search)


        val searchIntent = Intent(this, MainActivity::class.java)

        addButton.setOnClickListener {
            finish()
        }
    }
}