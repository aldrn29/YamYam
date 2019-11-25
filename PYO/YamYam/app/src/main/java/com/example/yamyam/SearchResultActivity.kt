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
import com.google.firebase.database.DataSnapshot
import androidx.recyclerview.widget.LinearLayoutManager


/* 11.25 파이어베이스에서 재료를 읽어와야 하는데 재료가 없네... 그래서 일단 이름 긁어옴...
        재료 리스트로 파이어베이스에 추가함
        선택된 재료로 파이어베이스에 등록된 레시피를 검색해서 검색결과 액티비티에 표시(좌측 상단 돋보기->재료선택->다시 좌측 상단 돋보기)

 */


class SearchResultActivity : AppCompatActivity() {

    //lateinit var recipeDB: DatabaseReference
    val database = FirebaseDatabase.getInstance()
    var searchResultRecipeList = ArrayList<SearchResultRecipe>()
    var StringList = ArrayList<String>()
    var SearchResultAdapter : SearchResultRecipeAdapter? = null
    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        var MaterialNameArrayToSearch = intent.getStringArrayListExtra("MaterialNameArrayToSearch")     // fridge_fragment 에서 가져온 재료 이름들
        //Toast.makeText(applicationContext, "${MaterialNameArrayToSearch}", Toast.LENGTH_SHORT).show()

        SearchResultAdapter = SearchResultRecipeAdapter(this, searchResultRecipeList)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        SearchResultRecyclerview.adapter = SearchResultAdapter
        SearchResultRecyclerview.setHasFixedSize(true)
        SearchResultRecyclerview.layoutManager =linearLayoutManager

        SearchResultAdapter?.notifyDataSetChanged()

        //var recipeDB = FirebaseDatabase.getInstance().reference
        //recipeDB.child("test").push().setValue("abc")

        val mRef = database.getReference("recipes")
        //Toast.makeText(applicationContext, "${mRef}", Toast.LENGTH_SHORT).show()

        mRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(SnapShotRecipesChildren in dataSnapshot.children) {
                    val materialsListChild  = SnapShotRecipesChildren.child("materialsList")
                    for(materialsListChildChildren in materialsListChild.children){
                        val materialInFirebase = materialsListChildChildren.getValue(String::class.java)                           //파이어베이스 materialsList 안에 들어있는 재료
                        for(materialInMaterialNameArrayToSearch in MaterialNameArrayToSearch){                                     // frideFragment 에서 넘긴 재료 이름들이 파이어베이스 안의 레시피 재료들에 포함되어 있다면 searchResultRecipeList에 그 레시피의 필드?들을 다 가져온다
                            if(materialInFirebase == materialInMaterialNameArrayToSearch){
                                //Toast.makeText(applicationContext, "${SnapShotRecipesChildren.child("name").getValue(String::class.java)}", Toast.LENGTH_SHORT).show()
                                searchResultRecipeList.add(SearchResultRecipe(
                                    SnapShotRecipesChildren.child("name").getValue(String::class.java)!!,
                                    SnapShotRecipesChildren.child("img").getValue(String::class.java)!!
                                ))
                                SearchResultAdapter?.notifyDataSetChanged()
                            }
                        }
                        //if(materialInFirebase != null)
                          //  StringList.add(materialInFirebase)        //재료 긁어오는 배열에 추가
                    }
                    //Toast.makeText(applicationContext, "$StringList", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

    }


}
