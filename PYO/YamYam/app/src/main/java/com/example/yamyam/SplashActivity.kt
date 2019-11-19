package com.example.yamyam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception
import android.content.Intent

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            Thread.sleep(1000)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        catch (e: Exception) {
            return
        }
    }
}