//package com.example.recipe
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.yamyam.R
//import kotlinx.android.synthetic.main.activity_recipe_item.view.*
//
////리사이클러뷰 어뎁터 타입안에 홀더가 들어간다. 여기선 이너클래스로 만들었으므로 RecipeListAdapter.Holder
//class RecipeListAdapter(val context: Context,
//                        private val recipeList: ArrayList<RecipeSource>,
//                        private val itemClickListener: (RecipeSource) -> Unit
//) : RecyclerView.Adapter<RecipeListAdapter.Holder>() {
//
//    //화면을 최초 로딩하여 만들어진 뷰가 없는경우 xml파일을 inflate하여 뷰홀더를 생성
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//
//        val view = LayoutInflater.from(context).inflate(R.layout.activity_recipe_item, parent, false)
//        return Holder(view)
//    }
//
////RecyclerView로 만들어지는 item의 총 개수 반환
//    override fun getItemCount(): Int {
//        return recipeList.size
//    }
////위에서 만들어진 뷰와 실제 입력되는 각각의 데이터를 연결한다.
//    override fun onBindViewHolder(holder: Holder, position: Int) {
//    holder.bind(recipeList[position], context, itemClickListener)
//}
//
//    //xml 파일의 View와 데이터를 연결하는 핵심 역할을 하는 메소드
//    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        //bind 함수는 ViewHolder와 클래스의 각 변수를 연동하는 역할을 함
//        fun bind (recipeSource: RecipeSource, context: Context, itemClickListener: (RecipeSource) -> Unit) {
//
//            //foodImg의 setImageResource에 들어갈 이미지의 id를 파일명(String)으로 찾고,
//            //이미지가 없는 경우 안드로이드 기본 아이콘을 표시한다
//            if (recipeSource.img
//                != null) {
//                val resourceId = context.resources.getIdentifier(recipeSource.img, "drawable", context.packageName)
//                itemView.recipeItemImg.setImageResource(resourceId)
//            } else {
//                itemView.recipeItemImg.setImageResource(R.mipmap.ic_launcher)
//            }
//
//
//            itemView.recipeItemName.text = recipeSource.name
//            itemView.setOnClickListener{itemClickListener(recipeSource)}
//        }
//
//    }
//
//}




// 지우지 마시오