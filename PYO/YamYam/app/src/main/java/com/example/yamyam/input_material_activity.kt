package com.example.yamyam

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_input_material_activity.*

class input_material_activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_material_activity)


        val resultIntent = Intent(this,MainActivity::class.java)

        addButton.setOnClickListener{
            //"nameOfMaterial" to material_text.text.toString()
            resultIntent.putExtra("nameOfMaterial", material_text.text.toString())
            setResult(RESULT_OK, resultIntent)

            finish()
        }
    }
}
