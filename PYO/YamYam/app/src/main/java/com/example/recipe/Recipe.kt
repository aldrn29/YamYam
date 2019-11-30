package com.example.recipe

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.yamyam.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_recipe.*

class Recipe : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val bundle: Bundle? = intent.extras
        val Image = bundle!!.getString("Firebase_Image")
        val Title = bundle!!.getString("Firebase_Title")
        val Materials = bundle!!.getString("Firebase_Materials")
        val Description = bundle!!.getString("Firebase_Description")


//      RecyclerView Item의 pos에 따라 바뀌여야함

        recipeName.text = Title
        Picasso.get().load(Image).into(recipeImg)
        materialArr.text = Materials
        cookingDescription.text = Description


    }

}