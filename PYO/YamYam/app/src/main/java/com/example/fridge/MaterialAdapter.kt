package com.example.fridge

/*10.07 notUseMaterialAdapter 정의
* 일단 name 과 image 만 intent 에 담아서 넘기도록 정의했는데 필요에 따라 늘릴 수 있음
* 11.10~15 아이템 스왑처리
* 11.15 bind에서 아이템 삭제처리*/

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.yamyam.R
import kotlin.collections.ArrayList


class MaterialAdapter (val context: Context, private val upperMaterialsList : ArrayList<Material>) : RecyclerView.Adapter<MaterialAdapter.Holder>() {
    //화면을 최초 로딩하여 만들어진 View 가 없는 경우, xml 파일을 inflate 하여 ViewHolder 를 생성한다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        //val materialView = LayoutInflater.from(context).inflate(R.layout.entry_material, parent, false)
        val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val materialView = inflator.inflate(R.layout.entry_material, null)
        return Holder(materialView)
    }
    //RecyclerView 로 만들어지는 item 의 총 개수를 반환한다.
    override fun getItemCount(): Int {
        return upperMaterialsList.size
    }
    //위의 onCreateViewHolder 에서 만든 view 와 실제 입력되는 각각의 데이터를 연결한다.
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(upperMaterialsList[position], context)
    }


    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val materialImg = itemView?.findViewById<ImageView>(R.id.imgMaterial)
        val materialName = itemView?.findViewById<TextView>(R.id.nameMaterial)

        /* bind 함수는 ViewHolder 와 클래스의 각 변수를 연동하는 역할을 한다 */
        fun bind(material: Material, context: Context) {
            materialImg?.setImageResource(material.image!!)
            materialName?.text = material.name

            /*여기서 아이템 삭제 처리*/
            itemView.setOnClickListener{
                if(isClicked == true) {
                    Toast.makeText(itemView.context, "클릭함", Toast.LENGTH_SHORT).show()
                    removeItem(material)
                }
            }
        }
    }

    /* isClicked 는 버튼의 눌림 여부를 알아서, 마이너스 버튼이 눌린 상태에서 클릭시에만 아이템을 삭제하기 위함*/
    var isClicked : Boolean = false
    fun setIsClicked(isClicked :Boolean){
        this.isClicked = isClicked
    }

    /* 아이템삭제함수*/
    fun removeItem(rmMaterial: Material){
        upperMaterialsList.remove(rmMaterial)
        notifyDataSetChanged()
    }

    /*아이템 스왑*/
    fun swapItems(fromPosition: Int, toPosition: Int) {
        var tmpMaterial : Material
        if (fromPosition < toPosition) {
            tmpMaterial = upperMaterialsList[fromPosition]
            for (i in fromPosition until toPosition) {
                upperMaterialsList[i] = upperMaterialsList[i + 1]       //한칸씩 뒤로 미루고
            }
            upperMaterialsList[toPosition] = tmpMaterial
        } else {
            tmpMaterial =upperMaterialsList[fromPosition]
            for(i in fromPosition downTo  toPosition + 1) {
                upperMaterialsList[i] = upperMaterialsList[i-1]
            }
            upperMaterialsList[toPosition] = tmpMaterial
        }
        notifyItemMoved(fromPosition, toPosition)
    }

}