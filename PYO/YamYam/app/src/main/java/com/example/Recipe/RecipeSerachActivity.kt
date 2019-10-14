package com.example.Recipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yamyam.R
import kotlinx.android.synthetic.main.activity_recipe_search.*

class RecipeSerachActivity : AppCompatActivity(){

    var recipeList = arrayListOf<Recipe>(
        Recipe("Hamburger", "hamburger"),
        Recipe("Lazania", "lazania")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_search)

        val recipeAdapter = RecipeListAdapter(this, recipeList)
        recipeListView.adapter = recipeAdapter
    }

}
