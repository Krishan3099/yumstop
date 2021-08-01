package com.play.freso.foodorderingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.play.freso.foodorderingapp.R

class CatRecyclerAdapter(var cats:ArrayList<String>, onNoteListen: OnNoteListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = ArrayList<String>()
    private var onNoteListener: OnNoteListener

    init {
        items = cats
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
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class CatItemViewHolder constructor(
        catView: View,
        onNoteListen: OnNoteListener
    ): RecyclerView.ViewHolder(catView), View.OnClickListener{

        val cat_name = itemView.findViewById<TextView>(R.id.cat_name)
        var onNoteListener: OnNoteListener

        init {
            catView.setOnClickListener(this)
            onNoteListener = onNoteListen
        }



        fun bind(catName: String){
            cat_name.setText(catName)
        }

        override fun onClick(v: View?) {
            onNoteListener.onNoteClick(adapterPosition)
        }
    }


     interface OnNoteListener {
        fun onNoteClick(position: Int)
    }
}