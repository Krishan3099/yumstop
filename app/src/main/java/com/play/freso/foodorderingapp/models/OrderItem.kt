package com.play.freso.foodorderingapp.models

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.DocumentSnapshot

data class OrderItem (
    var key:String= "",
    var name:String= "",
    var image:String= "",
    var price:String= "",
    var quantity: Int = 0,
    var totalPrice: Float = 0f
){
    companion object {
        fun DocumentSnapshot.toOrderItem(quantity: Int): OrderItem? {
            return try {
                //val id
                val name = getString("name")!!
                val img = getString("img")!!
                val price = getString("price")!!
                OrderItem(id, name, img, price, quantity)
            } catch (e: Exception) {
                Log.e(TAG, "Error converting to Order Item", e)
                FirebaseCrashlytics.getInstance().log("Error converting order item")
                FirebaseCrashlytics.getInstance().setCustomKey("userId", id)
                FirebaseCrashlytics.getInstance().recordException(e)
                null
            }
        }
        private const val TAG = "User"
    }

}