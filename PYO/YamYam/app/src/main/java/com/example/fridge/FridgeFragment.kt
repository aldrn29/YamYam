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


/* 10.16
임시 이미지가 아닌 MaterialInputActivity에서 선택된 foodimage가 들어가도록 변경
11.15 코드정리, 위아래 모두 리사이클러뷰로 변경
 */
class FridgeFragment : Fragment() {

    //var upperAdapter: notUseMaterialAdapter? = null
    var lowerAdapter: MaterialAdapter? = null
    var upperAdapter : MaterialAdapter? = null
    var upperMaterialsList: java.util.ArrayList<Material> = ArrayList<Material>()
    var lowerMaterialsList = ArrayList<Material>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(com.example.yamyam.R.layout.fragment_fridge, container, false)

        return view
    }

    /*
    // 버그 - 아이콘이 계속 추가됨
    // 툴바로 바꿔서 작업해보기 (액션바 비활성화)
    //enable options menu in this fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    //inflate the menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(com.example.yamyam.R.menu.menu_actionbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //handle item clicks of menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            com.example.yamyam.R.id.plusItem -> {
                Toast.makeText(activity, "추가", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    */

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

            // RecipeFragment로 화면이동
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
        val image: Int = data!!.getIntExtra("selectedFoodImage", 0)

        /* 아이템 터치 헬퍼 붙임 */
        setItemTouchHelper(requestCode, resultCode, nameOfMaterial!!, image)
    }


    private fun setItemTouchHelper(requestCode: Int, resultCode: Int, nameOfMaterial : String, image: Int){
        /* MaterialItemTouchHelper 에 callback 을 등록, recycler 뷰에 붙여줌
        *  상하좌우 드래그설정
        sapnCount 가 열 개수인듯*/
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
            upperMaterialsList.add(Material(nameOfMaterial, image))
            Toast.makeText(activity,"$nameOfMaterial 추가완료", Toast.LENGTH_SHORT).show()
        }
        //lowerBody 에 추가
        else if(resultCode == AppCompatActivity.RESULT_OK && requestCode == 1){
        //inputMaterialActivity 에서 넘긴 이름과, foodImage
        lowerMaterialsList.add(Material(nameOfMaterial, image))
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
        upperAdapter!!.notifyDataSetChanged()   //이새끼 여기가 답이였네, 왜 드래그로 위치 바꿔도 안바뀌나 3일 내내 고민
    }

}
