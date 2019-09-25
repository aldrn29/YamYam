package com.example.yamyam10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity() {


    var ingredients = arrayListOf<String>()

    val editTextPlace: EditText = findViewById(R.id.edit_text)
    val addButton: Button = findViewById(R.id.add_button)

    val list: ListView = findViewById(R.id.mainListView)

    val adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, ingredients)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        var ingredients = arrayListof<Ingredients>()

        this.list.adapter = this.adapter
        this.updateListView()

    }
    fun updateListView() {
        this.addButton.setOnClickListener {
            this.addIngredients()
            this.adapter.setNotifyOnChange(true)
        }
    }
    fun addIngredients() {
        this.ingredients.add(this.editTextPlace.text.toString())
    }
}
