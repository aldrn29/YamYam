package com.example.recipe

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yamyam.R

class RecipeFragment : Fragment() {

//    var recipeAdapter : RecipeListAdapter? = null
    var recipeList = arrayListOf<RecipeSource>()
    lateinit var recyclerView1: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        var viewInflater = inflater.inflate(R.layout.fragment_recipe_list, container, false)
        recipeList.add(RecipeSource("Hamburger","hamburger"/*,"재료배열","요리법"*/))
        recipeList.add(RecipeSource("Lazania", "lazania"))

        recyclerView1 = viewInflater.findViewById(R.id.searchView)as RecyclerView
        recyclerView1.layoutManager = LinearLayoutManager(requireContext())
        recyclerView1.adapter = RecipeListAdapter(requireContext(), recipeList,{recipeSource: RecipeSource ->  itemClicked()})

        return viewInflater
    }
    private fun itemClicked(){
        val intent = Intent(requireContext(), Recipe::class.java)
        startActivity(intent)
    }


}
