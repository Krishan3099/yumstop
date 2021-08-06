package com.play.freso.foodorderingapp.datasource

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.play.freso.foodorderingapp.models.FoodItem
import com.play.freso.foodorderingapp.models.FoodItem.CREATOR.toFoodItem
import com.play.freso.foodorderingapp.models.OrderItem
import com.play.freso.foodorderingapp.models.OrderItem.Companion.toOrderItem

import com.play.freso.foodorderingapp.models.User
import com.play.freso.foodorderingapp.models.User.Companion.toUser
import kotlinx.coroutines.tasks.await

object FirebaseService {
    private const val TAG = "FirebaseService"


    suspend fun getProfileData(userId: String): User? {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("users")
                .document(userId).get().await().toUser()
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user details", e)
            FirebaseCrashlytics.getInstance().log("Error getting user details")
            FirebaseCrashlytics.getInstance().setCustomKey("user id", "xpertSlug")
            FirebaseCrashlytics.getInstance().recordException(e)
            null
        }
    }


    suspend fun getCategories(): List<String> {
        val db = FirebaseFirestore.getInstance()
        var cats = mutableListOf<String>()
        return try {
            db.collection("cats").get().await().forEach {
                cats.add(it.data["cat_name"].toString())
            }
            cats
        } catch (e: Exception) {
            Log.e(TAG, "Error getting categories", e)
            FirebaseCrashlytics.getInstance().log("Error getting categories")
            FirebaseCrashlytics.getInstance().setCustomKey("user id", "xpertSlug")
            FirebaseCrashlytics.getInstance().recordException(e)
            emptyList()
        }
    }

    suspend fun getFoodItems(category: String): List<FoodItem> {
        val db = FirebaseFirestore.getInstance()
        var foodItems = mutableListOf<FoodItem>()

        return try {
            db.collection("food_items")
                .whereEqualTo("cat", category)
                .get().await().forEach {
                    foodItems.add(it.toFoodItem()!!)
            }
            foodItems
        } catch (e: Exception) {
            Log.e(TAG, "Error getting categories", e)
            FirebaseCrashlytics.getInstance().log("Error getting categories")
            FirebaseCrashlytics.getInstance().setCustomKey("user id", "xpertSlug")
            FirebaseCrashlytics.getInstance().recordException(e)
            emptyList()
        }
    }


    suspend fun getOrderData(order_number: String): MutableMap<String, Array<String>> {
        val db = FirebaseFirestore.getInstance()
        lateinit var orderItems: MutableMap<String, Array<String>>

        return try {
            db.collection("orders")
                .document(order_number)
                .get().await().data!!.forEach{
                    val item_key = it.key.toString()
                    val item: Array<String> = it.value as Array<String>
                    orderItems[item_key] = item


//                    var key:String= "",
//    var name:String= "",
//    var image:String= "",
//    var price:String= "",
//    var quantity: Int = 0,
//    var totalPrice: Float = 0f

                }
            orderItems
        } catch (e: Exception) {
            Log.e(TAG, "Error getting categories", e)
            FirebaseCrashlytics.getInstance().log("Error getting categories")
            FirebaseCrashlytics.getInstance().setCustomKey("user id", "xpertSlug")
            FirebaseCrashlytics.getInstance().recordException(e)
            emptyMap<String, Array<String>>() as MutableMap<String, Array<String>>
        }
    }

    suspend fun getOrderListItems(order: MutableMap<String, Array<String>>): List<OrderItem>? {
        val db = FirebaseFirestore.getInstance()
        var foodItems = mutableListOf<OrderItem>()
        return try {

            //k -> food_item_id
            //v -> Array[reference_to_food, quantity]
            order.forEach{ (k,v) ->
                db.collection("food_items")
                    .document(k)
                    .get().await().toOrderItem(v[1].toInt())?.let {
                        foodItems.add(
                            it
                        )
                    }
            }
            foodItems
        } catch (e: Exception) {
            Log.e(TAG, "Error getting categories", e)
            FirebaseCrashlytics.getInstance().log("Error getting categories")
            FirebaseCrashlytics.getInstance().setCustomKey("user id", "xpertSlug")
            FirebaseCrashlytics.getInstance().recordException(e)
            emptyList()
        }

    }


    //maybe this or maybe just have another dao for orders

}