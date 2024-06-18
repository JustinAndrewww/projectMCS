    package com.example.projectmcs

    import android.annotation.SuppressLint
    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.Button
    import android.widget.Toast
    import androidx.fragment.app.Fragment
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView

    class Cart : Fragment(), CartAdapter.CartItemListener {

        private lateinit var cartAdapter: CartAdapter
        private lateinit var recyclerView: RecyclerView
        private lateinit var checkoutButton: Button

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_cart, container, false)

            recyclerView = view.findViewById(R.id.recycler_view)
            recyclerView.layoutManager = LinearLayoutManager(context)
            checkoutButton = view.findViewById(R.id.buyButton)
            cartAdapter = CartAdapter(ShoppingCart.getCartItems().toMutableList(), this)
            recyclerView.adapter = cartAdapter
            checkoutButton.setOnClickListener {
                performCheckout()
            }
            return view
        }

        override fun onUpdateItem(cartItem: CartItem, quantity: Int) {
            // Update item quantity in ShoppingCart
            ShoppingCart.updateItem(cartItem, quantity)
            cartAdapter.notifyDataSetChanged()
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onDeleteItem(cartItem: CartItem) {
            ShoppingCart.removeItem(cartItem)
            cartAdapter.notifyDataSetChanged()
            loadData()
        }

        @SuppressLint("NotifyDataSetChanged")
        private fun performCheckout() {
            if (ShoppingCart.getCartItems().isEmpty()) {
                Toast.makeText(requireContext(), "Your cart is empty. Please add items before checking out.", Toast.LENGTH_SHORT).show()
                return
            }

            // Implement logic for checkout here, e.g., sending order to server, clearing cart in database, etc.
            // For now, we just clear the local cart and notify user.
            ShoppingCart.clearCart()
            cartAdapter.notifyDataSetChanged()
            Toast.makeText(requireContext(), "Checkout successful. Your cart is now empty.", Toast.LENGTH_SHORT).show()
            loadData()
        }
        private fun loadData() {
            // Reload or refresh cart data
            cartAdapter.updateItems(ShoppingCart.getCartItems())
        }
    }