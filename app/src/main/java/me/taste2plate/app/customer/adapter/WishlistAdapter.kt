package me.taste2plate.app.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.viewholder.WishlistViewHolder
import me.taste2plate.app.models.wishlist.WishlistItemResponse

class WishlistAdapter(private val wishLineItems: List<WishlistItemResponse.Result>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<WishlistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        return WishlistViewHolder(
            parent.context,
            LayoutInflater.from(parent.context)
                .inflate(R.layout.single_wishlist_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        holder.renderView(wishLineItems[position], position)
    }


    override fun getItemCount(): Int {
        return if (wishLineItems.isEmpty()) 0 else wishLineItems.size
    }
}
