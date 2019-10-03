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
            val intent = Intent(this, InputMaterialActivity::class.java)
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val upperTextView = TextView(this)
        val lowerTextView = TextView(this)
        upperBody.addView(upperTextView, 0)
        lowerBody.addView(lowerTextView, 0)

        //upperBody 에 추가
        if (resultCode == RESULT_OK && requestCode == 0){
            val nameOfMaterial = data?.getStringExtra("nameOfMaterial")

            Toast.makeText(this,"$nameOfMaterial 추가완료", Toast.LENGTH_SHORT).show()
            upperTextView.text = "$nameOfMaterial"
        }

        //lowerBody 에 추가
        else if(resultCode == RESULT_OK && requestCode == 1){
            //인텐트에서 nameOfMaterial 받아와서
            val nameOfMaterial = data?.getStringExtra("nameOfMaterial")

            Toast.makeText(this,"$nameOfMaterial 추가완료", Toast.LENGTH_SHORT).show()
            lowerTextView.text = "$nameOfMaterial"
        }

    }
}
