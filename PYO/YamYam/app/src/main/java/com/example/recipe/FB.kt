package com.example.recipe

import com.google.firebase.database.FirebaseDatabase

class FB {
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("message")

//    myRef.setValue("Hello")
}