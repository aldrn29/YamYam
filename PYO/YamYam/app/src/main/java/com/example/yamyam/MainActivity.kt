package com.example.yamyam

import android.content.Intent
import android.os.Build.VERSION_CODES.O
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //플러스버튼 클릭리스너
        plusButton.setOnClickListener{
            val intent = Intent(this, input_material_activity::class.java)
            startActivityForResult(intent, 1)
        }

        //마이너스버튼 클릭리스너
        minusButton.setOnClickListener{
            //textView.text = "마이너스 버튼 눌림"
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val textView = TextView(this)
        upperBody.addView(textView, 0)

        if (resultCode == RESULT_OK){
            //인텐트에서 nameOfMaterial 받아와서
            val nameOfMaterial = data?.getStringExtra("nameOfMaterial")


           // Toast.makeText(this,"$nameOfMaterial", Toast.LENGTH_SHORT).show()
            textView.text = "$nameOfMaterial"
        }

    }
}
