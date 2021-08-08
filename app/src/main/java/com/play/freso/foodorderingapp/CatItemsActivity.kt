package com.play.freso.foodorderingapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.play.freso.foodorderingapp.adapters.CatRecyclerAdapter
import com.play.freso.foodorderingapp.viewmodels.CatItemListViewModel
import kotlinx.android.synthetic.main.activity_cat_items.*
import kotlinx.coroutines.*



class CatItemsActivity : AppCompatActivity(), CatRecyclerAdapter.OnNoteListener {


    private lateinit var viewModel: CatItemListViewModel
    private lateinit var catAdapter: CatRecyclerAdapter
    lateinit var user: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat_items)

//        user = intent.getStringExtra("user") ?: "whoops :,)"
        val sharedPreference: SharedPreferences =  getSharedPreferences("USER_INFO", Context.MODE_PRIVATE)
        user = sharedPreference.getString("uid", "whoops :,)")!!

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Food Categories"

        viewModel = ViewModelProvider(this)[CatItemListViewModel::class.java]


        initRecyclerView()


        cart_button.setOnClickListener{
            val intent = Intent(this, CartActivity::class.java).apply{
                putExtra("user", user)
            }
            startActivity(intent)
        }


        viewModel.cats.observe(this, {
            recycler_view.apply {
                adapter = catAdapter
                catAdapter.submitList(it)
            }
        })


    }


    private fun initRecyclerView(){
        recycler_view.apply{
            layoutManager = LinearLayoutManager(this@CatItemsActivity)
            val topSpacingDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecoration)
            catAdapter = CatRecyclerAdapter(this@CatItemsActivity)
        }
    }

    override fun onNoteClick(position: Int) {
        viewModel = ViewModelProvider(this)[CatItemListViewModel::class.java]
        viewModel.cats.observe(this, {
            val intent = Intent(this, FoodItemsActivity::class.java).apply{
                putExtra("category", it[position])
                putExtra("user", user)
            }
            startActivity(intent)
        })

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}