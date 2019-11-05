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
import com.example.yamyam.MainActivity
import com.example.yamyam.R
import kotlinx.android.synthetic.main.activity_material_input_activity.*

class MaterialInputActivity : AppCompatActivity() {

    var foodCategoryAdapter: CategoryAdapter? = null
    var categoryAdapter: CategoryAdapter? = null
    var foodList = ArrayList<Category>()        //gridViewCategory(오른쪽)에 표시되는 음식리스트
    var categoryList = ArrayList<Category>()    //category(왼쪽)에 표시되는 카테고리 리스트
    var selectedFoodPosition: Int = 0           //선택된 foodImage 포지션

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_input_activity)


        //초기 이미지 load
        initialImageLoad()

        //category 아이템 클릭
        category.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
               // val selectedItem = parent.getItemAtPosition(position).toString()
                //Toast.makeText(applicationContext,"$position",Toast.LENGTH_SHORT).show()
                categoryItemClicked(position)               //클릭된 카테고리에 따라 이미지 load
            }
        }
        foodCategory.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedFoodPosition = position
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
            resultIntent.putExtra("selectedFoodImage", foodList[selectedFoodPosition].image)        //선택된 food image를 넘긴다
            setResult(RESULT_OK, resultIntent)

            finish()
        }
    }

    private fun initialImageLoad() {
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
        categoryList.add(Category("가공식품", R.drawable.five))
        categoryAdapter = CategoryAdapter(this, categoryList)
        category.adapter = categoryAdapter
    }

    private fun categoryItemClicked(position: Int) {
        //좌측 카테고리에서 육류 선택시
        when(position) {
            0 -> {
                foodList.removeAll(foodList)
                foodList.add(Category("소고기", R.drawable.one))
                foodList.add(Category("돼지고기", R.drawable.two))
            } //어패류
            1 -> {
                foodList.removeAll(foodList)
                foodList.add(Category("고등어", R.drawable.one))
                foodList.add(Category("바지락", R.drawable.two))
                foodList.add(Category("꽁치", R.drawable.three))
            } //유제품
            2 -> {
                foodList.removeAll(foodList)
                foodList.add(Category("우유", R.drawable.one))
                foodList.add(Category("계란", R.drawable.two))
                foodList.add(Category("치즈", R.drawable.three))
            } //야채
            3 -> {
                foodList.removeAll(foodList)
                foodList.add(Category("상추", R.drawable.one))
                foodList.add(Category("토마토", R.drawable.two))
                foodList.add(Category("깻잎", R.drawable.three))
                foodList.add(Category("부추", R.drawable.four))
            }
        }
        foodCategoryAdapter?.notifyDataSetChanged()                     //Item을 remove하고 나서 다시 알려줘야 refresh됨
    }
}
