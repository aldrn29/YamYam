package com.example.fridge
/*10.13 푸드카테고리 추가
* 10.14 seekBar 추가
* 10.15 카테고리 추가*/


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import android.widget.Toast
import com.example.yamyam.Category
import com.example.yamyam.CategoryAdapter
import com.example.yamyam.MainActivity
import com.example.yamyam.R
import kotlinx.android.synthetic.main.activity_material_input_activity.*

class MaterialInputActivity : AppCompatActivity() {

    var foodCategoryAdapter: CategoryAdapter? = null
    var categoryAdapter: CategoryAdapter? = null
    var foodList = ArrayList<Category>()        //gridViewCategory(오른쪽)에 표시되는 음식리스트
    var categoryList = ArrayList<Category>()    //category(왼쪽)에 표시되는 카테고리 리스트


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_input_activity)

        //food image load(오른쪽), 기본값이 육류
        foodList.add(Category("소고기", R.drawable.one))
        foodList.add(Category("돼지고기", R.drawable.two))
        foodCategoryAdapter = CategoryAdapter(this, foodList)
        foodCategory.adapter = foodCategoryAdapter

        //category image load(왼쪽)
        categoryList.add(Category("육류", R.drawable.one))
        categoryList.add(Category("어패류", R.drawable.two))
        categoryList.add(Category("유제품", R.drawable.three))
        categoryList.add(Category("야채", R.drawable.four))
        categoryAdapter = CategoryAdapter(this, categoryList)
        category.adapter = categoryAdapter

        category.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
               // val selectedItem = parent.getItemAtPosition(position).toString()
                //Toast.makeText(applicationContext,"$position",Toast.LENGTH_SHORT).show()

                //좌측 카테고리에서 육류 선택시
                if(position == 0){
                    foodList.removeAll(foodList)
                    foodList.add(Category("소고기", R.drawable.one))
                    foodList.add(Category("돼지고기", R.drawable.two))
                    foodCategoryAdapter?.notifyDataSetChanged()                     //Item을 remove하고 나서 다시 알려줘야 refresh됨
                }
                //어패류
                else if(position==1){
                    foodList.removeAll(foodList)
                    foodList.add(Category("고등어", R.drawable.one))
                    foodList.add(Category("바지락", R.drawable.two))
                    foodCategoryAdapter?.notifyDataSetChanged()
                }
                //유제품
                else if(position==2){
                    foodList.removeAll(foodList)
                    foodList.add(Category("우유", R.drawable.one))
                    foodList.add(Category("계란", R.drawable.two))
                    foodCategoryAdapter?.notifyDataSetChanged()
                }
                //야채
                else if(position==3){
                    foodList.removeAll(foodList)
                    foodList.add(Category("상추", R.drawable.one))
                    foodList.add(Category("사과", R.drawable.two))
                    foodCategoryAdapter?.notifyDataSetChanged()
                }
            }
        }





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

        val resultIntent = Intent(this, MainActivity::class.java)
        addButton.setOnClickListener{
            resultIntent.putExtra("nameOfMaterial", material_text.text.toString())
            setResult(RESULT_OK, resultIntent)

            finish()
        }
    }
}
