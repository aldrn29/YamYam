package com.example.fridge

/* MaterialInputActivity 에서 사용하는 어댑터

* 10.13 CategoryAdapter 정의
* */

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.yamyam.R
import kotlinx.android.synthetic.main.entry_category.view.*


class CategoryAdapter : BaseAdapter {
    var categoryList = ArrayList<Category>()
    var context: Context? = null

    constructor(context: Context?, CategoryList: ArrayList<Category>) : super() {
        this.context = context
        this.categoryList = CategoryList
    }

    override fun getCount(): Int {
        return categoryList.size
    }
    override fun getItem(position: Int): Any {
        return categoryList[position]
    }
    override fun getItemId(position: Int): Long{
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val category = this.categoryList[position]

        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var CategoryView = inflator.inflate(R.layout.entry_category, null)
        CategoryView.imgCategory.setImageResource(category.image!!)
        CategoryView.categoryName.text = category.name

        return CategoryView
    }
}