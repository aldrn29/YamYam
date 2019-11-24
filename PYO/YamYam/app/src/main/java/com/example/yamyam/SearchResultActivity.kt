package com.example.yamyam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipe.RecipeFragment
import com.example.recipe.RecipeSource
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_search_result.*

class SearchResultActivity : AppCompatActivity() {

    //lateinit var recipeDB: DatabaseReference
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("message")
    var recipeList = ArrayList<SearchResultRecipe>()
    var SearchResultAdapter : SearchResultRecipeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        var MaterialNameArrayToSearch = intent.getStringArrayListExtra("MaterialNameArrayToSearch")
        //Toast.makeText(applicationContext, "${MaterialNameArrayToSearch}", Toast.LENGTH_SHORT).show()

        SearchResultAdapter = SearchResultRecipeAdapter(this, recipeList)
        SearchResultRecyclerview.adapter = SearchResultAdapter

        //recipeDB = FirebaseDatabase.getInstance().reference
        //recipeDB.child("test").push().setValue("abc")





        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                Toast.makeText(applicationContext, "$value", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

    }


}
