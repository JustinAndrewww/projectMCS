package com.example.projectmcs

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DollDetail : AppCompatActivity() {
    private lateinit var backButton: ImageView
    private lateinit var imgDoll: ImageView
    private lateinit var nameDoll: TextView
    private lateinit var ratingDoll: TextView
    private lateinit var descDoll: TextView
    private lateinit var priceDoll: TextView
    private lateinit var buyButton: Button
    private lateinit var sizeDoll: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doll_detail)

        backButton = findViewById(R.id.backButton)
        imgDoll = findViewById(R.id.imgDoll)
        nameDoll = findViewById(R.id.nameDoll)
        ratingDoll = findViewById(R.id.ratingDoll)
        descDoll = findViewById(R.id.descDoll)
        priceDoll = findViewById(R.id.priceDoll)
        buyButton = findViewById(R.id.buyButton)
        sizeDoll = findViewById(R.id.sizeDoll)

        val img = intent.getStringExtra("image")
        val name = intent.getStringExtra("name")
        val rating = intent.getStringExtra("rating")
        val desc = intent.getStringExtra("desc")
        val price = intent.getStringExtra("price")
        val size = intent.getStringExtra("size")

        nameDoll.text = name
        sizeDoll.text = size
        ratingDoll.text = "/ $rating"
        descDoll.text = desc
        priceDoll.text = "Rp $price,00"

        Glide.with(this)
            .load(img)
            .into(imgDoll)

        backButton.setOnClickListener {
            finish()
        }

        buyButton.setOnClickListener {
            val cartItem = CartItem(
                id = System.currentTimeMillis().toInt(),
                name = name ?: "",
                quantity = 1,
                price = price?.toDouble() ?: 0.0,
                imageUrl = img ?: "" // Tambahkan URL gambar
            )
            ShoppingCart.addItem(cartItem)
            Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}