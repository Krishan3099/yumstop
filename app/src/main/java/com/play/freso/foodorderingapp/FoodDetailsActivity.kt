package com.play.freso.foodorderingapp

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.play.freso.foodorderingapp.adapters.MyCartAdapter
import com.play.freso.foodorderingapp.models.CartModel
import com.play.freso.foodorderingapp.models.FoodItemPost
import kotlinx.android.synthetic.main.activity_food_details.*
import kotlinx.android.synthetic.main.activity_food_details.food_price
import kotlinx.android.synthetic.main.register_activity.*
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable
import kotlin.reflect.typeOf


class FoodDetailsActivity : AppCompatActivity() {


    private val db = Firebase.firestore
    private lateinit var user: String
    private lateinit var food_id: String

    private lateinit var orderMap: MutableMap<String, String>
    private var quantity = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_details)


        val bundle = intent.getBundleExtra("bundle")
        val foodItem = bundle?.getParcelable<FoodItemPost>("selected_item")
        user = intent.getStringExtra("user") ?: "whoops :,)"
        food_id = foodItem!!.food_id.toString()


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Item Details"

        bind(foodItem?.name, foodItem?.img, foodItem?.desc, foodItem?.price)

        db.collection("users")
            .document(user)
            .get()
            .addOnSuccessListener { result ->


                Log.d("check", "WHAT DOES THIS SAY ${result["order"]} ")
                (result["order"] as MutableMap<*, *>).forEach { (k, v) ->
                    Log.d("check", "$k = $v")
                }

                orderMap =  result["order"] as MutableMap<String, String>


                setListeners()

//                initRecyclerView()
            }
            .addOnFailureListener { exception ->
                Log.w("category", "Error getting documents.", exception)
            }

    }


    private fun setListeners(){
        num_items_text!!.setText(quantity.toString())

        plus_button.setOnClickListener{
            quantity += 1
            num_items_text.setText(quantity.toString())
        }

        minus_button.setOnClickListener{
            if(quantity > 1) {quantity -= 1}
            num_items_text.setText(quantity.toString())
        }

        add_button.setOnClickListener{
            orderMap[food_id] = ((orderMap[food_id]?.toInt() ?: 0) + quantity).toString()
            db.collection("users")
                .document(user)
                .update("order", orderMap)
                .addOnSuccessListener {
                    Log.d("add_to_cart", "DocumentSnapshot successfully written!")
                    onBackPressed()
                }
                .addOnFailureListener{
                    Log.d("add_to_cart", "fail to cart!")
                }
        }
    }



    @SuppressLint("SetTextI18n")
    private fun bind(foodName: String?, foodImg: String?, foodDesc: String?, foodPrice: String?){

        food_title.text = foodName
        food_desc.text = foodDesc
        food_price.text = "$${foodPrice}"

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide.with(this)
            .applyDefaultRequestOptions(requestOptions)
            .load(foodImg)
            .into(food_image)
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

    private fun minusCartItem() {


    }

}