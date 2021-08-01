package com.play.freso.foodorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.play.freso.foodorderingapp.adapters.FoodRecyclerAdapter
import com.play.freso.foodorderingapp.adapters.MyCartAdapter
import com.play.freso.foodorderingapp.models.CartModel
import com.play.freso.foodorderingapp.models.FoodItemPost
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {


    private val users_db = Firebase.firestore
    private val items_db = Firebase.firestore
    private lateinit var user: String
    private lateinit var orderMap: MutableMap<String, String>
    private lateinit var cartAdapter: MyCartAdapter
    private var cartModelList = ArrayList<CartModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        user = intent.getStringExtra("user") ?: "whoops :,)"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Cart"

        //loadCartFromFirebase or previous activity


        users_db
            .collection("users")
            .document(user)
            .get()
            .addOnSuccessListener { result ->
                Log.d("check", "WHAT DOES THIS SAY ${result["order"]} ")
                (result["order"] as MutableMap<String, String>).forEach { (k, v) ->
                    items_db
                        .collection("food_items")
                        .document(k)
                        .get()
                        .addOnSuccessListener { res ->
                            Log.d("check", "WHAT DOES THIS SAY ${res} ")
                            Log.d("check", "WHAT DOES THIS SAY ${res["name"] as String} ")
                            cartModelList.add(
                                CartModel(
                                    k,
                                    res["name"] as String,
                                    res["img"] as String,
                                    res["price"] as String,
                                    v.toInt(),
                                    0f
                                )
                            )
                            Log.d("timing", "BeepBoop")
                    }
                        .addOnFailureListener{
                            Log.w("cart", "Error getting documents.", it)
                    }
                        .addOnCompleteListener{
                            recycler_cart.apply{cartAdapter.submitList(cartModelList)}
                        }

                }

                Log.d("timing", "Well here's why it doesn't work")
                init()
                onLoadCartSuccess(cartModelList)

            }
            .addOnFailureListener { exception ->
                Log.w("cart", "Error getting documents.", exception)
            }
            .addOnCompleteListener{
                Log.d("timing", "Well here's why it doesn't twerk")
            }


    }

    private fun init(){
//        val layoutManager = LinearLayoutManager(this)
//        recycler_cart!!.layoutManager = layoutManager
//        recycler_cart!!.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
//        cartAdapter = MyCartAdapter(this, cartModelList)
//        recycler_cart!!.adapter = cartAdapter

        recycler_cart.apply{
            layoutManager = LinearLayoutManager(this@CartActivity)
            addItemDecoration(DividerItemDecoration(this@CartActivity, (layoutManager as LinearLayoutManager).orientation))
            cartAdapter = MyCartAdapter(this@CartActivity)
            adapter = cartAdapter
            cartAdapter.submitList(cartModelList)
        }



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun onLoadCartSuccess(cartModelList:List<CartModel>){
        var sum = 0.0
        for(cartModel in cartModelList!!){
            sum+= cartModel!!.totalPrice
        }
        txtTotal.text = StringBuilder("$").append(sum)

    }
}