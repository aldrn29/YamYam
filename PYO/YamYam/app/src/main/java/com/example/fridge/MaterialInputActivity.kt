package com.example.fridge
/*10.13 푸드카테고리 추가
* 10.14 seekBar 추가
* 10.15 카테고리 추가
* 10.30 seekbar 범위 조절, 유통기한 날짜로 표시, 임시이미지가 아닌 이미지로 변경
* 11.14 foodList(오른쪽이미지)클릭시 텍스트 바로 들어가도록
* 11.15 코드 정리*/


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
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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
        //셋 리스너
        setListenerToCategorys()
        //seekBar 설정
        setSeekBar()

        val resultIntent = Intent(this, MainActivity::class.java)
        addButton.setOnClickListener{
            resultIntent.putExtra("nameOfMaterial", material_text.text.toString())
            resultIntent.putExtra("selectedFoodImage", foodList[selectedFoodPosition].image)        //선택된 food image를 넘긴다
            setResult(RESULT_OK, resultIntent)

            finish()
        }
    }

    /*초기 이미지 로드하는 함수*/
    private fun initialImageLoad() {
        //food image load(오른쪽), 기본값이 육류
        foodList.add(Category("소고기", R.drawable.beef))
        foodList.add(Category("돼지고기", R.drawable.pork))
        foodList.add(Category("닭고기", R.drawable.chicken))
        foodCategoryAdapter = CategoryAdapter(this, foodList)
        foodCategory.adapter = foodCategoryAdapter

        //category image load(왼쪽)
        categoryList.add(Category("육류", R.drawable.meat))
        categoryList.add(Category("어패류", R.drawable.fish))
        categoryList.add(Category("유제품", R.drawable.milk))
        categoryList.add(Category("야채", R.drawable.vegetable))
        categoryList.add(Category("과일", R.drawable.banana2))
        categoryAdapter = CategoryAdapter(this, categoryList)
        category.adapter = categoryAdapter
    }

    /*좌측 카테고리클릭시*/
    private fun categoryItemClicked(position: Int) {
        foodList.removeAll(foodList)
        when(position) {
            //육류
            0 -> {
                foodList.add(Category("소고기", R.drawable.beef))
                foodList.add(Category("돼지고기", R.drawable.pork))
                foodList.add(Category("닭고기", R.drawable.chicken))
            } //어패류
            1 -> {
                foodList.add(Category("고등어", R.drawable.one))
                foodList.add(Category("바지락", R.drawable.two))
                foodList.add(Category("꽁치", R.drawable.three))
            } //유제품
            2 -> {
                foodList.add(Category("우유", R.drawable.milk))
                foodList.add(Category("계란", R.drawable.egg))
                foodList.add(Category("치즈", R.drawable.cheese))
            } //야채
            3 -> {
                foodList.add(Category("상추", R.drawable.sang_chu))
                foodList.add(Category("토마토", R.drawable.tomato))
                foodList.add(Category("파", R.drawable.green_onion))
                foodList.add(Category("양파", R.drawable.onion))
                foodList.add(Category("샐러드", R.drawable.salad))
            } //과일
            4 -> {
                foodList.add(Category("사과", R.drawable.red_apple))
                foodList.add(Category("오렌지", R.drawable.orange))
                foodList.add(Category("복숭아", R.drawable.peach))
                foodList.add(Category("딸기", R.drawable.strawberry))
                foodList.add(Category("파인애플", R.drawable.pine_apple))
                foodList.add(Category("바나나", R.drawable.banana))
                foodList.add(Category("체리", R.drawable.cherry))
                foodList.add(Category("포도", R.drawable.grape))
                foodList.add(Category("수박", R.drawable.watermelon))
           }
        }
        foodCategoryAdapter?.notifyDataSetChanged()                     //Item을 remove하고 나서 다시 알려줘야 refresh됨
    }

    /*foodCategory, Category 설정*/
    private fun setListenerToCategorys(){
        //category 아이템 클릭
        category.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                categoryItemClicked(position)               //클릭된 카테고리에 따라 이미지 load
            }
        }
        //foodList(오른쪽 음식 이미지) 클릭
        foodCategory.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedFoodPosition = position
                material_text.setText(foodList[selectedFoodPosition].name)      //오른쪽 클리하면 그냥 텍스트가 바로 들어가도록 설정

            }
        }
    }

    /*SeekBar 설정 */
    private fun setSeekBar(){
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                val cal = Calendar.getInstance()
                cal.time = Date()
                val df: DateFormat = SimpleDateFormat("yyyy-MM-dd") as DateFormat
                //유통기한 증가 범위 조절
                if(p1<100)
                    cal.add(Calendar.DATE, p1/2)
                else if(p1<150)
                    cal.add(Calendar.DATE, p1 - 50)
                else if(p1<=200)
                    cal.add(Calendar.DATE, (p1-150)*10+100)
                expirationDate_text.setText(df.format(cal.time).toString())     //seekBar 값 받아서 expirationDate_text 텍스트뷰에 표시
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
                // Toast.makeText(applicationContext,"유통기한",Toast.LENGTH_SHORT).show()
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }
}
