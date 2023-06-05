package me.taste2plate.app.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.viewholder.SubCategoryViewHolder
import me.taste2plate.app.models.Category
import me.taste2plate.app.models.SubCategories

class SubcategoryAdapter(
    private val products: List<SubCategories.Result>
) : RecyclerView.Adapter<SubCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryViewHolder {
        return SubCategoryViewHolder(
            parent.context,
            LayoutInflater.from(parent.context).inflate(R.layout.single_subcategory_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SubCategoryViewHolder, position: Int) {
        holder.renderView(products[position])
    }


    override fun getItemCount(): Int {
        return if (products.isNullOrEmpty()) 0 else products.size
    }
}
