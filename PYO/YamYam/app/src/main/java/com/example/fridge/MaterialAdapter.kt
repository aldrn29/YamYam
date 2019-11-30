package com.example.fridge

/*10.07 notUseMaterialAdapter 정의
* 일단 name 과 image 만 intent 에 담아서 넘기도록 정의했는데 필요에 따라 늘릴 수 있음
* 11.10~15 아이템 스왑처리
* 11.15 bind 에서 아이템 삭제처리
* 11.18 gson 을 사용하여 Material write, load 하는 함수 Adapter 에 정의
* 11.19 유통기한을 체크해서 남은 기간에 따른 배경색 변경 함수
*       bind 에서 유통기한 체크
*       유통기한 체크함수 배경색이 i-1따라가는 문제 해결
* 11.24 매개변수에 검색을 위한 String array list 하나 더 선언해서 검색을 위한 Material 의 이름을 넣는다
*       재료가 선택되었을 경우 배경색을 white 로 바꿈
* */

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.yamyam.R
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class MaterialAdapter (val context: Context, private val MaterialsList : ArrayList<Material>, private val fileName: String, var materialNameArrayToSearch: ArrayList<String>) : RecyclerView.Adapter<MaterialAdapter.Holder>() {
    //화면을 최초 로딩하여 만들어진 View 가 없는 경우, xml 파일을 inflate 하여 ViewHolder 를 생성한다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        //val materialView = LayoutInflater.from(context).inflate(R.layout.entry_material, parent, false)
        val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val materialView = inflator.inflate(R.layout.entry_material, null)
        val holder = Holder(materialView)
        return holder
    }
    //RecyclerView 로 만들어지는 item 의 총 개수를 반환한다.
    override fun getItemCount(): Int {
        return MaterialsList.size
    }
    //위의 onCreateViewHolder 에서 만든 view 와 실제 입력되는 각각의 데이터를 연결한다.
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(MaterialsList[position], context, MaterialsList)
        //여기서 유통기한 체크하니까 이상하게 꼬이던게 없어졌네? 응아니야~
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val materialImg = itemView?.findViewById<ImageView>(R.id.imgMaterial)
        val materialName = itemView?.findViewById<TextView>(R.id.nameMaterial)

        /* bind 함수는 ViewHolder 와 클래스의 각 변수를 연동하는 역할을 한다 */
        fun bind(material: Material, context: Context, MaterialsList: ArrayList<Material>) {
            materialImg?.setImageResource(material.image!!)
            materialName?.text = material.name
            checkExpirationDate(material, itemView)

            itemView.setOnClickListener{
                /*여기서 아이템 삭제 처리*/
                if(isMinusClicked == true) {
                    //Toast.makeText(itemView.context, "클릭함", Toast.LENGTH_SHORT).show()
                    //checkExpirationDateList(MaterialsList, itemView)
                    removeItem(material)
                }
                //검색버튼이 눌렸을 경우 클릭된 재료의 이름을 배열에 넣는다
                else if(isSearchClicked == true) {
                    if(material.isSelected == false) {
                        materialNameArrayToSearch.add(material.name.toString())
                        //Toast.makeText(itemView.context, "${materialNameArrayToSearch}", Toast.LENGTH_SHORT).show()
                        itemView.setBackgroundColor(Color.WHITE)
                        material.isSelected = true
                    }
                    //재료를 선택했는데 재선택 할 경우 재료의 이름을 배열에서 빼고 백그라운드 색상 원래대로
                    else if(material.isSelected == true) {
                        materialNameArrayToSearch.remove(material.name.toString())
                        //Toast.makeText(itemView.context, "${materialNameArrayToSearch}", Toast.LENGTH_SHORT).show()
                        checkExpirationDate(material, itemView)
                        material.isSelected = false
                    }
                }
            }
            checkExpirationDate(material, itemView)
        }
    }

    /* isClicked 는 버튼의 눌림 여부를 알아서, 마이너스 버튼이 눌린 상태에서 클릭시에만 아이템을 삭제하기 위함*/
    var isMinusClicked : Boolean = false
    fun setIsMinusClicked(isMinusClicked :Boolean){
        this.isMinusClicked = isMinusClicked
    }

    var isSearchClicked : Boolean = false
    fun setIsSearchClicked(isSearchClicked: Boolean){
        this.isSearchClicked = isSearchClicked
    }

    /* 아이템삭제함수*/
    fun removeItem(rmMaterial: Material){
        MaterialsList.remove(rmMaterial)
        //삭제 결과를 또 jsonFIle 에 저장
        //얘가 덮어쓰기를 하나? 그런듯
        notifyDataSetChanged()
        writeJSONtoFile(fileName)
    }

    /*아이템 스왑*/
    fun swapItems(fromPosition: Int, toPosition: Int) {
        val tmpMaterial : Material
        if (fromPosition < toPosition) {
            tmpMaterial = MaterialsList[fromPosition]
            for (i in fromPosition until toPosition) {
                MaterialsList[i] = MaterialsList[i + 1]       //한칸씩 뒤로 미루고
            }
            MaterialsList[toPosition] = tmpMaterial
        } else {
            tmpMaterial = MaterialsList[fromPosition]
            for(i in fromPosition downTo  toPosition + 1) {
                MaterialsList[i] = MaterialsList[i-1]
            }
            MaterialsList[toPosition] = tmpMaterial
        }
        notifyItemMoved(fromPosition, toPosition)
        writeJSONtoFile(fileName)
    }

    /* JSONFile에서 load 하는 함수 */
    fun loadMaterialList(fileName: String){
        val gson = Gson()
        //Read the json file
        val file = File(context.cacheDir, fileName)
        val bufferedReader: BufferedReader = file.bufferedReader()
        //bufferedReader 에서 텍스트를 읽고 inputString 에 저장
        val inputString = bufferedReader.use { it.readText() }
        //json 파일에 저장되어 있던 String 들을 다시 Material 로
        val restoredMaterial = gson.fromJson(inputString, Array<Material>::class.java)

        //load
        for(i in restoredMaterial){
            MaterialsList.add(i)
        }
        notifyDataSetChanged()
    }

    /* JSONFile 에 write 하는 함수 */
    fun writeJSONtoFile(fileName: String){
        val gson = Gson()
        val jsonString:String = gson.toJson(MaterialsList)
        //Toast.makeText(activity,"$jsonString", Toast.LENGTH_SHORT).show()
        val file = File(context.cacheDir, fileName)
        file.writeText(jsonString)
    }

    /*유통기간 체크하는 함수 마테리얼 넘겨서 비교하자*/
    fun checkExpirationDate(material: Material, itemView: View){
        val cal : Calendar = Calendar.getInstance()
        cal.time = Date()                               //현재 날짜 가져옴
        itemView.setBackgroundColor(Color.argb(0,0,0,0))        // 기본 백그라운드 색상을 공백으로

        /*유통기한이 지났으면 */
        if(material.expirationDate!!.year < cal.time.year ){
            // 배경 검은색
            Toast.makeText(context, "유통기한이 지남", Toast.LENGTH_SHORT).show()
            itemView.setBackgroundColor(Color.BLACK)
        }
        else if(material.expirationDate!!.year == cal.time.year){
            if(material.expirationDate!!.month < cal.time.month){
                itemView.setBackgroundColor(Color.BLACK)
                Toast.makeText(context, "유통기한이 지남", Toast.LENGTH_SHORT).show()
            }
        }
        if( (material.expirationDate!!.year == cal.time.year) and  (material.expirationDate!!.month == cal.time.month)){
            /* 유통기한 3일 이내 */
            if( (material.expirationDate!!.date - cal.time.date) <= 3){
                Toast.makeText(context,"유통기한이 3일 이내로 남은 재료가 있습니다", Toast.LENGTH_SHORT).show()
                itemView.setBackgroundColor(Color.YELLOW)
            }
            /* 유통기한 3~7일 남았다면 */
            else if((material.expirationDate!!.date - cal.time.date) <= 7){
                Toast.makeText(context, "year: 유통기한이 3~7일 남은 재료가 있습니다", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "year: ${cal.time.year}. ${cal.time.month}. ${cal.time.date}" +
                      "유통기한이 3~7일 남은 재료가 있습니다", Toast.LENGTH_SHORT).show()
                itemView.setBackgroundColor(Color.GRAY)
            }
        }
    }

}