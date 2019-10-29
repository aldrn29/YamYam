package com.example.fridge

import android.os.Parcel
import android.os.Parcelable

/*10.07 Material 클래스 정의
* Material 에 필드 추가 가능*/

class Material() : Parcelable {
    var name: String? = null
    var image: Int? = null
    
    /*
    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        image = parcel.readValue(Int::class.java.classLoader) as? Int
    }

     */

    constructor(name: String, image: Int) : this() {
        this.name = name
        this.image = image
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeValue(image)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Material> {
        override fun createFromParcel(parcel: Parcel): Material {
            return Material()
        }

        override fun newArray(size: Int): Array<Material?> {
            return arrayOfNulls(size)
        }
    }
}
