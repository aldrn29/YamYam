package com.example.recipe

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.yamyam.R

class EditRecipe : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_recipe)

        val editImgBtn:ImageButton = findViewById(R.id.editImageBtn)
        val editName = findViewById<EditText>(R.id.editName)
        val editMaterial = findViewById<EditText>(R.id.editMaterial)
        val editDescription = findViewById<EditText>(R.id.editDescription)
    }

}