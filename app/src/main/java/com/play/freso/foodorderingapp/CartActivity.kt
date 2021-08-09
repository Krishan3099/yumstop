package com.play.freso.foodorderingapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.play.freso.foodorderingapp.adapters.MyCartAdapter
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

        setSupportActionBar(findViewById(R.id.my_toolbar))
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

        recycler_cart.apply{
            layoutManager = LinearLayoutManager(this@CartActivity)
            addItemDecoration(DividerItemDecoration(this@CartActivity, (layoutManager as LinearLayoutManager).orientation))
            cartAdapter = MyCartAdapter(this@CartActivity)
        }



    }

    private fun onLoadCartSuccess(){
        var sum = 0.0
        viewModel.orderItems.observe(this, {
            for(cartModel in it!!){
                sum+= cartModel.totalPrice
            }
            txtTotal.text = StringBuilder("$").append("%.2f".format(sum))
        })
    }




    override fun plusCartItem(position: Int) {
        viewModel.plusOrderItem(position)
        cartAdapter.notifyDataSetChanged()
        onLoadCartSuccess()
    }

    override fun minusCartItem(position: Int) {
        viewModel.minusOrderItem(position)
        cartAdapter.notifyDataSetChanged()
        onLoadCartSuccess()
//        holder.txtQuantity!!.text = StringBuilder("").append(cartModel.quantity)
    }


    override fun deleteItem(position: Int) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Delete Item")
            .setMessage("Do you really want to delete item?")
            .setNegativeButton("Cancel") {dialog, _ -> dialog.dismiss()}
            .setPositiveButton("Delete") {dialog, _ ->

                viewModel.deleteOrderItem(position)
                cartAdapter.notifyItemRemoved(position)
                cartAdapter.notifyDataSetChanged()
                onLoadCartSuccess()
            }
            .create()
        dialog.show()
    }

    override fun onPause() {
        super.onPause()
        viewModel.updateFirestoreOrder()
        onBackPressed()
    }


    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.action_cart)
        item.isVisible = false
        return true
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_logout -> {
                val dialog = AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Do you really want to logout?")
                    .setNegativeButton("Cancel") {dialog, _ -> dialog.dismiss()}
                    .setPositiveButton("Logout") { _, _ ->
                        getSharedPreferences("USER_INFO", Context.MODE_PRIVATE).edit().apply {
                            clear()
                            apply()
                        }
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                    .create()
                dialog.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}