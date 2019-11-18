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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_recipe_item.*

class RecipeFragment : Fragment() {

    lateinit var recyclerView1: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        var recipeList = arrayListOf<RecipeSource>()

        val dataBaseRef = FirebaseDatabase.getInstance().getReference().child("recipes")
        dataBaseRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                throw databaseError.toException()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (recipeSnapshot in dataSnapshot.children) {
                    val recipeSource = recipeSnapshot.getValue(RecipeSource::class.java)

                    recipeSource?.let {
//                        recipeItemImg.text = it.img
                        recipeItemName.text = it.name
                    }

                    recipeList.add(recipeSource!!)
                }
            }
        })


        var viewInflater = inflater.inflate(R.layout.fragment_recipe_list, container, false)

//        recipeList.add(RecipeSource("Hamburger", "hamburger","Hamburger"/*,"재료배열","요리법"*/))

        recyclerView1 = viewInflater.findViewById(R.id.searchView)as RecyclerView
        recyclerView1.layoutManager = LinearLayoutManager(requireContext())
        recyclerView1.adapter = RecipeListAdapter(requireContext(), recipeList,{recipeSource: RecipeSource ->  itemClicked()})

        return viewInflater
    }
    private fun itemClicked(){
        val intent = Intent(activity, RecipeActivity::class.java)
        startActivity(intent)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editBtn : Button = view.findViewById(R.id.editBtn)
        editBtn.setOnClickListener {
            val editIntent = Intent(activity, EditRecipeActivity::class.java)
//            startActivityForResult(editIntent,1)
            startActivity(editIntent)
        }
    }


}
