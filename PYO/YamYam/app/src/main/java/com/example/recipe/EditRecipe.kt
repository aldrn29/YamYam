package com.example.recipe

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.yamyam.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_edit_recipe.*
import java.io.ByteArrayOutputStream

class EditRecipe : AppCompatActivity() {
    //데이터베이스 인스턴스
    private lateinit var image : String
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
        val editImgBtn: ImageButton = findViewById(R.id.editImgBtn)


        editImgBtn.setOnClickListener { openGallery() }


        recipeDB = FirebaseDatabase.getInstance().reference

//        creat 버튼 누를시 입력된 값으로 객체 생성해야함
        creatBtn.setOnClickListener {
            writeRecipe(image,editName.text.toString(),editDescription.text.toString())
        }

    }

    private fun openGallery() {
        val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, OPEN_GALLERY)
    }


    private fun writeRecipe(image : String, name : String, description: String) {
        val key = recipeDB.child("recipes").push().key
        if (key == null) {
            Log.w(TAG, "Couldn't get push key for recipes")
            return
        }

        val recipe = RecipeSource(image, name, description)
        val recipeValues = recipe.toMap()

        val childUpdates = HashMap<String, Any>()
        childUpdates["/recipes/$key"] = recipeValues
//        childUpdates["/recipes/$name/$key"] = recipeValues

        recipeDB.updateChildren(childUpdates)
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == OPEN_GALLERY) {

                var currentImageUrl : Uri? = data?.data

                try{
                    var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, currentImageUrl)
                    editImgBtn.setImageBitmap(bitmap)
                    var byteArrayOutput = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutput)
                    var imageByte : ByteArray = byteArrayOutput.toByteArray()
                    image = Base64.encodeToString(imageByte, Base64.NO_WRAP)
                }catch(e:Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            Log.d("ActivityResult","Wrong")
        }
    }

    // companion object 내에 선언된 속성과 함수는 {클래스명}.{필드/함수 이름} 형태로 바로 호출할 수 있다. 자바 static 개념
    companion object {
        private const val TAG = "EditRecipe"
    }

}