package com.example.recipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yamyam.R
import com.google.firebase.database.DatabaseReference

class EditRecipe : AppCompatActivity() {
    //데이터베이스 인스턴스
    private lateinit var recipeDB: DatabaseReference

//    var editImgBtn:ImageButton = findViewById(R.id.editImageBtn)
//    var editName = findViewById<EditText>(R.id.editName)
//    var editMaterial = findViewById<EditText>(R.id.editMaterial)
//    var editDescription = findViewById<EditText>(R.id.editDescription)
//    var creatBtn: Button = findViewById(R.id.creatBtn)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_recipe)
//
//        recipeDB = FirebaseDatabase.getInstance().reference
//
//        //creat 버튼 누를시 입력된 값으로 객체 생성해야함
//        creatBtn.setOnClickListener {
//
//        }

    }


    private fun writeRecipe(title:String, img:String, name:String) {
        val recipe = RecipeSource(title, img, name)
        recipeDB.child("Title").child(title).setValue(name)
    }

}