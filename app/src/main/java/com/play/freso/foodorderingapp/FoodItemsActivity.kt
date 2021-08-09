package com.play.freso.foodorderingapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.play.freso.foodorderingapp.adapters.FoodRecyclerAdapter
import com.play.freso.foodorderingapp.viewmodels.FoodItemListViewModel
import kotlinx.android.synthetic.main.activity_cat_items.*
import kotlinx.android.synthetic.main.activity_food_items.*

import java.util.*



class FoodItemsActivity : AppCompatActivity(), FoodRecyclerAdapter.OnNoteListener{

    private lateinit var viewModel: FoodItemListViewModel
    private lateinit var user: String
    private lateinit var foodAdapter: FoodRecyclerAdapter
    private lateinit var recycler_view: RecyclerView
    private lateinit var category: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("help", "Created")
        setContentView(R.layout.activity_food_items)
        setSupportActionBar(findViewById(R.id.my_toolbar))


        val sharedPreference: SharedPreferences =  getSharedPreferences("USER_INFO", Context.MODE_PRIVATE)
        user = sharedPreference.getString("uid", "whoops :,)")!!
        category = sharedPreference.getString("cat", "whoops :,)")!!

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "${category.replaceFirstChar { it.uppercase() }} Items"

        viewModel = ViewModelProvider(this)[FoodItemListViewModel::class.java]
        viewModel.getItems(category)

        initRecyclerView()

        viewModel.items.observe(this, {
            recycler_view.apply {
                adapter = foodAdapter
                foodAdapter.submitList(it)
            }
        })


    }
    


    private fun initRecyclerView(){
        recycler_view = findViewById<RecyclerView>(R.id.recycler_view)
        recycler_view.apply{
            layoutManager = LinearLayoutManager(this@FoodItemsActivity)
            val topSpacingDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecoration)
            foodAdapter = FoodRecyclerAdapter(this@FoodItemsActivity)
        }
    }

    override fun onNoteClick(position: Int) {
        viewModel.items.observe(this, {
            val bundle = Bundle()
            val foodItem = it[position]
            val intent = Intent(this, FoodDetailsActivity::class.java).apply{
                bundle.putParcelable("selected_item", foodItem)
                putExtra("bundle", bundle)
                putExtra("user", user)
            }
            startActivity(intent)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                val intent = Intent(this, CartActivity::class.java).apply{
                    putExtra("user", user)
                }
                startActivity(intent)
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