package com.example.yamyam

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.fridge.FridgeFragment
import com.example.recipe.RecipeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

/* 11.15 fragment 왔다갔다 해도 유지되도록 설정
*  11.25 기존에는 show 와 hide 를 이용해서 fridegeFragment 를 유지 했었지만
*        gson 을 통해서 파일에 읽고 쓰고 하므로
*        fridgeFragment 를 아예 새로 생성해서 표시하는게 초기화 관리에 용이하다*/
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
                    fridgeFragment = FridgeFragment()
                    transaction.add(R.id.act_fragment, fridgeFragment!!)
                    if(wishListFragment!= null)
                        transaction.hide(wishListFragment!!)
                    if(recipeFragment != null)
                        transaction.hide(recipeFragment!!)
                    transaction.show(fridgeFragment!!)
                    transaction.commit()
                }
                R.id.wishlistItem -> {
                    val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
                    if(wishListFragment == null) {
                        wishListFragment = WishListFragment()
                        transaction.add(R.id.act_fragment, wishListFragment!!)
                    }
                    if(fridgeFragment!= null)
                        transaction.hide(fridgeFragment!!)
                    if(recipeFragment != null)
                        transaction.hide(recipeFragment!!)
                    transaction.show(wishListFragment!!)
                    transaction.commit()
                }
                R.id.recipeItem -> {
                    val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
                    if(recipeFragment == null) {
                        recipeFragment = RecipeFragment()
                        transaction.add(R.id.act_fragment, recipeFragment!!)
                    }
                    if(fridgeFragment!= null)
                        transaction.hide(fridgeFragment!!)
                    if(wishListFragment != null)
                        transaction.hide(wishListFragment!!)
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
