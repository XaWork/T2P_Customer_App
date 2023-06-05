package me.taste2plate.app.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.viewholder.ProductViewHolder
import me.taste2plate.app.customer.viewmodels.ProductViewModel
import me.taste2plate.app.models.Product
import me.taste2plate.app.models.home.ProductDeal

class ListAdapter(private val products: List<ProductDeal>, private val viewModel: ProductViewModel,private val lifeCycleOwner: LifecycleOwner) : androidx.recyclerview.widget.RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            parent.context,
            LayoutInflater.from(parent.context).inflate(R.layout.single_product_item, parent, false), viewModel, lifeCycleOwner
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.renderView(products[position])
    }


    override fun getItemCount(): Int {
        return if (products.isEmpty()) 0 else products.size
    }
}
