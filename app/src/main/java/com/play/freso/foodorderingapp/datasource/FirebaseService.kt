package com.play.freso.foodorderingapp.datasource

import android.util.Log
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
            emptyList()
        }
    }


    suspend fun getOrderData(order_number: String): MutableMap<String, ArrayList<String>> {
        val db = FirebaseFirestore.getInstance()
        var orderItems: MutableMap<String, ArrayList<String>> = mutableMapOf()


        return try {
            db.collection("orders")
                .document(order_number)
                .get().await().data!!.forEach{
                    val itemKey = it.key.toString()
                    val item: ArrayList<String> = it.value as ArrayList<String>
                    orderItems[itemKey] = item

                }
            orderItems
        } catch (e: Exception) {
            Log.e(TAG, "Error getting categories", e)
            emptyMap<String, Array<String>>() as MutableMap<String, ArrayList<String>>
        }
    }

    suspend fun getOrderListItems(order: MutableMap<String, ArrayList<String>>): ArrayList<OrderItem>? {
        val db = FirebaseFirestore.getInstance()
        var foodItems = arrayListOf<OrderItem>()
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
            emptyArray<OrderItem>() as ArrayList<OrderItem>
        }

    }
}