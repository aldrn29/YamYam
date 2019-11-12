package com.example.recipe

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.yamyam.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditRecipe : AppCompatActivity() {
    //데이터베이스 인스턴스
    private lateinit var recipeDB: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_recipe)

        val editImgBtn:ImageButton = findViewById(R.id.editImageBtn)
        val editName = findViewById<EditText>(R.id.editName)
        val editMaterial = findViewById<EditText>(R.id.editMaterial)
        val editDescription = findViewById<EditText>(R.id.editDescription)
        val creatBtn: Button = findViewById(R.id.creatBtn)

        recipeDB = FirebaseDatabase.getInstance().reference

    }


    private fun writeRecipe(title:String, img:String, name:String) {
        val recipe = RecipeDB(title, img, name)
        recipeDB.child("Title").child(title).setValue(name)
    }

}