package com.example.yamyam.searchResult

import android.os.Bundle
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.fridge.FridgeFragment
import com.example.recipe.Recipe
import kotlinx.android.synthetic.main.activity_recipe.*
import com.example.recipe.RecipeSource
import com.example.yamyam.R
import com.example.yamyam.WishListFragment
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.io.BufferedReader
import java.io.File

/* 레시피를 클릭할 경우 실행되는 액티비티
   레시피의 이름, 방법 등 상세 내용이 표시됨

    12.10 검색 결과가 클릭될경우 나타나는 액티비티
    12.12 내부저장소에 저장된 파일을 사용해 위시리스트 추가(하트버튼) 삭제(하트버튼) 구현
 */
class RecipeClickedActivity : AppCompatActivity()
{
    var wishList =  ArrayList<RecipeSource>()//
    val fileName = "SavedWishLists"
    var touch : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        val heartBtn = this.findViewById(R.id.addWish) as ToggleButton

        //처음에 저장된 위시리스트 들고있기
        loadFromSavedFile()
        //클릭한 레시피가 저장된 위시리스트 파일에 존재하나 확인
        isThisRecipeExistInWishList()

        heartButton(heartBtn, touch)
        //어댑터에서 넘긴 인텐트로부터 레시피 정보를 가져옴
        val clickResultRecipe = RecipeSource(
            intent.getStringExtra("description"),
            intent.getStringExtra("imageUri"),
            intent.getStringArrayListExtra("materialsList"),
            intent.getStringExtra("name"),
            touch
            )

        setHeartButtonClickListener(heartBtn, clickResultRecipe)
        /* 인텐트로부터 받아온 레시피를 화면에 표시 */
        setRecipeToDisplay(clickResultRecipe)
    }

    /* 가져온 레시피 정보를 다시 화면에 표시해주는 함수 */
    private fun setRecipeToDisplay(clickResultRecipe: RecipeSource){
        //ArrayList -> String 으로 바꾸는 곳
        var materialsListTextSum : String = ""
        var position : Int = 0
        for(material in clickResultRecipe.materials!!){
            if(position != 0) {
                materialsListTextSum += ", "
            }
            materialsListTextSum += material
            position++
        }
        materialArr.text = materialsListTextSum
        recipeName.text = clickResultRecipe.name
        cookingDescription.text = clickResultRecipe.description
        Picasso.get().load(clickResultRecipe.imageUri).into(recipeImg)
    }

    /* 셋 하트버튼 클릭 리스너, 위시리스트 넣고뺴고 여기서 */
    private fun setHeartButtonClickListener(heartBtn: ToggleButton, clickResultRecipe:RecipeSource){
        heartBtn.setOnClickListener {
            if (touch == false) {
                wishList.add(clickResultRecipe)
                touch = true
                heartButton(heartBtn,touch)
                writeJSONtoFile(fileName)
            } else {
                //그냥 레시피소스로 remove 하면 안됨, name 찾아서 지우자
                for(i in wishList) {
                    if (i.name.toString() == intent.getStringExtra("name")) {
                        wishList.remove(i)
                        break
                    }
                }
                touch = false
                heartButton(heartBtn,touch)
                writeJSONtoFile(fileName)
            }
        }
    }
    /* 하트버튼 표시해주는 함수 */
    private fun heartButton(heartBtn: ToggleButton, touch :Boolean){
        //이 레시피가 저장된 파일에 존재하면 true(검은하트) 존재하지 않으면 false(그냥하트)
        if (touch) {
            heartBtn.background = resources.getDrawable(R.drawable.tab_heart_on)
        } else {
            heartBtn.background = resources.getDrawable(R.drawable.tab_heart)
        }
    }

    private fun writeJSONtoFile(fileName: String){
        val gson = Gson()
        val jsonString:String = gson.toJson(wishList)
        //Toast.makeText(activity,"$jsonString", Toast.LENGTH_SHORT).show()
        val file = File(this.cacheDir, fileName)
        file.writeText(jsonString)
    }

    private fun loadWishList(fileName: String){
        val gson = Gson()
        //Read the json file
        val file = File(this.cacheDir, fileName)
        val bufferedReader: BufferedReader = file.bufferedReader()
        //bufferedReader 에서 텍스트를 읽고 inputString 에 저장
        val inputString = bufferedReader.use { it.readText() }
        //json 파일에 저장되어 있던 String 들을 다시 RecipeSource 로
        val restoredMaterial = gson.fromJson(inputString, Array<RecipeSource>::class.java)

        //load
        for(i in restoredMaterial){
            wishList.add(i)
        }
    }

    private fun loadFromSavedFile() {
        /* 처음 어플을 실행하는 경우 아직 파일을 쓰지 않았으므로 */
        if(File(this.cacheDir, fileName).exists()) {
            /* json 파일에서 저장되었던 wishList 불러옴 */
            loadWishList(fileName)
        }
    }

    /* 클릭한 레시피가 저장된 위시리스트 파일 안에 존재하면 true */
    private fun isThisRecipeExistInWishList() {
        for(i in wishList){
            if(i.name.toString() == intent.getStringExtra("name")){
                touch = true
                return
            }
            touch = false
        }
    }
}
