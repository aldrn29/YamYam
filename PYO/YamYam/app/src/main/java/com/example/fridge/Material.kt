package com.example.fridge

import android.os.Parcel
import android.os.Parcelable

/*10.07 Material 클래스 정의
* Material 에 필드 추가 가능*/

class Material{
    var name: String? = null
    var image: Int? = null

    constructor(name: String, image: Int){
        this.name = name
        this.image = image
    }

}