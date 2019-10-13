package com.example.yamyam
/*10.13 카테고리 추가*/

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_input_material_activity.*

class MaterialInputActivity : AppCompatActivity() {

    var adapter: CategoryAdapter? = null
    var categoryList = ArrayList<Category>()    //카테고리 리스트


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_material_activity)

        //카테고리 image load
        categoryList.add(Category("카테고리1", R.drawable.one))
        categoryList.add(Category("카테고리2", R.drawable.two))
        categoryList.add(Category("카테고리3", R.drawable.three))
        categoryList.add(Category("카테고리4", R.drawable.four))
        categoryList.add(Category("카테고리5", R.drawable.five))
        categoryList.add(Category("카테고리6", R.drawable.six))
        categoryList.add(Category("카테고리7", R.drawable.seven))
        categoryList.add(Category("카테고리8", R.drawable.eight))
        categoryList.add(Category("카테고리9", R.drawable.nine))
        adapter = CategoryAdapter(this, categoryList)

        gridViewCategory.adapter = adapter

        val resultIntent = Intent(this,MainActivity::class.java)

        addButton.setOnClickListener{
            resultIntent.putExtra("nameOfMaterial", material_text.text.toString())
            setResult(RESULT_OK, resultIntent)

            finish()
        }
    }
}
