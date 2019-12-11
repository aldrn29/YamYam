package com.example.recipe

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yamyam.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_recipe.*
import android.widget.ToggleButton
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot




class Recipe : AppCompatActivity() {

    private lateinit var recipeDB: DatabaseReference
    private val database = FirebaseDatabase.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val bundle: Bundle? = intent.extras
        val Image = bundle!!.getString("Firebase_Image")
        val Title = bundle!!.getString("Firebase_Title")
        val Materials = bundle!!.getString("Firebase_Materials")
        val Description = bundle!!.getString("Firebase_Description")
        val Wish = bundle!!.getBoolean("Firebase_Wish")

        // RecyclerView Item의 pos에 따라 바뀌여야함
        recipeName.text = Title
        Picasso.get().load(Image).into(recipeImg)
        materialArr.text = Materials
        cookingDescription.text = Description


        // 토글버튼
        val heartBtn = this.findViewById(R.id.addWish) as ToggleButton
        var touch = Wish

        // Wish 값에 따라 보여지는 이미지
        if (Wish) {
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

            recipeRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (start) return

                    //firebase에서 name으로 검색해서 값을 바꿔주기
                    if (touch == true) {
                        searchRecipeFromFirebase(dataSnapshot, Title!!, true)
                    } else {
                        searchRecipeFromFirebase(dataSnapshot, Title!!, false)
                    }
                    start = true
                    println("입력: " + touch)
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }

    }


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