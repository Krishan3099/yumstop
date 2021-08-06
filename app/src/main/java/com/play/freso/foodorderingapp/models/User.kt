package com.play.freso.foodorderingapp.models

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.DocumentSnapshot

data class User (val userid: String = "",
                 val name: String = "",
                 var orderNumber: String = ""){

    companion object {
        fun DocumentSnapshot.toUser(): User? {
            return try {
                val name = getString("name")!!
                val orderNumber = getString("order_number")!!
                User(id, name, orderNumber)
            } catch (e: Exception) {
                Log.e(TAG, "Error converting user profile", e)
                FirebaseCrashlytics.getInstance().log("Error converting user profile")
                FirebaseCrashlytics.getInstance().setCustomKey("userId", id)
                FirebaseCrashlytics.getInstance().recordException(e)
                null
            }
        }
        private const val TAG = "User"
    }


}