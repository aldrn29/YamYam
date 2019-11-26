package com.example.yamyam

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.fridge.MaterialAdapter


class SearchResultRecipeAdapter (val context: Context, private val searchReusultRecipeList : ArrayList<SearchResultRecipe>) : RecyclerView.Adapter<SearchResultRecipeAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val searchResultRecipeView = inflator.inflate(R.layout.cardview, null)
        return Holder(searchResultRecipeView)
    }
    override fun getItemCount(): Int {
        return searchReusultRecipeList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(searchReusultRecipeList[position], context, searchReusultRecipeList)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val searchResultRecipeImg = itemView?.findViewById<ImageView>(R.id.recipeItemImg)
        val searchResultRecipeName = itemView?.findViewById<TextView>(R.id.recipeItemName)

        /* bind 함수는 ViewHolder 와 클래스의 각 변수를 연동하는 역할을 한다 */
        fun bind(recipe: SearchResultRecipe, context: Context, SearchResultReipeLists: ArrayList<SearchResultRecipe>) {
            //searchResultRecipeImg?.setImageResource(recipe.image!!)
            searchResultRecipeName?.text = recipe.name
            //Toast.makeText(itemView.context, "${recipe.name}", Toast.LENGTH_SHORT).show()

            itemView.setOnClickListener{

            }
        }
    }
}

/*
class SearchResultRecipeAdapter : BaseAdapter {
    var SearchResultRecipeList = ArrayList<SearchResultRecipe>()
    var context: Context? = null

    constructor(context: Context?, RecipeList: ArrayList<SearchResultRecipe>) : super() {
        this.context = context
        this.SearchResultRecipeList = RecipeList
    }
    override fun getCount(): Int {
        return SearchResultRecipeList.size
    }

    override fun getItem(position: Int): Any {
        return SearchResultRecipeList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val SearchResultRecipe = this.SearchResultRecipeList[position]
        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var SearchRecipeRecyclerView = inflator.inflate(R.layout.cardview, null)

        //SearchRecipeRecyclerView.recipeItemImg.setImageResource(recipe.recipeImg)
        SearchRecipeRecyclerView.recipeItemName.text = SearchResultRecipe.name

        /*
        val category = this.categoryList[position]

        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var CategoryView = inflator.inflate(R.layout.entry_category, null)
        CategoryView.imgCategory.setImageResource(category.image!!)
        CategoryView.categoryName.text = category.name

        return CategoryView
         */
        return SearchRecipeRecyclerView
    }


}

 */