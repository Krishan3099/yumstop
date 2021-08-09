package com.play.freso.foodorderingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.play.freso.foodorderingapp.R
import com.play.freso.foodorderingapp.models.OrderItem


class MyCartAdapter(onNoteListen: OnNoteListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var cartModelList: List<OrderItem> = ArrayList()
    private var onNoteListener: OnNoteListener

    init {
        onNoteListener = onNoteListen

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyCartViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_cart_item,parent, false),
            onNoteListener
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){
            is MyCartViewHolder ->{
                holder.bind(cartModelList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return cartModelList.size
    }

    fun submitList(cartList: List<OrderItem>){
        cartModelList = cartList
    }

    class MyCartViewHolder constructor(
        itemView: View,
        onNoteListen: OnNoteListener
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private val btnMinus = itemView.findViewById(R.id.btnMinus) as ImageView
        private var btnPlus = itemView.findViewById(R.id.btnPlus) as ImageView
        private var imageView = itemView.findViewById(R.id.imageView) as ImageView
        private var btnDelete = itemView.findViewById(R.id.btnDelete) as ImageView
        private var txtName = itemView.findViewById(R.id.txtName) as TextView
        private var txtPrice = itemView.findViewById(R.id.txtPrice) as TextView
        private var txtQuantity = itemView.findViewById(R.id.txtQuantity) as TextView
        private var onNoteListener: OnNoteListener



        init{
            itemView.setOnClickListener(this)
            onNoteListener = onNoteListen
        }


        fun bind(orderItem: OrderItem){
            Glide.with(itemView.context)
                .load(orderItem.image)
                .into(imageView)
            txtName.text = StringBuilder().append(orderItem.name)
            txtPrice.text = StringBuilder("$").append("%.2f".format(orderItem.totalPrice))
            txtQuantity.text = StringBuilder("").append(orderItem.quantity)

        }


        override fun onClick(v: View?) {

            btnMinus.setOnClickListener{
                onNoteListener.minusCartItem(adapterPosition)
            }

            btnPlus.setOnClickListener{
                onNoteListener.plusCartItem(adapterPosition)
            }

            btnDelete.setOnClickListener{
                onNoteListener.deleteItem(adapterPosition)
            }
        }


    }
    interface OnNoteListener {
        fun minusCartItem(position: Int)
        fun plusCartItem(position: Int)
        fun deleteItem(position: Int)
    }

}