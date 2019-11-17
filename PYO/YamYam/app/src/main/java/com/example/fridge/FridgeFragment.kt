package com.example.fridge


import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.recipe.RecipeFragment
import kotlinx.android.synthetic.main.fragment_fridge.*
import kotlin.collections.ArrayList
import com.example.yamyam.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_material_input_activity.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.exp


/* 10.16
임시 이미지가 아닌 MaterialInputActivity에서 선택된 foodimage가 들어가도록 변경
11.15 코드정리, 위아래 모두 리사이클러뷰로 변경
11.17 객체생성할때 날짜도 받아서 생성하도록 만듦
Intent로 넘겨받은 year,month,date를 한번에 쓰기위한 data class materialExpirationDate 정의
 */
class FridgeFragment : Fragment() {

    //var upperAdapter: notUseMaterialAdapter? = null
    var lowerAdapter: MaterialAdapter? = null
    var upperAdapter : MaterialAdapter? = null
    var upperMaterialsList: java.util.ArrayList<Material> = ArrayList<Material>()
    var lowerMaterialsList = ArrayList<Material>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(com.example.yamyam.R.layout.fragment_fridge, container, false)

        // toolbar 초기화
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(com.example.yamyam.R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        //(activity as AppCompatActivity).supportActionBar?.title = "냉장고"
        setHasOptionsMenu(true)


        return view
    }

    //handle item clicks of menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            com.example.yamyam.R.id.plusItem -> {
                Toast.makeText(activity, "추가", Toast.LENGTH_SHORT).show()

                // + 버튼 이벤트 추가하기
                // 냉장고 상/하 구분짓기


                


                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val temporaryButton : Button = view.findViewById(com.example.yamyam.R.id.temporaryButton)

        /* set + - 버튼 클릭 리스너 */
        setClickListenerToButtons()

        //임시 버튼 하단 탭 구성전에 임시로 사용중 버튼 위치상 첫번째 냉동고 기입 식재료명 가려짐
        temporaryButton.setOnClickListener {
            // RecipeSerachActivity로 이동
            //val intent = Intent(activity, RecipeSerachActivity::class.java)
            //startActivity(intent)

            // 탭 메뉴 변경
            val bottomNavigationView : BottomNavigationView = (activity as MainActivity).findViewById(
                com.example.yamyam.R.id.navigationView)
            bottomNavigationView.menu.findItem(com.example.yamyam.R.id.recipeItem).isChecked = true

            // RecipeFragment 로 화면이동
            val transaction : FragmentTransaction = (activity as MainActivity).supportFragmentManager.beginTransaction()
            transaction.add(com.example.yamyam.R.id.act_fragment, RecipeFragment())
            transaction.commit()
        }

        /*위 아래 리사이클러 뷰에 어댑터 붙임*/
        setAdapter()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val nameOfMaterial : String? = data?.getStringExtra("nameOfMaterial")
        val image : Int = data!!.getIntExtra("selectedFoodImage", 0)
        val expirationDate_year=  data.getIntExtra("expirationDate_year",0)
        val expirationDate_month=  data.getIntExtra("expirationDate_month",0)
        val expirationDate_date=  data.getIntExtra("expirationDate_date",0)

        //나중에 변수명 바꿀것, 변수명뭐로하지
        var tmpDate = materialExpirationDate(expirationDate_year, expirationDate_month, expirationDate_date)
        /* 아이템 터치 헬퍼 붙임 */
        setItemTouchHelper(requestCode, resultCode, nameOfMaterial!!, image, tmpDate)
    }

    private fun setItemTouchHelper(requestCode: Int, resultCode: Int, nameOfMaterial : String, image: Int, expirationDate : materialExpirationDate){
        /* MaterialItemTouchHelper 에 callback 을 등록, recycler 뷰에 붙여줌
        *  상하좌우 드래그설정
        spanCount 가 열 개수인듯*/
        val upperManager = GridLayoutManager(requireContext(), 6)
        val upperCallBack = MaterialItemTouchHelper(upperAdapter!!, requireContext(), (ItemTouchHelper.ANIMATION_TYPE_DRAG), -1)
        val upperHelper = ItemTouchHelper(upperCallBack)
        upperRecyclerView.layoutManager = upperManager
        upperHelper.attachToRecyclerView(upperRecyclerView)
        upperRecyclerView.setHasFixedSize(true)

        val lowerManager = GridLayoutManager(requireContext(), 6)
        val lowerCallBack = MaterialItemTouchHelper(lowerAdapter!!, requireContext(), (ItemTouchHelper.ANIMATION_TYPE_DRAG), -1)
        val lowerHelper = ItemTouchHelper(lowerCallBack)
        lowerRecyclerView.layoutManager = lowerManager
        lowerHelper.attachToRecyclerView(lowerRecyclerView)
        lowerRecyclerView.setHasFixedSize(true)

        //upperBody 에 추가
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == 0){
            //inputMaterialActivity 에서 넘긴 이름과, foodImage
            upperMaterialsList.add(Material(nameOfMaterial, image, expirationDate))
            Toast.makeText(activity,"$nameOfMaterial 추가완료", Toast.LENGTH_SHORT).show()
        }
        //lowerBody 에 추가
        else if(resultCode == AppCompatActivity.RESULT_OK && requestCode == 1){
            //inputMaterialActivity 에서 넘긴 이름과, foodImage
            lowerMaterialsList.add(Material(nameOfMaterial, image, expirationDate))
            Toast.makeText(activity,"$nameOfMaterial 추가완료", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setClickListenerToButtons(){
        upperPlusButton.setOnClickListener {
        val intent = Intent(activity, MaterialInputActivity::class.java)
        startActivityForResult(intent, 0)       //request Code 0은 upperBody
        }
        upperMinusButton.setOnClickListener {
            if (upperAdapter?.isClicked == false) {  //안눌린 상태
                upperMinusButton.setBackgroundColor(-0x777778)  //gray
                upperAdapter?.setIsClicked(true)
            } else if (upperAdapter?.isClicked == true) {    //눌린상태
                upperMinusButton.setBackgroundColor(-0x1)   //white
                upperAdapter?.setIsClicked(false)
            }
        }

        lowerPlusButton.setOnClickListener{
        val intent = Intent(activity, MaterialInputActivity::class.java)
        startActivityForResult(intent, 1)       //requestCode 1은 lowerBody
        }
        lowerMinusButton.setOnClickListener {
            if (lowerAdapter?.isClicked == false) {  //안눌린 상태
                lowerMinusButton.setBackgroundColor(-0x777778)  //gray
                lowerAdapter?.setIsClicked(true)
            } else if (lowerAdapter?.isClicked == true) {    //눌린상태
                lowerMinusButton.setBackgroundColor(-0x1)   //white
                lowerAdapter?.setIsClicked(false)
            }
        }
    }

    private fun setAdapter(){
        lowerAdapter = MaterialAdapter(requireContext(), lowerMaterialsList)
        upperAdapter = MaterialAdapter(requireContext(), upperMaterialsList)
        upperRecyclerView.adapter = upperAdapter
        lowerRecyclerView.adapter = lowerAdapter
        upperAdapter!!.notifyDataSetChanged()   //여기가 답이였네, 왜 드래그로 위치 바꿔도 안바뀌나 3일 내내 고민
    }

    /*유통기간 체크하는 함수 마테리얼 넘겨서 비교하자*/
    private fun checkExpirationDate(material: Material){
        var cal : Calendar = Calendar.getInstance()
        cal.time = Date()
        /*
        if(//3일밖에 유통기한이 안남았다면))
        {
            //배경 노란색으로 변경
        }
        else if(3~7일 남았다면){
            //배경 주황생으로 변경
        }
        */
    }

    //private fun saveData

    /* 인텐트로 넘겨받은 날짜 한 데 모아둘 데이터클래스 */
    data class materialExpirationDate(var year: Int, var month: Int, var date: Int)
}
