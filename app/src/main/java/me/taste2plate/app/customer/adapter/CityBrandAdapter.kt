package me.taste2plate.app.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.viewholder.CityBrandViewHolder
import me.taste2plate.app.models.CityBrand

class CityBrandAdapter(
    private var products: List<CityBrand.Result>,
    private val type: String
) : RecyclerView.Adapter<CityBrandViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityBrandViewHolder {
        return CityBrandViewHolder(
            parent.context,
            LayoutInflater.from(parent.context).inflate(R.layout.single_city_brand_item1, parent, false),type
        )
    }

    override fun onBindViewHolder(holder: CityBrandViewHolder, position: Int) {
        holder.renderView(products[position])
    }

    fun filterList(filterlist: List<CityBrand.Result>) {
        products = filterlist
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return if (products.isEmpty()) 0 else products.size
    }
}
