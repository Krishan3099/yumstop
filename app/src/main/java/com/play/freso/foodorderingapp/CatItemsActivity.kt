package com.play.freso.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.play.freso.foodorderingapp.adapters.CatRecyclerAdapter
import kotlinx.android.synthetic.main.activity_cat_items.*
import kotlinx.coroutines.*



class CatItemsActivity : AppCompatActivity(), CatRecyclerAdapter.OnNoteListener {


    private val db = Firebase.firestore
    private lateinit var catAdapter: CatRecyclerAdapter
    private var cats = ArrayList<String>()
    lateinit var recycler_view: RecyclerView
    lateinit var user: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat_items)
        user = intent.getStringExtra("user") ?: "whoops :,)"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Food Categories"



        db.collection("cats")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("category", "${document.id} => ${document.data["cat_name"].toString()}")
                    cats.add(document.data["cat_name"].toString())
                }
                initRecyclerView()
                cart_button.setOnClickListener{
                    val intent = Intent(this, CartActivity::class.java).apply{
                        putExtra("user", user)
                    }
                    startActivity(intent)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("category", "Error getting documents.", exception)
            }


    }


    private fun initRecyclerView(){

        recycler_view = findViewById<RecyclerView>(R.id.recycler_view)
        recycler_view.apply{
            layoutManager = LinearLayoutManager(this@CatItemsActivity)
            val topSpacingDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecoration)
            Log.d("category", "2")
            catAdapter = CatRecyclerAdapter(cats, this@CatItemsActivity)
            adapter = catAdapter
        }
    }

    override fun onNoteClick(position: Int) {
        val intent = Intent(this, FoodItemsActivity::class.java).apply{
            putExtra("category", cats[position])
            putExtra("user", user)
        }
        startActivity(intent)
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