package com.example.yamyam

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class RecipeSearchAdapter (val context: Context, val recipeList: ArrayList<RecipeData>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        /* LayoutInflater는 item을 Adapter에서 사용할 View로 부풀려주는(inflate) 역할을 한다. */
        val view: View = LayoutInflater.from(context).inflate(R.layout.activity_recipe_item, null)

        /* 위에서 생성된 view를 res-layout-main_lv_item.xml 파일의 각 View와 연결하는 과정이다. */
        val recipeImage = view.findViewById<ImageView>(R.id.recipeImage)
        val recipeName = view.findViewById<TextView>(R.id.recipeName)

        /* ArrayList<RecipeData>의 변수 recipe의 이미지와 데이터를 ImageView와 TextView에 담는다. */
//        입력요망 리소스 없음
        val recipe = recipeList[position]
        val resourceId = context.resources.getIdentifier(recipe.image, "drawable", context.packageName)
        recipeName.text = recipe.name
        recipeImage.setImageResource(resourceId)


        return view

    }

    override fun getItem(position: Int): Any {
//        return recipeList[postion]
        return 0
    }

    override fun getItemId(position: Int): Long {
    return 0
    }

    override fun getCount(): Int {
        return recipeList.size
    }

}