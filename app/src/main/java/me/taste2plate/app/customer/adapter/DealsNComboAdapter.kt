package me.taste2plate.app.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.viewholder.DealsNComboViewHolder
import me.taste2plate.app.customer.adapter.viewholder.ProductViewHolder
import me.taste2plate.app.models.Product

class DealsNComboAdapter(private val products: List<Product>) : RecyclerView.Adapter<DealsNComboViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealsNComboViewHolder {
        return DealsNComboViewHolder(
            parent.context,
            LayoutInflater.from(parent.context).inflate(R.layout.single_product_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DealsNComboViewHolder, position: Int) {
        holder.renderView(products[position])
    }


    override fun getItemCount(): Int {
        return if (products.isEmpty()) 0 else products.size
    }
}
