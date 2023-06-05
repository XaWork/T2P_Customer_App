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
import me.taste2plate.app.customer.databinding.TopMostOrderdProductContainerBinding
import me.taste2plate.app.models.address.cityzip.CityOption
import me.taste2plate.app.models.home.MostOrderedItem
import me.taste2plate.app.models.home.TopBrands
import me.taste2plate.app.models.home.TopMostOrderedProducts

class TopMostOrderProductsAdapterItem(private val options: List<TopMostOrderedProducts>) :
    AdapterItem<TopMostOrderProductsViewHolder>() {

    override val layoutId: Int = R.layout.top_most_orderd_product_container

    override fun onCreateViewHolder(view: View): TopMostOrderProductsViewHolder = TopMostOrderProductsViewHolder(view)

    override fun updateItemViews(viewHolder: TopMostOrderProductsViewHolder) {
        val mostOrderItems = options.map {
            SingleTopMostOrderProduct(it)
        }
        viewHolder.itemAdapter.replaceItems(mostOrderItems, true)
    }

    override fun isTheSame(newItem: AdapterItem<*>): Boolean {
        return newItem is TopMostOrderProductsAdapterItem
    }

    override fun isContentsTheSame(newItem: AdapterItem<*>): Boolean {
        return isTheSame(newItem) && (newItem as TopMostOrderProductsAdapterItem).options == options
    }
}


class TopMostOrderProductsViewHolder(view: View) : BaseViewHolder(view) {
    val itemBinding: TopMostOrderdProductContainerBinding by BindListItem(view)
    val itemAdapter = ItemsViewAdapter()

    init {
        val gridManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        itemBinding.run {
            topBrands.layoutManager = gridManager
            topBrands.adapter = itemAdapter
        }
    }
}