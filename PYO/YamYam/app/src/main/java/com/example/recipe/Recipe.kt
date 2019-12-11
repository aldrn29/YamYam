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

//        val bundle: Bundle? = intent.extras
//        val Image = bundle!!.getString("Firebase_Image")
//        val Title = bundle!!.getString("Firebase_Title")
//        val Materials = bundle!!.getString("Firebase_Materials")
//        val Description = bundle!!.getString("Firebase_Description")
//
//
////      RecyclerView Item의 pos에 따라 바뀌여야함
//
//        recipeName.text = Title
//        Picasso.get().load(Image).into(recipeImg)
//        materialArr.text = Materials
//        cookingDescription.text = Description

//어댑터에서 넘긴 인텐트로부터 레시피 정보를 가져옴
        val clickResultRecipe = RecipeSource(intent.getStringExtra("description"),intent.getStringExtra("imageUri"), intent.getStringArrayListExtra("materialsList"), intent.getStringExtra("name"))

        //ArrayList -> String 으로 바꾸는 곳
        var materialsListTextSum : String = ""
        var position : Int = 0
        for(i in clickResultRecipe.materials!!){
            if(position != 0) {
                materialsListTextSum += ", "
            }
            materialsListTextSum += i
            position++
        }

        materialArr.setText(materialsListTextSum)
        recipeName.setText(clickResultRecipe.name)
        cookingDescription.setText(clickResultRecipe.description)
        Picasso.get().load(clickResultRecipe.imageUri).into(recipeImg)

    }

}