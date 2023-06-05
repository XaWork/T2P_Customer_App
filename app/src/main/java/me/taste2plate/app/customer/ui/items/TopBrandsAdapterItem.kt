package me.taste2plate.app.customer.ui.items

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fueled.reclaim.AdapterItem
import com.fueled.reclaim.BaseViewHolder
import com.fueled.reclaim.ItemsViewAdapter
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.BindListItem
import me.taste2plate.app.customer.databinding.CategoryContainerBinding
import me.taste2plate.app.customer.databinding.TopBrandContainerBinding
import me.taste2plate.app.models.address.cityzip.CityOption
import me.taste2plate.app.models.home.MostOrderedItem
import me.taste2plate.app.models.home.TopBrands

class TopBrandsAdapterItem(private val options: List<TopBrands>) :
    AdapterItem<TopBrandsViewHolder>() {

    override val layoutId: Int = R.layout.top_brand_container

    override fun onCreateViewHolder(view: View): TopBrandsViewHolder = TopBrandsViewHolder(view)

    override fun updateItemViews(viewHolder: TopBrandsViewHolder) {
        val cityItems = options.map {
            SingleTopBrandItem(it)
        }
        viewHolder.itemAdapter.replaceItems(cityItems, true)
    }

    override fun isTheSame(newItem: AdapterItem<*>): Boolean {
        return newItem is TopBrandsAdapterItem
    }

    override fun isContentsTheSame(newItem: AdapterItem<*>): Boolean {
        return isTheSame(newItem) && (newItem as TopBrandsAdapterItem).options == options
    }
}


class TopBrandsViewHolder(view: View) : BaseViewHolder(view) {
    val itemBinding: TopBrandContainerBinding by BindListItem(view)
    val itemAdapter = ItemsViewAdapter()

    init {
        val gridManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        itemBinding.run {
            topBrands.layoutManager = gridManager
            topBrands.adapter = itemAdapter
        }
    }
}