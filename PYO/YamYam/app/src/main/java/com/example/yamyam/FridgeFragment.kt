package com.example.yamyam


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_fridge.*


class FridgeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_fridge, container, false)

        val upperPlusButton : Button = view.findViewById(R.id.upperPlusButton)
        val upperMinusButton : Button = view.findViewById(R.id.upperMinusButton)
        val lowerPlusButton : Button = view.findViewById(R.id.lowerPlusButton)
        val lowerMinusButton : Button = view.findViewById(R.id.lowerMinusButton)
        val temporaryButton : Button = view.findViewById(R.id.temporaryButton)

        /* + - 버튼 클릭 리스너 */
        upperPlusButton.setOnClickListener {
            val intent = Intent(activity, MaterialInputActivity::class.java)
            startActivityForResult(intent, 0)       //request Code 0은 upperBody
        }
        upperMinusButton.setOnClickListener{
            //textView.text = "마이너스 버튼 눌림"
        }

        lowerPlusButton.setOnClickListener{
            val intent = Intent(activity, MaterialInputActivity::class.java)
            startActivityForResult(intent, 1)       //requestCode 1은 lowerBody
        }
        lowerMinusButton.setOnClickListener{
            //textView.text = "마이너스 버튼 눌림"
        }

        //임시 버튼 하단 탭 구성전에 임시로 사용중 버튼 위치상 첫번째 냉동고 기입 식재료명 가려짐
        temporaryButton.setOnClickListener {
            val intent = Intent(activity, RecipeSerachActivity::class.java)
            startActivity(intent)
        }

        // Inflate the layout for this fragment
        return view
    }


    var upperAdapter: MaterialAdapter? = null
    var lowerAdapter: MaterialAdapter? = null
    var upperMaterialsList = ArrayList<Material>()
    var lowerMaterialsList = ArrayList<Material>()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val nameOfMaterial = data?.getStringExtra("nameOfMaterial")
        upperAdapter = MaterialAdapter(requireContext(), upperMaterialsList)
        lowerAdapter = MaterialAdapter(requireContext(), lowerMaterialsList)
        upperGridView.adapter = upperAdapter
        lowerGridView.adapter = lowerAdapter

        //upperBody 에 추가
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == 0){
            Toast.makeText(activity,"$nameOfMaterial 추가완료", Toast.LENGTH_SHORT).show()
            upperMaterialsList.add(Material(nameOfMaterial.toString(), R.drawable.coffee_pot))              //inputMaterialActivity 에서 넘긴 이름과, 임시 이미지
        }
        //lowerBody 에 추가
        else if(resultCode == AppCompatActivity.RESULT_OK && requestCode == 1){
            Toast.makeText(activity,"$nameOfMaterial 추가완료", Toast.LENGTH_SHORT).show()
            lowerMaterialsList.add(Material(nameOfMaterial.toString(), R.drawable.coffee_pot))
        }

    }

}
