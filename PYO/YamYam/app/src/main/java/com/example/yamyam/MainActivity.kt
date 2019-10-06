package com.example.yamyam

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* + - 버튼 클릭 리스너 */
        upperPlusButton.setOnClickListener{
            val intent = Intent(this, InputMaterialActivity::class.java)
            startActivityForResult(intent, 0)       //request Code 0은 upperBody
        }

        upperMinusButton.setOnClickListener{
            //textView.text = "마이너스 버튼 눌림"
        }
        lowerPlusButton.setOnClickListener{
            val intent = Intent(    this, InputMaterialActivity::class.java)
            startActivityForResult(intent, 1)       //requestCode 1은 lowerBody
        }
        upperMinusButton.setOnClickListener{
            //textView.text = "마이너스 버튼 눌림"
        }

//        임시 버튼 하단 탭 구성전에 임시로 사용중 버튼 위치상 첫번째 냉동고 기입 식재료명 가려짐
        temporaryButton.setOnClickListener {
            val intent = Intent(this, RecipeSerachActivity::class.java)
            startActivity(intent)
        }
    }

    var upperAdapter: MaterialAdapter? = null
    var lowerAdapter: MaterialAdapter? = null
    var upperMaterialsList = ArrayList<Material>()
    var lowerMaterialsList = ArrayList<Material>()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val nameOfMaterial = data?.getStringExtra("nameOfMaterial")
        upperAdapter = MaterialAdapter(this, upperMaterialsList)
        lowerAdapter = MaterialAdapter(this, lowerMaterialsList)
        upperGridView.adapter = upperAdapter
        lowerGridView.adapter = lowerAdapter

        //upperBody 에 추가
        if (resultCode == RESULT_OK && requestCode == 0){
            Toast.makeText(this,"$nameOfMaterial 추가완료", Toast.LENGTH_SHORT).show()
            upperMaterialsList.add(Material(nameOfMaterial.toString(), R.drawable.coffee_pot))              //inputMaterialActivity 에서 넘긴 이름과, 임시 이미지
        }
        //lowerBody 에 추가
        else if(resultCode == RESULT_OK && requestCode == 1){
            Toast.makeText(this,"$nameOfMaterial 추가완료", Toast.LENGTH_SHORT).show()
            lowerMaterialsList.add(Material(nameOfMaterial.toString(), R.drawable.coffee_pot))
        }

    }
}
