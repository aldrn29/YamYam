package com.example.fridge

/*
    MaterialInputActivity 에서 사용하는 Category class
 */
class Category {
    var name: String? = null
    var image: Int? = null

    constructor(name: String, image: Int){
        this.name = name
        this.image = image
    }
}