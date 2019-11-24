package com.example.fridge


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.yamyam.R
import kotlinx.android.synthetic.main.fragment_fridge.*
import java.io.File

/* 10.16
임시 이미지가 아닌 MaterialInputActivity 에서 선택된 foodimage 가 들어가도록 변경
11.15 코드정리, 위아래 모두 리사이클러뷰로 변경
11.17 객체생성할때 날짜도 받아서 생성하도록 만듦
    Intent 로 넘겨받은 year,month,date를 한번에 쓰기위한 data class materialExpirationDate 정의
11.18 gson 사용하여 파일 저장, load 가능
11.19 저장된 파일에서 불러오는 거 함수 따로 정의, 처음 실행시 Material 이 load 되지 않았던 문제 해결
11.22 MaterialInputActivity 에서 냉동 냉장 체크박스 체크내용 Intent 로 받아와서 냉동/냉장에 추가되도록 설정
    기존 + - 버튼 기능제거(주석)
11.24 툴바 아이템 클릭 리스너 없애고  onOptionsItemSelected 로 통합
 */

class FridgeFragment : Fragment() {

    //var upperAdapter: notUseMaterialAdapter? = null
    var lowerAdapter: MaterialAdapter? = null
    var upperAdapter : MaterialAdapter? = null
    var upperMaterialsList: java.util.ArrayList<Material> = ArrayList<Material>()
    var lowerMaterialsList = ArrayList<Material>()
    val upperFileName = "upperSavedMaterial.json"    //자꾸 fileNotFoundException (Read-only file system) 랑 permission denied 떠서 권한이 없는줄알고
    val lowerFileName = "lowerSavedMaterial.json"
    var MaterialNameArrayToSearch = ArrayList<String>()
   // var lowerMaterialNameArrayToSearch = ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(com.example.yamyam.R.layout.fragment_fridge, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // toolbar 초기화
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(com.example.yamyam.R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        //왼쪽에 검색버튼을 추가하기위해 홈버튼을 만들고 그 홈버튼의 이미지를 검색으로 바꾼다(id는 그대로 home)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.tab_search_blue)

        setHasOptionsMenu(true)

        /* set + - 버튼 클릭 리스너 */
        //setClickListenerToButtons()
        /*위 아래 리사이클러 뷰에 어댑터 붙임*/
        setAdapter()
        /* 저장된 파일에서 불러옴 */
        loadFromSavedFile()
        /* 여기서 먼저 임시로 ItemTouchHelper 를 붙여야 맨 처음 어플 실행시 이미지가 정상적으로 로딩된다 */
        setItemTouchHelper(null, null, null, null, null, -1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val nameOfMaterial : String? = data?.getStringExtra("nameOfMaterial")
        val image : Int = data!!.getIntExtra("selectedFoodImage", 0)
        val expirationDate_year=  data.getIntExtra("expirationDate_year",0)
        val expirationDate_month=  data.getIntExtra("expirationDate_month",0)
        val expirationDate_date=  data.getIntExtra("expirationDate_date",0)
        val upperOrLower = data.getIntExtra("upperOrLower", 0)

        //loadMaterialList(file)
        //나중에 변수명 바꿀것, 변수명뭐로하지
        var tmpDate = materialExpirationDate(expirationDate_year, expirationDate_month, expirationDate_date)
        /* 아이템 터치 헬퍼 붙임 */
        setItemTouchHelper(requestCode, resultCode, nameOfMaterial!!, image, tmpDate, upperOrLower)
        //json 파일에 upperMaterials 리스트를 저장하자
        upperAdapter!!.writeJSONtoFile(upperFileName)
        lowerAdapter!!.writeJSONtoFile(lowerFileName)
    }

    private fun setItemTouchHelper(requestCode: Int?, resultCode: Int?, nameOfMaterial : String?, image: Int?, expirationDate : materialExpirationDate?, upperOrLower :Int){
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
        if (resultCode == AppCompatActivity.RESULT_OK &&  upperOrLower == 0){
            //inputMaterialActivity 에서 넘긴 이름과, foodImage
            upperMaterialsList.add(Material(nameOfMaterial!!, image!!, expirationDate))
        }
        //lowerBody 에 추가
        else if(resultCode == AppCompatActivity.RESULT_OK && upperOrLower == 1){
            //inputMaterialActivity 에서 넘긴 이름과, foodImage
            lowerMaterialsList.add(Material(nameOfMaterial!!, image!!, expirationDate))
        }
    }

    /* 툴바 선택 */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.plusItem){
            val intent = Intent(activity, MaterialInputActivity::class.java)
            startActivityForResult(intent, 0)       //request Code 0은 upperBody
        }
        else if(item.itemId == R.id.minusItem){
            if (upperAdapter?.isMinusClicked == false) {     //안눌린 상태
                view?.setBackgroundColor(-0x777778)         //gray
                upperAdapter?.setIsMinusClicked(true)
                lowerAdapter?.setIsMinusClicked(true)
            }
            else if (upperAdapter?.isMinusClicked == true) { //눌린상태
                view?.setBackgroundColor(-0x1)               //white
                upperAdapter?.setIsMinusClicked(false)
                lowerAdapter?.setIsMinusClicked(false)
            }
        }
        else if(item.itemId == android.R.id.home) {       //좌측 상단 검색
            if(upperAdapter?.isSearchClicked == false) {
                MaterialNameArrayToSearch = ArrayList<String>() //초기화
                //lowerMaterialNameArrayToSearch = ArrayList<String>()
                upperAdapter?.setIsSearchClicked(true)
                lowerAdapter?.setIsSearchClicked(true)
                Toast.makeText(requireContext(), "선택하는 재료들로 레시피를 검색합니다", Toast.LENGTH_SHORT).show()
            }
            else if(upperAdapter?.isSearchClicked == true){
                upperAdapter?.setIsSearchClicked(false)
                lowerAdapter?.setIsSearchClicked(false)
                //선택된 재료들로 레시피를 검색한 검색결과 프래그먼트로 넘어감
            }
        }
        return super.onOptionsItemSelected(item)
    }

/*
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

 */

    private fun setAdapter(){
        lowerAdapter = MaterialAdapter(requireContext(), lowerMaterialsList, lowerFileName, MaterialNameArrayToSearch)
        upperAdapter = MaterialAdapter(requireContext(), upperMaterialsList, upperFileName, MaterialNameArrayToSearch)
        upperRecyclerView.adapter = upperAdapter
        lowerRecyclerView.adapter = lowerAdapter
        upperAdapter!!.notifyDataSetChanged()   //여기가 답이였네, 왜 드래그로 위치 바꿔도 안바뀌나 3일 내내 고민
        lowerAdapter!!.notifyDataSetChanged()
    }

    /* 처음에 저장된 파일에서 불러오는 함수 */
    private fun loadFromSavedFile()
    {
        /* 처음 어플을 실행하는 경우 아직 파일을 쓰지 않았으므로 */
        if(File(context?.cacheDir, upperFileName).exists()) {
            /* json 파일에서 저장되었던 material Lists 불러옴 */
            upperAdapter!!.loadMaterialList(upperFileName)
            upperAdapter!!.notifyDataSetChanged()
            //upperAdapter!!.notifyItemRangeChanged(0, upperMaterialsList.size)
            //upperRecyclerView.scrollToPosition(0);
            //upperRecyclerView.scrollBy(0,0) 이거 스왑에다 적으면 되겠는데?
            //upperRecyclerView.adapter = upperAdapter
        }

        if(File(context?.cacheDir, lowerFileName).exists()) {
            lowerAdapter!!.loadMaterialList(lowerFileName)
            lowerAdapter!!.notifyDataSetChanged()
        }
    }


    /* 인텐트로 넘겨받은 날짜 한 데 모아둘 데이터클래스 */
    data class materialExpirationDate(var year: Int, var month: Int, var date: Int)

}
