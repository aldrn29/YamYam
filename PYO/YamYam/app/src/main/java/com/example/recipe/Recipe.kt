package com.example.recipe

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.yamyam.R

class Recipe : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val recipeImg = findViewById<ImageView>(R.id.recipeImg)
        val name = findViewById<TextView>(R.id.recipeName)
        val materialArr = findViewById<TextView>(R.id.materialArr)
        val description = findViewById<TextView>(R.id.cookingDescription)

//      RecyclerView Item의 pos에 따라 바뀌여야함
        recipeImg.setImageResource(R.drawable.hamburger)
        name.setText("setText")
        materialArr.setText("Materials")
        description.setText("Description")


    }

}