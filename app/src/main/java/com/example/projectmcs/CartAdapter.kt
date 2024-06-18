package com.example.projectmcs

import android.annotation.SuppressLint
import android.widget.Toast
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CartAdapter(
    private val cartItems: MutableList<CartItem>,
    private val listener: CartItemListener
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    interface CartItemListener {
        fun onDeleteItem(cartItem: CartItem)
        fun onUpdateItem(cartItem: CartItem, quantity: Int)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<CartItem>) {
        cartItems.clear()
        cartItems.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvItemName: TextView = itemView.findViewById(R.id.NamaDoll)
        private val ivItemImage: ImageView = itemView.findViewById(R.id.GambarDoll)
        private val tvPrice: TextView = itemView.findViewById(R.id.HargaDoll)
        private val etQuantity: EditText = itemView.findViewById(R.id.Quantity)
        private val btnDelete: Button = itemView.findViewById(R.id.deleteButton)
        private val btnUpdate: Button = itemView.findViewById(R.id.updateButton)

        init {
            btnDelete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteItem(cartItems[position])
                }
            }

            btnUpdate.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val quantityStr = etQuantity.text.toString()
                    if (quantityStr.isNotEmpty() && quantityStr.toInt() > 0) {
                        val newQuantity = quantityStr.toInt()
                        listener.onUpdateItem(cartItems[position], newQuantity)
                    } else {
                        Toast.makeText(itemView.context, "Quantity must be a number and more than 0", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        fun bind(cartItem: CartItem) {

            val total  = cartItem.price * cartItem.quantity
            tvItemName.text = cartItem.name
            tvPrice.text = total.toString()
            etQuantity.setText(cartItem.quantity.toString())
            // Assuming you have imageUrl in CartItem, load image using Glide
            Glide.with(itemView.context).load(cartItem.imageUrl).into(ivItemImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }
}