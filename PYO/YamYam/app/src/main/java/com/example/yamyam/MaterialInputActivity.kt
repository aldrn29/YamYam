package com.example.yamyam
/*10.13 카테고리 추가
* 10.14 seekBar 추가*/


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_material_input_activity.*

class MaterialInputActivity : AppCompatActivity() {

    var adapter: CategoryAdapter? = null
    var categoryList = ArrayList<Category>()    //카테고리 리스트


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_input_activity)

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

        //seekBar 값 받아서 expirationDate_text 텍스트뷰에 표시
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                expirationDate_text.setText(p1.toString())
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
                Toast.makeText(applicationContext,"유통기한",Toast.LENGTH_SHORT).show()
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        val resultIntent = Intent(this,MainActivity::class.java)
        addButton.setOnClickListener{
            resultIntent.putExtra("nameOfMaterial", material_text.text.toString())
            setResult(RESULT_OK, resultIntent)

            finish()
        }
    }
}
