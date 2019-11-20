package com.example.recipe

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yamyam.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class RecipeFragment : Fragment() {

    private var mRecylerview : RecyclerView? = null
    private var linearLayoutManager : LinearLayoutManager? = null

    lateinit var ref: DatabaseReference

    lateinit var show_progress: ProgressBar

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        firebaseData()
//
//    }

    //리사이클러뷰 초기화 하고 레이아웃을 초기화 해야함
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        var viewInflater: View = inflater.inflate(R.layout.fragment_recipelist, container, false)


        linearLayoutManager = LinearLayoutManager(activity)
        mRecylerview = viewInflater.findViewById(R.id.recipe_list) as RecyclerView
        mRecylerview?.layoutManager = linearLayoutManager

        ref = FirebaseDatabase.getInstance().getReference().child("recipes")

        show_progress = viewInflater.findViewById(R.id.progress_bar)

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

                    override fun onDataChange(p0: DataSnapshot) {
                        show_progress.visibility = if(itemCount == 0) View.VISIBLE else View.GONE
                        holder.itemName.setText(model.name)
                        Picasso.get().load(model.img).into(holder.itemImg)

//                        holder.itemView.setOnClickListener{
//                            val intent = Intent(activity, Recipe::class.java)
//                        }

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
    }

//    private fun itemClicked(){
//        val intent = Intent(activity, Recipe::class.java)
//        startActivity(intent)
//    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var itemName: TextView = itemView.findViewById(R.id.recipeItemName)
        internal var itemImg: ImageView = itemView.findViewById(R.id.recipeItemImg)


    }



}
