package com.example.yamyam

/*10.13 CategoryAdapter 정의
* */

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.entry_category.view.*
import kotlinx.android.synthetic.main.entry_material.view.*



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
/*
        CategoryView.imgCategory.setOnClickListener {
            val intent = Intent(context, MaterialInputActivity::class.java)
            intent.putExtra("name", material.name)
            intent.putExtra("image", material.image!!)
            context!!.startActivity(intent)
        }
        materialView.imgMaterial.setImageResource(material.image!!)
        materialView.tvName.text = material.name!!

        return materialView
    }*/
}