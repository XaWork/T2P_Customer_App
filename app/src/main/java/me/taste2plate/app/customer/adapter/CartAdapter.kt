package me.taste2plate.app.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.viewholder.CartViewHolder
import me.taste2plate.app.customer.models.CartLineItem
import me.taste2plate.app.models.cart.CartItem

class CartAdapter(private val cartLineItems: List<CartItem>) : androidx.recyclerview.widget.RecyclerView.Adapter<CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            parent.context,
            LayoutInflater.from(parent.context).inflate(R.layout.single_cart_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.renderView(cartLineItems[position])
    }


    override fun getItemCount(): Int {
        return if (cartLineItems.isEmpty()) 0 else cartLineItems.size
    }
}
