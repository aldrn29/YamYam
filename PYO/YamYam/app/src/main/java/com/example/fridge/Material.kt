package com.example.fridge

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/*10.07 Material 클래스 정의
* Material 에 필드 추가 가능
* 11.17 유통기한 필드추가
* 11.24 재료 선택 검색을 위한 isSelected 필드 추가*/

class Material() : Parcelable {
    var name: String? = null
    var image: Int? = null
    var expirationDate: FridgeFragment.materialExpirationDate? = null
    var isSelected : Boolean = false
    /*
    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        image = parcel.readValue(Int::class.java.classLoader) as? Int
    }

     */

    constructor(name: String, image: Int, expirationDate: FridgeFragment.materialExpirationDate?) : this() {
        this.name = name
        this.image = image
        this.expirationDate = expirationDate
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeValue(image)
        //parcel.writeInt(expirationDate)
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
