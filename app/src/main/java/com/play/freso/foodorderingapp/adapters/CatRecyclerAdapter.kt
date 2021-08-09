package com.play.freso.foodorderingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.play.freso.foodorderingapp.R

class CatRecyclerAdapter(onNoteListen: OnNoteListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<String> = ArrayList()
    private var onNoteListener: OnNoteListener

    init {
        onNoteListener = onNoteListen

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CatItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_cat_item, parent, false),
            onNoteListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){

            is CatItemViewHolder ->{
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    fun submitList(catList: List<String>){
        items = catList
    }

    class CatItemViewHolder constructor(
        catView: View,
        onNoteListen: OnNoteListener
    ): RecyclerView.ViewHolder(catView), View.OnClickListener{

        private val catName = itemView.findViewById<TextView>(R.id.cat_name)
        private var onNoteListener: OnNoteListener

        init {
            catView.setOnClickListener(this)
            onNoteListener = onNoteListen
        }



        fun bind(cat_name: String){
            catName.text = cat_name
        }

        override fun onClick(v: View?) {
            onNoteListener.onNoteClick(adapterPosition)
        }
    }


     interface OnNoteListener {
        fun onNoteClick(position: Int)
    }
}