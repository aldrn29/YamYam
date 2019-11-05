package com.example.recipe

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yamyam.R
import kotlinx.android.synthetic.main.fragment_recipe.*

class RecipeFragment : Fragment() {

    var recipeAdapter : RecipeListAdapter? = null
    var recipeList = arrayListOf<RecipeSource>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        recipeList.add(RecipeSource("Hamburger", "hamburger"))
        recipeList.add(RecipeSource("Lazania", "lazania"))

        recipeAdapter = RecipeListAdapter(requireContext(), recipeList)
        recipeView.adapter = recipeAdapter

        val lm = LinearLayoutManager(activity)
        recipeView.layoutManager = lm
        recipeView.setHasFixedSize(true)


    }


}
