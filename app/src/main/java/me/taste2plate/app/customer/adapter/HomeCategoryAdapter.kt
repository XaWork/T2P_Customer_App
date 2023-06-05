package me.taste2plate.app.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.viewholder.CityViewHolder
import me.taste2plate.app.models.Category
import me.taste2plate.app.models.address.cityzip.CityOption

class HomeCityAdapter(
    private val cities: List<CityOption>
) : androidx.recyclerview.widget.RecyclerView.Adapter<CityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(
            parent.context,
            LayoutInflater.from(parent.context).inflate(
                R.layout.single_home_category_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.renderView(cities[position])
    }


    override fun getItemCount(): Int {
        return if (cities.isEmpty()) 0 else cities.size
    }
}
