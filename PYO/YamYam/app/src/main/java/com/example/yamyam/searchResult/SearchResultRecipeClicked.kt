package com.example.yamyam.searchResult

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_recipe.*
import com.example.recipe.RecipeSource
import com.example.yamyam.R
import com.squareup.picasso.Picasso

/* 12.10 검색 결과가 클릭될경우 나타나는 액티비티

 */
class SearchResultRecipeClicked : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

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