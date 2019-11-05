package com.example.fridge

/*10.07 MaterialAdapter 정의
* 일단 name 과 image 만 intent 에 담아서 넘기도록 정의했는데 필요에 따라 늘릴 수 있음*/

import android.content.Context
import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yamyam.MainActivity
import com.example.yamyam.R
import kotlinx.android.synthetic.main.entry_material.view.*



class tmpMaterialAdapter (val context: Context, val upperMaterialsList : ArrayList<Material>) : RecyclerView.Adapter<tmpMaterialAdapter.Holder>() {
    //화면을 최초 로딩하여 만들어진 View가 없는 경우, xml파일을 inflate하여 ViewHolder를 생성한다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        //val materialView = LayoutInflater.from(context).inflate(R.layout.entry_material, parent, false)
        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var materialView = inflator.inflate(R.layout.entry_material, null)
        return Holder(materialView)
    }
    //RecyclerView로 만들어지는 item의 총 개수를 반환한다.
    override fun getItemCount(): Int {
        return upperMaterialsList.size
    }
    //위의 onCreateViewHolder에서 만든 view와 실제 입력되는 각각의 데이터를 연결한다.
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(upperMaterialsList[position], context)
    }


    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val materialImg = itemView?.findViewById<ImageView>(R.id.imgMaterial)
        val materialName = itemView?.findViewById<TextView>(R.id.nameMaterial)

        //bind 함수는 ViewHolder와 클래스의 각 변수를 연동하는 역할을 한다
        fun bind(material: Material, context: Context) {
            //materialView.imgMaterial.setImageResource(material.image!!)
            //materialView.nameMaterial.text = material.name!!
            materialImg?.setImageResource(material.image!!)
            materialName?.text = material.name
        }
    }

    // 아이템삭제함수
    fun removeItem(position : Int){
        upperMaterialsList.removeAt(position)
        notifyDataSetChanged()
    }
}