package com.play.freso.foodorderingapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.play.freso.foodorderingapp.models.FoodItem
import com.play.freso.foodorderingapp.viewmodels.FoodItemListViewModel
import com.play.freso.foodorderingapp.viewmodels.UserOrderViewModel
import kotlinx.android.synthetic.main.activity_food_details.*
import kotlinx.android.synthetic.main.activity_food_details.food_price
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.tasks.await

import com.google.firebase.auth.AuthResult
import java.util.concurrent.ExecutionException


class FoodDetailsActivity : AppCompatActivity() {


    private lateinit var viewModel: UserOrderViewModel
    private lateinit var user_id: String
    private lateinit var food_id: String
    private var quantity = 1




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Item Details"

        val bundle = intent.getBundleExtra("bundle")
        val foodItem = bundle?.getParcelable<FoodItem>("selected_item")
        val sharedPreference: SharedPreferences =  getSharedPreferences("USER_INFO", Context.MODE_PRIVATE)
        user_id = sharedPreference.getString("uid", "whoops :,)")!!
        food_id = foodItem!!.food_id.toString()


        viewModel = ViewModelProvider(this)[UserOrderViewModel::class.java]


        viewModel.loadCart(user_id)



        setViewDetails(foodItem.name!!, foodItem.img!!, foodItem.desc!!, foodItem.price!!)



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

                viewModel.addToOrder(food_id, quantity)

            onBackPressed()
        }







    }




    @SuppressLint("SetTextI18n")
    private fun setViewDetails(foodName: String?, foodImg: String?, foodDesc: String?, foodPrice: String?){

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

}