package com.example.recipe

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yamyam.R
import com.google.firebase.database.DatabaseReference

class RecipeFragment : Fragment() {

    var recipeList = arrayListOf<RecipeSource>()
    lateinit var recyclerView1: RecyclerView
    lateinit var dataBaseRef : DatabaseReference


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        //start recipe value event listener
//        val recipeListener = object : ValueEventListener {
//            override fun onDataChange(p0: DataSnapshot) {
//                //get post object and use the values to update the UI
//                val recipe = p0.getValue(RecipeSource::class.java)
//                //start exclude
//                recipe?.let {
//                    img.text = it.img
//                }
//            }
//
//        }

        var viewInflater = inflater.inflate(R.layout.fragment_recipe_list, container, false)

//        dataBaseRef = FirebaseDatabase.getInstance().getReference().child("recipes")
//        recipeList.add(RecipeSource("Hamburger", "hamburger","Hamburger"/*,"재료배열","요리법"*/))
//        recipeList.add(RecipeSource("Lazania", "lazania", "Lazania"))


        recyclerView1 = viewInflater.findViewById(R.id.searchView)as RecyclerView
        recyclerView1.layoutManager = LinearLayoutManager(requireContext())
        recyclerView1.adapter = RecipeListAdapter(requireContext(), recipeList,{recipeSource: RecipeSource ->  itemClicked()})

        return viewInflater
    }
    private fun itemClicked(){
        val intent = Intent(activity, Recipe::class.java)
        startActivity(intent)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editBtn : Button = view.findViewById(R.id.editBtn)
        editBtn.setOnClickListener {
            val editIntent = Intent(activity, EditRecipe::class.java)
//            startActivityForResult(editIntent,1)
            startActivity(editIntent)
        }
    }






}
