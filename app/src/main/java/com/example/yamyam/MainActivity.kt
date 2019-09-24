package com.example.yamyam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_refrigerator.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refrigerator)

        // 버튼 이벤트
        btn_plus.setOnClickListener {

            val intent = Intent(this, Store::class.java)
            startActivity(intent)
        }

    }
}
