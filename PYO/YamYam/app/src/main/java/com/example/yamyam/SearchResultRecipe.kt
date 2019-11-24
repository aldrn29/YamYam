package com.example.yamyam

class SearchResultRecipe {
    var name: String? = null
    var image:Int? = null
    var materialArr : ArrayList<String>? = null
    var description : String? = null

    constructor(name: String, image: Int){
        this.name = name
        this.image = image
    }

}
/*
class Recipe : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val recipeImg = findViewById<ImageView>(R.id.recipeImg)
        val name = findViewById<TextView>(R.id.recipeName)
        val materialArr = findViewById<TextView>(R.id.materialArr)
        val description = findViewById<TextView>(R.id.cookingDescription)

//      RecyclerView Item의 pos에 따라 바뀌여야함
        recipeImg.setImageResource(R.drawable.hamburger)
        name.setText("setText")
        materialArr.setText("Materials")
        description.setText("Description")


    }

}
 */