package com.example.projectmcs

object ShoppingCart {
    private val cartItems: MutableList<CartItem> = mutableListOf()

    fun addItem(item: CartItem) {
        val existingItem = cartItems.find { it.name == item.name }
        if (existingItem != null) {
            existingItem.quantity += item.quantity
        } else {
            cartItems.add(item)
        }
    }

    fun getCartItems(): List<CartItem> {
        return cartItems
    }

    fun clearCart() {
        cartItems.clear()
    }

    fun removeItem(item: CartItem) {
        cartItems.remove(item)
    }

    fun updateItem(item: CartItem, newQuantity: Int) {
        val existingItem = cartItems.find { it.name == item.name }
        if (existingItem != null) {
            existingItem.quantity = newQuantity
        }
    }
}