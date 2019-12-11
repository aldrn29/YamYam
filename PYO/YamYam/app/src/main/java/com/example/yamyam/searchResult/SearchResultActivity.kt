package com.example.yamyam.searchResult

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_search_result.*
import com.google.firebase.database.DataSnapshot
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe.RecipeSource
import com.example.yamyam.R

/* FridgeFragment 좌측 상단에 검색 버튼으로 검색 수행시 실행되는 액티비티
   내가 선택한 재료들로 firebase 에서 검색해 줌

    11.25 파이어베이스에서 재료를 읽어와야 하는데 재료가 없네... 그래서 일단 이름 긁어옴...
        재료 리스트로 파이어베이스에 추가함
        선택된 재료로 파이어베이스에 등록된 레시피를 검색해서 검색결과 액티비티에 표시(좌측 상단 돋보기->재료선택->다시 좌측 상단 돋보기)
    12.12 레이아웃 매니저를 리니어에서 그리드로 변경
 */


class SearchResultActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private var searchResultRecipeList = ArrayList<RecipeSource>()      //검색된 레시피들을 저장하는 ArraryList
    var searchResultAdapter : SearchResultRecipeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        val materialNameArrayToSearch = intent.getStringArrayListExtra("MaterialNameArrayToSearch")     // fridge_fragment 에서 가져온 재료 이름들
        val SearchResultActivityLayoutManager = GridLayoutManager(this, 1)

        searchResultAdapter = SearchResultRecipeAdapter(this, searchResultRecipeList)
        SearchResultRecyclerview.adapter = searchResultAdapter
        SearchResultRecyclerview.layoutManager = SearchResultActivityLayoutManager
        SearchResultRecyclerview.setHasFixedSize(true)
        searchResultAdapter?.notifyDataSetChanged()


        val mRef = database.getReference("recipes")

        mRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //firebase 에서 검색
                searchRecipeFromFirebase(dataSnapshot, materialNameArrayToSearch)
            }
                    //Toast.makeText(applicationContext, "$StringList", Toast.LENGTH_SHORT).show()

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    /* 재료의 이름들이 담긴 ArrayList 를 받아서 firebase 에서 검색하는 함수 */
    fun searchRecipeFromFirebase(dataSnapshot: DataSnapshot, materialNameArrayList :ArrayList<String>){
        var contains = false

        for(dataSnapshotChild in dataSnapshot.children) {
            val dataSnapshotChildChild  = dataSnapshotChild.child("materialsList")
            for(dataSnapshotChildChildChild in dataSnapshotChildChild.children){
                val materialInFirebase = dataSnapshotChildChildChild.getValue(String::class.java)                           //파이어베이스 materialsList 안에 들어있는 재료
                // fridgeFragment 에서 넘긴 재료 이름들이 파이어베이스 안의 레시피 재료들에 포함되어 있다면 searchResultRecipeList 에 그 레시피의 필드?들을 다 가져온다
                for(materialNameInArrayList in materialNameArrayList){
                    if(materialInFirebase == materialNameInArrayList){////파이어베이스의 재료가 넘긴재료들 중 하나와 같고
                        //중복검색을 막기위해 결과 레시피 리스트에 추가할 레시피의 이름이 없다면 add 해야함
                        for(recipe in searchResultRecipeList){
                            if(recipe.name == dataSnapshotChild.child("name").getValue(String::class.java)!!)
                                contains = true
                        }
                        //Toast.makeText(applicationContext, "${SnapShotRecipesChildren.child("name").getValue(String::class.java)}", Toast.LENGTH_SHORT).show()
                        if(contains == false) {
                            //재료 받아오기..하나씩..
                            var materialListInFirebase  = ArrayList<String>()
                            for(material in dataSnapshotChild.child("materialsList").children){
                                materialListInFirebase.add(material.getValue(String::class.java)!!)
                            }
                            searchResultRecipeList.add(RecipeSource(
                                dataSnapshotChild.child("description").getValue(String::class.java)!!,
                                dataSnapshotChild.child("imageUri").getValue(String::class.java)!!,
                                materialListInFirebase,
                                dataSnapshotChild.child("name").getValue(String::class.java)!!
                            ))
                                //add(RecipeSource(dataSnapshotChild.child("imageUri").getValue(String::class.java)!!, dataSnapshotChild.child("name").getValue(String::class.java)!!, dataSnapshotChild.child("description").getValue(String::class.java)!!), tmpList)
                        }
                        searchResultAdapter?.notifyDataSetChanged()
                        contains = false
                    }
                }
            }
        }
    }


}
