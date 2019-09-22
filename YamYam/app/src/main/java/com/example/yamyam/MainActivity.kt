package com.example.yamyam

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val textView = TextView(this)
        //플러스버튼 클릭리스너
        plusButton.setOnClickListener{
            textView.text = "플러스 버튼 눌림"
        }
        //마이너스버튼 클릭리스너
        minusButton.setOnClickListener{
            textView.text = "마이너스 버튼 눌림"
        }
        upperBody.addView(textView, 0)
    }



}
