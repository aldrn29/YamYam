package com.example.yamyam

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class RecipeSerachActivity : AppCompatActivity(){

    var recipeList = arrayOf(RecipeData(name = "Spaghetti"), RecipeData(name = "Gimbab"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_search)

        val list : ListView = findViewById(R.id.searchListView)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, recipeList)

        list.adapter = adapter

    }
}
