package com.example.recipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yamyam.R
import kotlinx.android.synthetic.main.activity_recipe_search.*

//fragment에 맞게 변경할것
class RecipeSerachActivity : AppCompatActivity(){

    var recipeList = arrayListOf<RecipeSource>(

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_search)

        val recipeListAdapter = RecipeListAdapter(this, recipeList)
        recipeView.adapter = recipeListAdapter

        val lm = LinearLayoutManager(this)
        recipeView.layoutManager = lm
        recipeView.setHasFixedSize(true)

    }

}
