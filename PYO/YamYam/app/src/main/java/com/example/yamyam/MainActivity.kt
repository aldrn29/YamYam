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

/* 11.15 fragment 왔다갔다 해도 유지되도록 설정 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var fridgeFragment : FridgeFragment? = null
        var wishListFragment : WishListFragment? = null
        var recipeFragment : RecipeFragment? = null

        // 하단 탭부분 클릭 시 Fragment 전환
        val bottomNavigationView : BottomNavigationView = findViewById(R.id.navigationView)
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.act_fragment, FridgeFragment()).commit()

        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.fridgeItem -> {
                    val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
                    if(fridgeFragment == null) {
                        fridgeFragment = FridgeFragment()
                        transaction.add(R.id.act_fragment, fridgeFragment!!)
                    }
                    if(wishListFragment!= null) transaction.hide(wishListFragment!!)
                    if(recipeFragment != null) transaction.hide(recipeFragment!!)
                    transaction.show(fridgeFragment!!)
                    transaction.commit()
                }
                R.id.wishlistItem -> {
                    val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
                    if(wishListFragment == null) {
                        wishListFragment = WishListFragment()
                        transaction.add(R.id.act_fragment, wishListFragment!!)
                    }
                    if(fridgeFragment!= null) transaction.hide(fridgeFragment!!)
                    if(recipeFragment != null) transaction.hide(recipeFragment!!)
                    transaction.show(wishListFragment!!)
                    transaction.commit()
                }
                R.id.recipeItem -> {
                    val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
                    if(recipeFragment == null) {
                        recipeFragment = RecipeFragment()
                        transaction.add(R.id.act_fragment, recipeFragment!!)
                    }
                    if(fridgeFragment!= null) transaction.hide(fridgeFragment!!)
                    if(wishListFragment != null) transaction.hide(wishListFragment!!)
                    transaction.show(recipeFragment!!)
                    transaction.commit()
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
