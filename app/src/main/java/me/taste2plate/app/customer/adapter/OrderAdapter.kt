package me.taste2plate.app.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.viewholder.OrderViewHolder
import me.taste2plate.app.models.order.Order

class OrderAdapter(private val orders: List<Order>, private val serverTime:String) : androidx.recyclerview.widget.RecyclerView.Adapter<OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(
            parent.context,
            LayoutInflater.from(parent.context).inflate(R.layout.single_order_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.renderView(orders[position], serverTime)
    }


    override fun getItemCount(): Int {
        return if (orders.isEmpty()) 0 else orders.size
    }
}
