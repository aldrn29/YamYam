package com.example.yamyam

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import androidx.fragment.app.FragmentTransaction
import android.view.MenuItem
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.annotation.NonNull
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView


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
            val intent = Intent(    this, InputMaterialActivity::class.java)
            startActivityForResult(intent, 1)       //requestCode 1은 lowerBody
        }
        upperMinusButton.setOnClickListener{
            //textView.text = "마이너스 버튼 눌림"
        }

        //임시 버튼 하단 탭 구성전에 임시로 사용중 버튼 위치상 첫번째 냉동고 기입 식재료명 가려짐
        temporaryButton.setOnClickListener {
            val intent = Intent(this, RecipeSerachActivity::class.java)
            startActivity(intent)
        }

        // 하단 탭부분 클릭 시 Fragment 전환
        val bottomNavigationView : BottomNavigationView = findViewById(R.id.navigationView)
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.act_fragment, FridgeFragment()).commit()


        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.fridgeItem -> {
                    Toast.makeText(this, "첫번째", Toast.LENGTH_SHORT).show()
                    val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
                    transaction.add(R.id.act_fragment, FridgeFragment())
                    transaction.commit()
                }
                R.id.heartItem -> {
                    Toast.makeText(this, "두번째", Toast.LENGTH_SHORT).show()
                    val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
                    transaction.add(R.id.act_fragment, HeartFragment())
                    transaction.commit()
                }
                R.id.searchItem -> {
                    Toast.makeText(this, "세번째", Toast.LENGTH_SHORT).show()
                    val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
                    transaction.add(R.id.act_fragment, SearchFragment())
                    transaction.commit()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

    }
    var upperAdapter: MaterialAdapter? = null
    var lowerAdapter: MaterialAdapter? = null
    var upperMaterialsList = ArrayList<Material>()
    var lowerMaterialsList = ArrayList<Material>()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val nameOfMaterial = data?.getStringExtra("nameOfMaterial")
        upperAdapter = MaterialAdapter(this, upperMaterialsList)
        lowerAdapter = MaterialAdapter(this, lowerMaterialsList)
        upperGridView.adapter = upperAdapter
        lowerGridView.adapter = lowerAdapter

        //upperBody 에 추가
        if (resultCode == RESULT_OK && requestCode == 0){
            Toast.makeText(this,"$nameOfMaterial 추가완료", Toast.LENGTH_SHORT).show()
            upperMaterialsList.add(Material(nameOfMaterial.toString(), R.drawable.coffee_pot))              //inputMaterialActivity 에서 넘긴 이름과, 임시 이미지
        }
        //lowerBody 에 추가
        else if(resultCode == RESULT_OK && requestCode == 1){
            Toast.makeText(this,"$nameOfMaterial 추가완료", Toast.LENGTH_SHORT).show()
            lowerMaterialsList.add(Material(nameOfMaterial.toString(), R.drawable.coffee_pot))
        }

    }
}
