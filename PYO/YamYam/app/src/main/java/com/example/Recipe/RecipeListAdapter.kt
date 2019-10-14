package com.example.Recipe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.yamyam.R

class RecipeListAdapter(val context: Context, val recipeList: ArrayList<Recipe>) : BaseAdapter() {
    //xml 파일의 View와 데이터를 연결하는 핵심 역할을 하는 메소드
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.activity_recipe_item, null)

        //위의 뷰를 res-layout-main_lv_item.xml 파일의 각 뷰와 연결하는 과정
        val recipeImage = view.findViewById<ImageView>(R.id.recipeImage)
        val recipeName = view.findViewById<TextView>(R.id.recipeName)

        // ArrayList<Recipe>의 변수 Recipe의 이미지와 데이터를 이미지뷰와 텍스트뷰에 담는다
        val recipe = recipeList[position]
        val resourceId = context.resources.getIdentifier(recipe.image, "drawable", context.packageName)
        recipeImage.setImageResource(resourceId)
        recipeName.text = recipe.name

        return view
    }

    //해당 위치의 item을 선택하는 메소드 position은 위치를 의미 0번째 : getItem(0)
    override fun getItem(position: Int): Any {
        return recipeList[position]
    }

    //해당 위치의 item id를 반환하는 메소드 필요치 않을시 0을 반환
    override fun getItemId(position: Int): Long {
        return 0
    }

    //리스트뷰에 속한 아이템 전체 수를 반환
    override fun getCount(): Int {
        return recipeList.size
    }

}