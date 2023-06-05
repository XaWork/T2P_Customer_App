package me.taste2plate.app.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.viewholder.CategoryViewHolder
import me.taste2plate.app.models.Category

class CategoryAdapter(
    private val categories: List<Category.Result>,
    private val categoryMap: MutableMap<String, ArrayList<Category.Result>>
) : androidx.recyclerview.widget.RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            parent.context,
            LayoutInflater.from(parent.context).inflate(
                R.layout.single_city_brand_item1,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.renderView(categories[position], categoryMap[categories[position]._id])
    }


    override fun getItemCount(): Int {
        return if (categories.isEmpty()) 0 else categories.size
    }
}
