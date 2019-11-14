package com.example.recipe

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.yamyam.R
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_edit_recipe.*

class EditRecipe : AppCompatActivity() {
    //데이터베이스 인스턴스
    private lateinit var recipeDB: DatabaseReference
//    var editName = findViewById<EditText>(R.id.editName)
//    var editMaterial = findViewById<EditText>(R.id.editMaterial)
//    var editDescription = findViewById<EditText>(R.id.editDescription)
//    var creatBtn: Button = findViewById(R.id.creatBtn)

    private val OPEN_GALLERY = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_recipe)

        // 이안에 꼭 선언해야함 안하면 안뜸
            var selectedImg : ImageView = findViewById(R.id.selectedImg)
        val editImgBtn: ImageButton = findViewById(R.id.editImgBtn)


        editImgBtn.setOnClickListener { openGallery() }


//        recipeDB = FirebaseDatabase.getInstance().reference
//
//        //creat 버튼 누를시 입력된 값으로 객체 생성해야함
//        creatBtn.setOnClickListener {
//
//        }

    }

    private fun openGallery() {
        val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, OPEN_GALLERY)
    }


    private fun writeRecipe(title:String, img:String, name:String) {
        val recipe = RecipeSource(title, img, name)
        recipeDB.child("Title").child(title).setValue(name)
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == OPEN_GALLERY) {

                var currentImageUrl : Uri? = data?.data

                try{
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, currentImageUrl)
                    selectedImg.setImageBitmap(bitmap)
                }catch(e:Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            Log.d("ActivityResult","Wrong")
        }
    }

}