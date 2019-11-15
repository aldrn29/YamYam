package com.example.yamyam

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import android.view.MenuItem
import android.widget.Toast
import com.example.fridge.FridgeFragment
import com.example.recipe.RecipeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_fridge.*


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
                    val tr : FragmentTransaction = supportFragmentManager.beginTransaction()
                    tr.add(R.id.act_fragment, FridgeFragment())
                    tr.commit()
                }
                R.id.wishlistItem -> {
                    val tr : FragmentTransaction = supportFragmentManager.beginTransaction()
                    tr.add(R.id.act_fragment, WishListFragment())
                    tr.commit()
                }
                R.id.recipeItem -> {
                    val tr : FragmentTransaction = supportFragmentManager.beginTransaction()
                    tr.add(R.id.act_fragment, RecipeFragment())
                    tr.commit()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_actionbar, menu)
        return super.onCreateOptionsMenu(menu)
    }



}
