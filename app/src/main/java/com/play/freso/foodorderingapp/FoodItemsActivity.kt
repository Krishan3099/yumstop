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
import com.play.freso.foodorderingapp.adapters.FoodRecyclerAdapter
import com.play.freso.foodorderingapp.datasource.FoodDataSource
import com.play.freso.foodorderingapp.models.FoodItemPost
import kotlinx.android.synthetic.main.activity_food_items.*
import java.util.*
import javax.security.auth.DestroyFailedException
import kotlin.collections.ArrayList


class FoodItemsActivity : AppCompatActivity(), FoodRecyclerAdapter.OnNoteListener{

    private val db = Firebase.firestore
    private lateinit var user: String

    private lateinit var foodAdapter: FoodRecyclerAdapter
    private lateinit var recycler_view: RecyclerView
    private lateinit var category: String
    private var foodItems = ArrayList<FoodItemPost>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("help", "Created")
        setContentView(R.layout.activity_food_items)
        category = intent.getStringExtra("category") ?: "whoops :,)"
        user = intent.getStringExtra("user") ?: "whoops :,)"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "${category.uppercase()} ITEMS"


        db.collection("food_items")
            .whereEqualTo("cat", category)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("food_items", "${document.id} => ${document.data}")
                    foodItems.add(
                            FoodItemPost(
                            document.id,
                            document.data["cat"].toString(),
                            document.data["name"].toString(),
                            document.data["price"].toString(),
                            document.data["desc"].toString(),
                            document.data["img"].toString()
                        )
                    )
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
                Log.w("food_items", "Error getting documents.", exception)
            }
    }
    


    private fun initRecyclerView(){
        recycler_view = findViewById<RecyclerView>(R.id.recycler_view)
        recycler_view.apply{
            layoutManager = LinearLayoutManager(this@FoodItemsActivity)
            val topSpacingDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecoration)
            foodAdapter = FoodRecyclerAdapter(this@FoodItemsActivity)
            adapter = foodAdapter
            foodAdapter.submitList(foodItems)
        }
    }

    override fun onNoteClick(position: Int) {
        val bundle = Bundle()
        val foodItem = foodItems[position]
        val intent = Intent(this, FoodDetailsActivity::class.java).apply{
            bundle.putParcelable("selected_item", foodItem)
            putExtra("bundle", bundle)
            putExtra("user", user)
        }
        startActivity(intent)
    }


}