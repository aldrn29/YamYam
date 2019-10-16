package com.example.fridge


import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recipe.RecipeSerachActivity
import kotlinx.android.synthetic.main.fragment_fridge.*
import kotlin.collections.ArrayList



/* 10.16
임시 이미지가 아닌 MaterialInputActivity에서 선택된 foodimage가 들어가도록 변경
 */
class FridgeFragment : Fragment() {

    var upperAdapter: MaterialAdapter? = null
    var lowerAdapter: MaterialAdapter? = null
    var upperMaterialsList: java.util.ArrayList<Material> = ArrayList<Material>()
    var lowerMaterialsList = ArrayList<Material>()
    var upperMinusButtonClicked: Boolean = false
    var lowerMinusButtonClicked: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(com.example.yamyam.R.layout.fragment_fridge, container, false)
        // Inflate the layout for this fragment
        //Toast.makeText(activity,"들어옴0", Toast.LENGTH_SHORT).show()//
        if(savedInstanceState != null){

        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val upperPlusButton : Button = view.findViewById(com.example.yamyam.R.id.upperPlusButton)
        val upperMinusButton : Button = view.findViewById(com.example.yamyam.R.id.upperMinusButton)
        val lowerPlusButton : Button = view.findViewById(com.example.yamyam.R.id.lowerPlusButton)
        val lowerMinusButton : Button = view.findViewById(com.example.yamyam.R.id.lowerMinusButton)
        val temporaryButton : Button = view.findViewById(com.example.yamyam.R.id.temporaryButton)

        /* + - 버튼 클릭 리스너 */
        upperPlusButton.setOnClickListener {
            val intent = Intent(activity, MaterialInputActivity::class.java)
            startActivityForResult(intent, 0)       //request Code 0은 upperBody
        }

        upperMinusButton.setOnClickListener {
            Toast.makeText(activity, "마이너스 버튼 눌림", Toast.LENGTH_SHORT).show()
            if (upperMinusButtonClicked == false) {  //안눌린 상태
                upperMinusButton.setBackgroundColor(-0x777778)  //gray
            } else if (upperMinusButtonClicked == true) {    //눌린상태
                upperMinusButton.setBackgroundColor(-0x1)   //white
            }
            upperMinusButtonClicked = true
        }

        lowerPlusButton.setOnClickListener{
            val intent = Intent(activity, MaterialInputActivity::class.java)
            startActivityForResult(intent, 1)       //requestCode 1은 lowerBody
        }

        lowerMinusButton.setOnClickListener{
            Toast.makeText(activity,"마이너스 버튼 눌림", Toast.LENGTH_SHORT).show()
            if(lowerMinusButtonClicked == false){
                lowerMinusButton.setBackgroundColor(-0x777778)
            } else if(lowerMinusButtonClicked == true) {
                lowerMinusButton.setBackgroundColor(-0x1)
            }
            lowerMinusButtonClicked = true
        }

        //임시 버튼 하단 탭 구성전에 임시로 사용중 버튼 위치상 첫번째 냉동고 기입 식재료명 가려짐
        temporaryButton.setOnClickListener {
            val intent = Intent(activity, RecipeSerachActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val nameOfMaterial = data?.getStringExtra("nameOfMaterial")
        val image: Int = data!!.getIntExtra("selectedFoodImage", 0)
        upperAdapter = MaterialAdapter(requireContext(), upperMaterialsList)
        lowerAdapter = MaterialAdapter(requireContext(), lowerMaterialsList)
        upperGridView.adapter = upperAdapter
        lowerGridView.adapter = lowerAdapter

        //upperBody 에 추가
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == 0){
            //inputMaterialActivity 에서 넘긴 이름과, foodImage
                upperMaterialsList.add(Material(nameOfMaterial.toString(), image))
            Toast.makeText(activity,"$nameOfMaterial 추가완료", Toast.LENGTH_SHORT).show()
        }
        //lowerBody 에 추가
        else if(resultCode == AppCompatActivity.RESULT_OK && requestCode == 1){
            //inputMaterialActivity 에서 넘긴 이름과, foodImage
            lowerMaterialsList.add(Material(nameOfMaterial.toString(), image))
            Toast.makeText(activity,"$nameOfMaterial 추가완료", Toast.LENGTH_SHORT).show()
        }
        //삭제
        upperGridView.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                //삭제모드(마이너스 버튼 눌렸을 때)
                if(upperMinusButtonClicked == true) {
                    upperAdapter!!.removeItem(position)
                    Toast.makeText(activity, "$nameOfMaterial 제거됨", Toast.LENGTH_SHORT).show()
                    upperMinusButtonClicked = false
                    upperMinusButton.setBackgroundColor(-0x1)
                }//그냥 선택모드
                else if(upperMinusButtonClicked == false) {
                }

            }
        }
        lowerGridView.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if(lowerMinusButtonClicked == true) {
                    lowerAdapter!!.removeItem(position)
                    Toast.makeText(activity, "$nameOfMaterial 제거됨", Toast.LENGTH_SHORT).show()
                    lowerMinusButtonClicked = false
                    upperMinusButton.setBackgroundColor(-0x1)
                }
                else if(lowerMinusButtonClicked==false){
                }
            }
        }
    }

    //얘는 다른 액티비티로 넘어갈 때 실행됨
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Toast.makeText(activity,"저장됨", Toast.LENGTH_SHORT).show()//
        outState.putParcelableArrayList("upperMaterialsList", upperMaterialsList)
    }



    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
       // Toast.makeText(activity,"저장됨1", Toast.LENGTH_SHORT).show()
        //Toast.makeText(activity,"들어옴1", Toast.LENGTH_SHORT).show()//
    }

}
