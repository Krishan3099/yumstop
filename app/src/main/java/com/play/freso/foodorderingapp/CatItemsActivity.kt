package com.play.freso.foodorderingapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.play.freso.foodorderingapp.adapters.CatRecyclerAdapter
import com.play.freso.foodorderingapp.viewmodels.CatItemListViewModel
import kotlinx.android.synthetic.main.activity_cat_items.*
import kotlinx.coroutines.*



class CatItemsActivity : AppCompatActivity(), CatRecyclerAdapter.OnNoteListener {


    private lateinit var sharedPreference: SharedPreferences
    private lateinit var viewModel: CatItemListViewModel
    private lateinit var catAdapter: CatRecyclerAdapter
    lateinit var user: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat_items)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.title = "Food Categories"


//        user = intent.getStringExtra("user") ?: "whoops :,)"
        sharedPreference =  getSharedPreferences("USER_INFO", Context.MODE_PRIVATE)
        user = sharedPreference.getString("uid", "whoops :,)")!!



        viewModel = ViewModelProvider(this)[CatItemListViewModel::class.java]


        initRecyclerView()


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
                sharedPreference.edit {
                    putString("cat", it[position])
                    apply()
                }
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