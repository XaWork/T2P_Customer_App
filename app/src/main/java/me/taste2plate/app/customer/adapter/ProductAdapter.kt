package me.taste2plate.app.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.viewholder.ProductListingViewHolder
import me.taste2plate.app.customer.viewmodels.ProductViewModel
import me.taste2plate.app.models.newproducts.NewProduct

class ProductAdapter(
    private val products: List<NewProduct>,
    val viewModel: ProductViewModel,
    val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<ProductListingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListingViewHolder {
        return ProductListingViewHolder(
            parent.context,
            LayoutInflater.from(parent.context).inflate(R.layout.single_product_item, parent, false),
            viewModel, lifecycleOwner
        )
    }

    override fun onBindViewHolder(holder: ProductListingViewHolder, position: Int) {
        holder.renderView(products[position])
    }


    override fun getItemCount(): Int {
        return if (products.isEmpty()) 0 else products.size
    }
}
