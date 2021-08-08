package com.play.freso.foodorderingapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.play.freso.foodorderingapp.adapters.FoodRecyclerAdapter
import com.play.freso.foodorderingapp.viewmodels.CatItemListViewModel
import com.play.freso.foodorderingapp.viewmodels.FoodItemListViewModel
import kotlinx.android.synthetic.main.activity_cat_items.*
import kotlinx.android.synthetic.main.activity_food_items.*
import kotlinx.android.synthetic.main.activity_food_items.cart_button
import java.util.*
import kotlin.collections.ArrayList


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
        category = intent.getStringExtra("category") ?: "whoops :,)"
        val sharedPreference: SharedPreferences =  getSharedPreferences("USER_INFO", Context.MODE_PRIVATE)
        user = sharedPreference.getString("uid", "whoops :,)")!!

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "${category.uppercase()} ITEMS"

        viewModel = ViewModelProvider(this)[FoodItemListViewModel::class.java]
        viewModel.getItems(category)

        initRecyclerView()

        cart_button.setOnClickListener{
            val intent = Intent(this, CartActivity::class.java).apply{
                putExtra("user", user)
            }
            startActivity(intent)
        }

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


}