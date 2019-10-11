package com.example.yamyam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // 하단 탭부분 클릭 시 Fragment 전환
        val bottomNavigationView : BottomNavigationView = findViewById(R.id.navigationView)
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.act_fragment, FridgeFragment()).commit()

        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.fridgeItem -> {
                    val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
                    transaction.add(R.id.act_fragment, FridgeFragment())
                    transaction.commit()
                }
                R.id.wishlistItem -> {
                    val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
                    transaction.add(R.id.act_fragment, WishListFragment())
                    transaction.commit()
                }
                R.id.searchItem -> {
                    val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
                    transaction.add(R.id.act_fragment, SearchFragment())
                    transaction.commit()
                }
                R.id.tipItem -> {
                    val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
                    transaction.add(R.id.act_fragment, TipFragment())
                    transaction.commit()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

    }

}
