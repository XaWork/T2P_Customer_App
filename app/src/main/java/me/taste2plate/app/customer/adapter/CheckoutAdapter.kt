package me.taste2plate.app.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.viewholder.CheckoutViewHolder
import me.taste2plate.app.models.order.OrderProductItem
import me.taste2plate.app.models.order.Product

class CheckoutAdapter(private val cartLineItems: List<OrderProductItem>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<CheckoutViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutViewHolder {
        return CheckoutViewHolder(
            parent.context,
            LayoutInflater.from(parent.context).inflate(
                R.layout.single_checkout_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CheckoutViewHolder, position: Int) {
        holder.renderView(cartLineItems[position])
    }


    override fun getItemCount(): Int {
        return if (cartLineItems.isEmpty()) 0 else cartLineItems.size
    }
}
