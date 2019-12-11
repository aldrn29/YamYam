package com.example.recipe

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.yamyam.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_edit_recipe.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
/*
요리 레시피에 대한 정보를 사용자가 직접 입력하여 데이터 베이스에 저장하는 API
이미지 버튼을 눌러 디바이스 내 갤러리에 있는 사진을 가져온다
나머지 요리 이름, 재료, 설명을 기입하고 이 정보들을 토대로 파이어베이스 스토리지에 사진을,
이름, 재료, 설명, 이미지 URI를 DB에 저장한다
 */

class EditRecipe : AppCompatActivity() {
    //데이터베이스 인스턴스
    private lateinit var recipeDB: DatabaseReference
    private var filePath : Uri? = null

    private val OPEN_GALLERY = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_recipe)

        // 이안에 꼭 선언해야함 안하면 안뜸
        val editImgBtn: ImageButton = findViewById(R.id.editImgBtn)
        val createBtn : Button = findViewById(R.id.createBtn)
        var editMaterial = findViewById<EditText>(R.id.editMaterial)
        var materialArray = mutableListOf<String>()

        editImgBtn.setOnClickListener { openGallery() }

        //파이어베이스 DB 참조
        recipeDB = FirebaseDatabase.getInstance().reference

        //create 버튼
        createBtn.setOnClickListener {
            //버튼이 눌리면 editMaterial 에 입력된 것들을 쪼개서 List<Stirng>에 넣는다
            for(a in editMaterial.text.toString().split(",", "\\s").toTypedArray()) {
                materialArray.add(a)
            }
            writeRecipe(editName.text.toString(),editDescription.text.toString(), materialArray)
        }

    }

    //갤러리에서 사진정보를 가져온다
    private fun openGallery() {
        val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(Intent.createChooser(intent, "Select image"), OPEN_GALLERY)
    }


    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && requestCode == OPEN_GALLERY) {
            filePath = data!!.data
            Log.d(TAG, "uri:" + filePath.toString())
            try{
                //Uri 파일을 Bitmap으로 만들어서 ImageView에 집어 넣는다.
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                editImgBtn!!.setImageBitmap(bitmap)
            }catch(e:Exception) {
                e.printStackTrace()
            }

        } else {
            Log.d("ActivityResult","Wrong")
        }
    }

    private fun writeRecipe(name : String, description: String, materialArray : List<String>, wish : Boolean = false) {

        var imageUri : String = "No Image"

        //지금까지 왜 안됐을까.. 우선 스토리지 주소를 입력하지 않았었고 어쩌면 성공 실패 리스너가 필수일지도 모른다.
        //Progress Dialog & Upload to Storage
        if (filePath != null) {

            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("업로드중...")
            progressDialog.show()


            val storage = FirebaseStorage.getInstance()

            //storage 주소와 폴더 파일명을 지정해 준다.
            val formatter = SimpleDateFormat("yyyyMMHH_mmss")
            val now = Date()
            val filename = formatter.format(now) + ".png"
            val storageRef = storage.getReferenceFromUrl("gs://yamyam-6690a.appspot.com/") //이부분이 없으면 저장할 수 없다 파이어베이스 주소
                .child("images/$filename")


            val uploadTask = storageRef.putFile(filePath!!)
            //완료시 dialog.dismiss 전에 activity finish를 하면 에러가 발생한다
            //it : UploadTask.TaskSnapshot
            uploadTask.addOnSuccessListener {
                var result = it.metadata!!.reference!!.downloadUrl
                result.addOnSuccessListener {
                    imageUri = it.toString()
                    Log.w("image", it.toString())
                    val key = recipeDB.child("recipes").push().key
                    if (key == null) {
                        Log.w(TAG, "Couldn't get push key for recipes")
                        return@addOnSuccessListener
                    }
                    val recipe = RecipeSource(description, it.toString(), materialArray, name, wish)
                    val recipeValues = recipe.toMap()

                    val childUpdates = HashMap<String, Any>()
                    childUpdates["/recipes/$key"] = recipeValues

                    recipeDB.updateChildren(childUpdates)
                }

                progressDialog.dismiss()
                Toast.makeText(applicationContext, "업로드 완료!", Toast.LENGTH_SHORT).show()
                finish()
            }
                //실패시
                .addOnFailureListener {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "업로드 실패!", Toast.LENGTH_SHORT).show()
                }
                //진행중
                .addOnProgressListener { taskSnapshot ->
                    val progress =
                        (100 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toDouble()
                    //dialog에 진행률을 퍼센트로 출력해 준다
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "% …")
                }
        } else {
            Toast.makeText(applicationContext, "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show()
        }


    }



    // companion object 내에 선언된 속성과 함수는 {클래스명}.{필드/함수 이름} 형태로 바로 호출할 수 있다. 자바 static 개념
    companion object {
        private const val TAG = "EditRecipe"
    }

}