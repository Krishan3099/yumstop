package com.play.freso.foodorderingapp

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
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

class CartActivity : AppCompatActivity(), MyCartAdapter.OnNoteListener {


    private lateinit var viewModel: UserOrderViewModel
    private lateinit var user_id: String
    private lateinit var cartAdapter: MyCartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        val sharedPreference: SharedPreferences =  getSharedPreferences("USER_INFO", Context.MODE_PRIVATE)
        user_id = sharedPreference.getString("uid", "whoops :,)")!!

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
        })

        onLoadCartSuccess()

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

    private fun onLoadCartSuccess(){
        var sum = 0.0
        viewModel.orderItems.observe(this, {
            for(cartModel in it!!){
                sum+= cartModel!!.totalPrice
            }
            txtTotal.text = StringBuilder("$").append(sum)
        })
    }




    override fun plusCartItem(position: Int) {
        viewModel.plusOrderItem(position)
        cartAdapter.notifyDataSetChanged();
        onLoadCartSuccess()
    }

    override fun minusCartItem(position: Int) {
        viewModel.minusOrderItem(position)
        cartAdapter.notifyDataSetChanged();
        onLoadCartSuccess()
//        holder.txtQuantity!!.text = StringBuilder("").append(cartModel.quantity)
    }


    override fun deleteItem(position: Int) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Delete Item")
            .setMessage("Do you really want to delete item?")
            .setNegativeButton("Cancel") {dialog, _ -> dialog.dismiss()}
            .setPositiveButton("Delete") {dialog, _ ->
//                notifyItemRemoved(position)
                viewModel.deleteOrderItem(position)
                cartAdapter.notifyItemRemoved(position);
                cartAdapter.notifyDataSetChanged();
                onLoadCartSuccess()
            }
            .create()
        dialog.show()
    }

    override fun onPause() {
        super.onPause()
        viewModel.updateFirestoreOrder()
    }
}