package com.play.freso.foodorderingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.play.freso.foodorderingapp.R
import com.play.freso.foodorderingapp.models.FoodItem

class FoodRecyclerAdapter(onNoteListen: OnNoteListener):  RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var items: List<FoodItem> = ArrayList()
    private var onNoteListener: OnNoteListener

    init {
        onNoteListener = onNoteListen

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FoodItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_food_list_item, parent, false),
            onNoteListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){

            is FoodItemViewHolder ->{
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(foodList: List<FoodItem>){
        items = foodList
    }

    class FoodItemViewHolder constructor(
        itemView: View,
        onNoteListen: OnNoteListener
    ): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private val foodImage = itemView.findViewById<ImageView>(R.id.food_image)
        private val foodName = itemView.findViewById<TextView>(R.id.food_name)
        private val foodPrice = itemView.findViewById<TextView>(R.id.food_price)
        private var onNoteListener: OnNoteListener

        init {
            itemView.setOnClickListener(this)
            onNoteListener = onNoteListen
        }
        fun bind(foodItemPost: FoodItem){
            foodName.text = foodItemPost.name
            foodPrice.text = foodItemPost.price.toString()

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(foodItemPost.img)
                .into(foodImage)
        }

        override fun onClick(v: View?) {
            onNoteListener.onNoteClick(adapterPosition)
        }

    }

    interface OnNoteListener {
        fun onNoteClick(position: Int)
    }
}