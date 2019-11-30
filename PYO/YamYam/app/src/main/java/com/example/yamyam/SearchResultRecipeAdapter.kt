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
import com.example.recipe.RecipeSource
import com.squareup.picasso.Picasso


class SearchResultRecipeAdapter (val context: Context, private val searchReusultRecipeList : ArrayList<RecipeSource>) : RecyclerView.Adapter<SearchResultRecipeAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val searchResultRecipeView = inflator.inflate(R.layout.cardview, null)
        return Holder(searchResultRecipeView)
    }
    override fun getItemCount(): Int {
        return searchReusultRecipeList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(searchReusultRecipeList[position])
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        private val searchResultRecipeImg = itemView?.findViewById<ImageView>(R.id.recipeItemImg)
        private val searchResultRecipeName = itemView?.findViewById<TextView>(R.id.recipeItemName)

        /* bind 함수는 ViewHolder 와 클래스의 각 변수를 연동하는 역할을 한다 */
        fun bind(recipe: RecipeSource) {
            if(recipe.imageUri!!.isEmpty()){    //이미지 등록이 안되어있으면 기본이미지
                searchResultRecipeImg!!.setImageResource(R.drawable.tomato)
            }else {
                Picasso.get().load(recipe.imageUri).into(searchResultRecipeImg)
            }
            searchResultRecipeName?.text = recipe.name
            //Toast.makeText(itemView.context, "${recipe.name}", Toast.LENGTH_SHORT).show()

            itemView.setOnClickListener{

            }
        }
    }
}