package com.play.freso.foodorderingapp.models

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot

data class FoodItem(
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

    companion object CREATOR : Parcelable.Creator<FoodItem> {
        override fun createFromParcel(parcel: Parcel): FoodItem {
            return FoodItem(parcel)
        }

        override fun newArray(size: Int): Array<FoodItem?> {
            return arrayOfNulls(size)
        }

        fun DocumentSnapshot.toFoodItem(): FoodItem? {
            return try {
                //val id
                val cat = getString("cat")!!
                val name = getString("name")!!
                val price = getString("price")
                val desc = getString("desc")
                val img = getString("img")

                FoodItem(id, cat, name, price, desc, img)
            } catch (e: Exception) {
                null
            }
        }
    }

}