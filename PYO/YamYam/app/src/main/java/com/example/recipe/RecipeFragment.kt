package com.example.recipe

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yamyam.R
import com.example.yamyam.searchResult.SearchResultRecipeAdapter
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_recipelist.*
/*
DB에 저장되어 있는 레시피 리스트를 보여주는 Fragment
레시피 이름을 검색할 수 있는 editText UI와 버튼, 새로 레시피 편집이 가능한 버튼,
레시피 리스트를 보여주는 Firebase Recyclerview로 구성되어 있다.
 */
class RecipeFragment : Fragment() {

    private var mRecylerview : RecyclerView? = null
    private var linearLayoutManager : LinearLayoutManager? = null
    private var SearchResultAdapter : SearchResultRecipeAdapter? = null
    private var searchResultRecipeList =  ArrayList<RecipeSource>()

    lateinit var ref: DatabaseReference

    //진척도 표시
    lateinit var showProgress: ProgressBar

    //리사이클러뷰 초기화 하고 레이아웃을 초기화 해야함
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        var viewInflater: View = inflater.inflate(R.layout.fragment_recipelist, container, false)


        linearLayoutManager = LinearLayoutManager(activity)
        mRecylerview = viewInflater.findViewById(R.id.recipe_list) as RecyclerView
        mRecylerview!!.layoutManager = linearLayoutManager

        ref = FirebaseDatabase.getInstance().reference.child("recipes")

        showProgress = viewInflater.findViewById(R.id.progress_bar)

        firebaseData()

        return viewInflater
    }



    private fun firebaseData() {


        val option = FirebaseRecyclerOptions.Builder<RecipeSource>()
            .setQuery(ref, RecipeSource::class.java)
            .setLifecycleOwner(this)
            .build()


        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<RecipeSource, MyViewHolder>(option) {


            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val itemView = LayoutInflater.from(context).inflate(R.layout.cardview,parent,false)
                return MyViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: RecipeSource) {
                val placeid = getRef(position).key.toString()

                ref.child(placeid).addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    //모델의 파라미터명과 같은 DB의 해당 자료를 가져온다 파라미터명 바꿀시 주의
                    override fun onDataChange(p0: DataSnapshot) {
                        showProgress.visibility = if(itemCount == 0) View.VISIBLE else View.GONE
                        holder.itemName.text = model.name
                        if (model.imageUri!!.isEmpty()) {
                            //고른 사진이 없을때 기본 이미지
                            holder.itemImg.setImageResource(R.drawable.tomato)
                        } else {
                            Picasso.get().load(model.imageUri).into(holder.itemImg)
                        }
                        holder.itemView.setOnClickListener{
                            val intent = Intent(context, Recipe::class.java)
                            intent.putExtra("imageUri", model.imageUri)
                            intent.putExtra("name", model.name)
                            intent.putExtra("description", model.description)
                            intent.putExtra("wish", model.wish)
                            var materialsList = ArrayList<String>()
                            //TODO 여기 model.materials의 값이 안가져와진다. 그래서 재료리스트가 공백임
                            for(i in model.materials!!){
                                materialsList.add(i)
                            }
                            intent.putStringArrayListExtra("materialsList", materialsList)
                            context!!.startActivity(intent)
                        }

                    }
                })
            }
        }

        mRecylerview!!.adapter = firebaseRecyclerAdapter

        firebaseRecyclerAdapter.startListening()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editBtn: Button = view.findViewById(R.id.editBtn)

        editBtn.setOnClickListener {
            val editIntent = Intent(activity, EditRecipe::class.java)
//            startActivityForResult(editIntent,1)
            startActivity(editIntent)
        }

        var searchRecipeName : String
        searchBtn.setOnClickListener {
            searchResultRecipeList.clear()
            SearchResultAdapter = SearchResultRecipeAdapter(requireContext(), searchResultRecipeList)
            mRecylerview!!.adapter = SearchResultAdapter
            searchRecipeName = editText.text.toString()         //검색창에 작성한 레시피 이름
            SearchResultAdapter!!.notifyDataSetChanged()

            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(recipes: DataSnapshot) {
                    //작성한 이름을 firebase 에서 검색
                    searchRecipeFromFirebase(recipes, searchRecipeName)

                }
                override fun onCancelled(p0: DatabaseError) {

                }
            })
            SearchResultAdapter?.notifyDataSetChanged()
        }
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var itemName: TextView = itemView.findViewById(R.id.recipeItemName)
        internal var itemImg: ImageView = itemView.findViewById(R.id.recipeItemImg)

    }


//fun searchRecipeNameInFirebase(ref: DatabaseReference, searchRecipeName : String, searchResultRecipe : SearchResultRecipe?)
//{
//
//
//    //ref = FirebaseDatabase.getInstance().getReference().child("recipes")
//}

    /*레시피 이름으로 파이어베이스에서 검색하는 함수*/
    fun searchRecipeFromFirebase(dataSnapshot: DataSnapshot, searchRecipeName : String) {
        for(dataSnapshotChild in dataSnapshot.children){
            if(dataSnapshotChild.child("name").getValue(String::class.java) == searchRecipeName){

                var materialListInFirebase  = ArrayList<String>()
                for(material in dataSnapshotChild.child("materialsList").children){
                    materialListInFirebase.add(material.getValue(String::class.java)!!)
                }
                searchResultRecipeList.add(RecipeSource(
                    dataSnapshotChild.child("description").getValue(String::class.java)!!,
                    dataSnapshotChild.child("imageUri").getValue(String::class.java)!!,
                    materialListInFirebase,
                    dataSnapshotChild.child("name").getValue(String::class.java)!!,
                    dataSnapshotChild.child("wish").getValue(Boolean::class.java)!!
                ))
                SearchResultAdapter?.notifyDataSetChanged()
            }
        }
    }
}
