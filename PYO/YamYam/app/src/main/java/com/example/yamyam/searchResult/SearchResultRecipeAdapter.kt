package com.example.yamyam.searchResult

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe.RecipeSource
import com.squareup.picasso.Picasso

/*
    12.10 아이템을 클릭하면 액티비티(상세 레시피)가 나타나도록 설정
 */






class SearchResultRecipeAdapter (val context: Context, private val searchReusultRecipeList : ArrayList<RecipeSource>) : RecyclerView.Adapter<SearchResultRecipeAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val searchResultRecipeView = inflator.inflate(com.example.yamyam.R.layout.cardview, null)
        return Holder(searchResultRecipeView)
    }
    override fun getItemCount(): Int {
        return searchReusultRecipeList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(searchReusultRecipeList[position])
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        private val searchResultRecipeImg = itemView?.findViewById<ImageView>(com.example.yamyam.R.id.recipeItemImg)
        private val searchResultRecipeName = itemView?.findViewById<TextView>(com.example.yamyam.R.id.recipeItemName)

        /* bind 함수는 ViewHolder 와 클래스의 각 변수를 연동하는 역할을 한다 */
        fun bind(recipe: RecipeSource) {
            if (recipe.imageUri!!.isEmpty()) {
                //이미지 등록이 안되어있으면 기본이미지
                searchResultRecipeImg!!.setImageResource(com.example.yamyam.R.drawable.tomato)
            } else {
                Picasso.get().load(recipe.imageUri).into(searchResultRecipeImg)
            }
            searchResultRecipeName?.text = recipe.name
            //Toast.makeText(itemView.context, "${recipe.name}", Toast.LENGTH_SHORT).show()

            /* 뷰의 아이템을 클릭하면 레시피 화면이 뜨도록 */
            itemView.setOnClickListener {
                val intent = Intent(context, RecipeClickedActivity::class.java) //클릭하면 나타나는 액티비티로 넘길 인텐트

                intent.putExtra("description", recipe.description)
                intent.putExtra("imageUri", recipe.imageUri)
                //재료들 쪼개서 ArrayList<String> 에 집어넣고 intent 에 담는다
                val materialsList = ArrayList<String>()
                for(i in recipe.materials!!){
                    materialsList.add(i)
                }
                intent.putStringArrayListExtra("materialsList", materialsList)
                intent.putExtra("name", recipe.name)
                intent.putExtra("wish", recipe.wish)
                //잉 어댑터에서 액티비티 호출이 안되는줄 알았는데 되네?
                context.startActivity(intent)

            }
        }
    }


}