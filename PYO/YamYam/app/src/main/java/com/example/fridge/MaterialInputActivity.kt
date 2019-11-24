package com.example.fridge
/*10.13 푸드카테고리 추가
* 10.14 seekBar 추가
* 10.15 카테고리 추가
* 10.30 seekbar 범위 조절, 유통기한 날짜로 표시, 임시이미지가 아닌 이미지로 변경
* 11.14 foodList(오른쪽이미지)클릭시 텍스트 바로 들어가도록
* 11.15 코드 정리
* 11.17 cal 를 쪼개서 year, month, date 로 넘김
* 11.22 냉동 냉장을 선택하는 checkBox 추가, 냉동/냉장 중복 체크 불가능하도록 설정*/


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
    var foodList = ArrayList<Category>()                   //gridViewCategory(오른쪽)에 표시되는 음식리스트
    var categoryList = ArrayList<Category>()               //category(왼쪽)에 표시되는 카테고리 리스트
    var selectedFoodPosition: Int = 0                      //선택된 foodImage 포지션
    var upperOrLowerChecked :Int? = null                   //냉장(upper) == 0  냉동(lower) == 1
    val cal : Calendar = Calendar.getInstance()            //유통기한 표시용 날짜

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_input_activity)

        //초기 이미지 load
        initialImageLoad()
        //셋 리스너
        setListenerToCategorys()
        //seekBar 설정
        setSeekBar(cal)
        //checkBox 리스너 설정
        setListenersToCheckBoxes()

        //추가
        val resultIntent = Intent(this, MainActivity::class.java)
        addButton.setOnClickListener{
            if(upperOrLowerChecked == null){
                Toast.makeText(applicationContext, "냉동, 냉장을 선택해 주십시오",Toast.LENGTH_SHORT).show()
            }
            else{
                resultIntent.putExtra("nameOfMaterial", material_text.text.toString())
                resultIntent.putExtra("selectedFoodImage", foodList[selectedFoodPosition].image) //선택된 food image 를 넘긴다
                resultIntent.putExtra("expirationDate_year", cal.time.year)
                resultIntent.putExtra("expirationDate_month", cal.time.month)
                resultIntent.putExtra("expirationDate_date", cal.time.date) //유통기한 쪼개서 넘기자 get~~Extra 로 calendar 못가져오는것 같음
                resultIntent.putExtra("upperOrLower", upperOrLowerChecked!!)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    /*초기 이미지 로드하는 함수*/
    private fun initialImageLoad() {
        //food image load(오른쪽), 기본값이 육류
        foodList.add(Category("소고기", R.drawable.beef))
        foodList.add(Category("돼지고기", R.drawable.pork))
        foodList.add(Category("닭고기", R.drawable.chicken))
        foodList.add(Category("닭가슴살", R.drawable.chicken_breast))
        foodList.add(Category("베이컨", R.drawable.bacon))
        foodList.add(Category("햄", R.drawable.ham))
        foodCategoryAdapter = CategoryAdapter(this, foodList)
        foodCategory.adapter = foodCategoryAdapter

        //category image load(왼쪽)
        categoryList.add(Category("육류", R.drawable.beef))
        categoryList.add(Category("어패류", R.drawable.anchovy))
        categoryList.add(Category("유제품", R.drawable.milk))
        categoryList.add(Category("채소", R.drawable.broccoli))
        categoryList.add(Category("과일", R.drawable.apple))
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
                foodList.add(Category("닭가슴살", R.drawable.chicken_breast))
                foodList.add(Category("베이컨", R.drawable.bacon))
                foodList.add(Category("햄", R.drawable.ham))
            } //어패류
            1 -> {
                foodList.add(Category("멸치", R.drawable.anchovy))
                foodList.add(Category("새우", R.drawable.shrimp))
                foodList.add(Category("꽃게", R.drawable.crab))
            } //유제품
            2 -> {
                foodList.add(Category("우유", R.drawable.milk))
                foodList.add(Category("계란", R.drawable.egg))
                foodList.add(Category("치즈", R.drawable.cheese))
            } //채소
            3 -> {
                foodList.add(Category("브로콜리", R.drawable.broccoli))
                foodList.add(Category("당근", R.drawable.carrot))
                foodList.add(Category("대파", R.drawable.green_onion))
                foodList.add(Category("토마토", R.drawable.tomato))
                foodList.add(Category("양파", R.drawable.onion))
                foodList.add(Category("배추", R.drawable.chinese_cabbage))
                foodList.add(Category("양배추", R.drawable.cabbage))
                foodList.add(Category("파프리카", R.drawable.paprica))
                foodList.add(Category("콩나물", R.drawable.bean_sprout))
                foodList.add(Category("콩", R.drawable.bean))
                foodList.add(Category("고추", R.drawable.chili))
                foodList.add(Category("가지", R.drawable.eggplant))
                foodList.add(Category("마늘", R.drawable.garlic))
                foodList.add(Category("무", R.drawable.radish))
                foodList.add(Category("호박", R.drawable.pumpkin))
                foodList.add(Category("애호박", R.drawable.green_pumpkin))
                foodList.add(Category("다시마", R.drawable.kelp))
                foodList.add(Category("상추", R.drawable.lettuce))
                foodList.add(Category("시금치", R.drawable.spinach))
                foodList.add(Category("두부", R.drawable.tofu))
            } //과일
            4 -> {
                foodList.add(Category("사과", R.drawable.apple))
                foodList.add(Category("레몬", R.drawable.lemon))
                foodList.add(Category("딸기", R.drawable.strawberry))
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

    /* checkBox Listener */
    private fun setListenersToCheckBoxes() {
        /* 냉동 냉장 둘 중 하나만 선택가능 하도록 */
        upperCheckBox.setOnClickListener {
            if(lowerCheckBox.isChecked) {
                lowerCheckBox.isChecked = false
            }
            upperOrLowerChecked = 0               //0은 냉장(upper)
        }
        lowerCheckBox.setOnClickListener {
            if(upperCheckBox.isChecked){
                upperCheckBox.isChecked = false
            }
            upperOrLowerChecked = 1               //1은 냉동(lower)
        }
    }

    /*SeekBar 설정 */
    private fun setSeekBar(cal : Calendar){
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
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
