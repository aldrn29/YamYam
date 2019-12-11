package com.example.recipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yamyam.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_recipe.*
import android.widget.ToggleButton
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot
/*
하나의 레시피 정보를 자세히 보여주는 API
사용자가 RecipeFragment에 있는 레시피 아이템 하나를 클릭시 보여진다.
레시피 사진, 이름, 재료명, 설명, 위시리스트 추가여부를 보여준다
그 아이템에 해당하는 레시피의 DB를 가져와 사용자에게 보여준다.
 */
class Recipe : AppCompatActivity() {

    private lateinit var recipeDB: DatabaseReference
    private val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val bundle: Bundle? = intent.extras
        val imageUri = bundle!!.getString("imageUri")
        val name = bundle!!.getString("name")
        val materialsList = bundle!!.getString("materialsList")
        val description = bundle!!.getString("description")
        val wish = bundle!!.getBoolean("wish")

        // RecyclerView Item의 pos에 따라 바뀌여야함
        recipeName.text = name
        Picasso.get().load(imageUri).into(recipeImg)
        materialArr.text = materialsList
        cookingDescription.text = description

        // 토글버튼
        val heartBtn = this.findViewById(R.id.addWish) as ToggleButton
        var touch = wish

        // Wish 값에 따라 보여지는 이미지
        if (touch) {
            heartBtn.background = resources.getDrawable(R.drawable.tab_heart_on)
        } else {
            heartBtn.background = resources.getDrawable(R.drawable.tab_heart)
        }

        // 토글버튼 눌렀을 때 이미지 변경
        heartBtn.setOnClickListener {

            if (heartBtn.isChecked) {
                heartBtn.background = resources.getDrawable(R.drawable.tab_heart_on)
                touch = true

            } else {
                heartBtn.background = resources.getDrawable(R.drawable.tab_heart)
                touch = false
            }

            recipeDB = FirebaseDatabase.getInstance().reference
            var recipeRef = database.getReference("recipes")
            var start = false

            // 토글에 따라 값 업데이트하기
            recipeRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (start) return

                    if (touch == true) {
                        searchRecipeFromFirebase(dataSnapshot, name!!, true)
                    } else {
                        searchRecipeFromFirebase(dataSnapshot, name!!, false)
                    }
                    start = true
                    //println("입력: " + touch)
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }

    }

    // firebase: name으로 검색해서 key 값 찾기
    // database: wish 값으로 업데이트 하기
    fun searchRecipeFromFirebase(dataSnapshot: DataSnapshot, name : String, wish : Boolean) {

        for (dataSnapshotChild in dataSnapshot.children) {
            if (dataSnapshotChild.child("name").value == name) {

                val key = dataSnapshotChild.key
                recipeDB.child("recipes").child(key!!).child("wish").setValue(wish)
                break
            }

        }
    }

}