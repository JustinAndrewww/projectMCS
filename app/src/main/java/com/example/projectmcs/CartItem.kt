package com.example.projectmcs

data class CartItem(
    val id: Int,
    val name: String,
    var quantity: Int,
    val price: Double,
    val imageUrl: String // Tambahkan properti ini
)