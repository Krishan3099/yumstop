package com.play.freso.foodorderingapp.models

import android.os.Parcel
import android.os.Parcelable

data class FoodItemPost(
    val food_id: String?,
    val cat: String?,
    val name: String?,
    val price: String?,
    val desc: String?,
    val img: String?
                         ): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(food_id)
        parcel.writeString(cat)
        parcel.writeString(name)
        parcel.writeString(price)
        parcel.writeString(desc)
        parcel.writeString(img)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FoodItemPost> {
        override fun createFromParcel(parcel: Parcel): FoodItemPost {
            return FoodItemPost(parcel)
        }

        override fun newArray(size: Int): Array<FoodItemPost?> {
            return arrayOfNulls(size)
        }
    }
}