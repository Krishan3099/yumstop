package com.play.freso.foodorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.play.freso.foodorderingapp.adapters.MyCartAdapter
import com.play.freso.foodorderingapp.models.OrderItem
import com.play.freso.foodorderingapp.viewmodels.UserOrderViewModel
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {


    private lateinit var viewModel: UserOrderViewModel
    private lateinit var user_id: String
    private lateinit var cartAdapter: MyCartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        user_id = intent.getStringExtra("user") ?: "whoops :,)"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Cart"

        //loadCartFromFirebase or previous activity
        viewModel = ViewModelProvider(this)[UserOrderViewModel::class.java]
        viewModel.loadCartItems(user_id)


        init()

        viewModel.orderItems.observe(this, {
            recycler_cart.apply{
                adapter = cartAdapter
                cartAdapter.submitList(it)
            }
            onLoadCartSuccess(it)
        })

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

    private fun onLoadCartSuccess(cartModelList:List<OrderItem>){
        var sum = 0.0
        for(cartModel in cartModelList!!){
            sum+= cartModel!!.totalPrice
        }
        txtTotal.text = StringBuilder("$").append(sum)

    }
}