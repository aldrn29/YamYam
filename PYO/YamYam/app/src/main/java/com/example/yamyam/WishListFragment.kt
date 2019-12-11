package com.example.yamyam


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipe.RecipeSource
import com.example.yamyam.searchResult.SearchResultRecipeAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_fridge.*
import kotlinx.android.synthetic.main.fragment_wishlist.*
import java.io.BufferedReader
import java.io.File

/*
    위시리스트 프래그먼트
    위시리스트로 저장된 레시피들을 화면에 표시
    위시리스트가 저장된 내부 파일로부터 읽어 표시, 관리

    12.12 내부저장소에 저장된 파일을 읽어 위시리스트 표시
 */
class WishListFragment : Fragment() {

    private val fileName = "SavedWishLists"
    private var savedWishList = ArrayList<RecipeSource>()
    var wishListAdapter : SearchResultRecipeAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wishlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*저장된 파일에서 로딩*/
        loadFromSavedFile()

        wishListAdapter = SearchResultRecipeAdapter(requireContext(), savedWishList)

        val wishListFragmentManager = GridLayoutManager(requireContext(), 1)
        recipe_recyclerView.layoutManager = wishListFragmentManager
        recipe_recyclerView.setHasFixedSize(true)
        recipe_recyclerView.adapter = wishListAdapter
    }

    private fun loadWishList(fileName: String){
        savedWishList.clear()
        val gson = Gson()
        //Read the json file
        val file = File(requireContext().cacheDir, fileName)
        val bufferedReader: BufferedReader = file.bufferedReader()
        //bufferedReader 에서 텍스트를 읽고 inputString 에 저장
        val inputString = bufferedReader.use { it.readText() }
        //json 파일에 저장되어 있던 String 들을 다시 RecipeSource 로
        val restored = gson.fromJson(inputString, Array<RecipeSource>::class.java)

        //load
        for(i in restored){
            savedWishList.add(i)
        }
    }

    /* 재시작될경우(위시리스트를 취소한 경우도 있을 수 있으므로 다시 파일을 불러온다*/
    override fun onResume() {
        super.onResume()
        loadFromSavedFile()
        wishListAdapter!!.notifyDataSetChanged()
    }

    private fun loadFromSavedFile()
    {
        /* 처음 어플을 실행하는 경우 아직 파일을 쓰지 않았으므로 */
        if(File(requireContext().cacheDir, fileName).exists()) {
            /* json 파일에서 저장되었던 wishList 불러옴 */
            loadWishList(fileName)
        }
    }

}
