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
import me.taste2plate.app.customer.databinding.MostOrderItemContainerBinding
import me.taste2plate.app.customer.databinding.TopBrandContainerBinding
import me.taste2plate.app.models.address.cityzip.CityOption
import me.taste2plate.app.models.home.MostOrderedItem

class MostOrderedAdapterItem(private val options: List<MostOrderedItem>) :
    AdapterItem<MostOrderedItemViewHolder>() {

    override val layoutId: Int = R.layout.most_order_item_container

    override fun onCreateViewHolder(view: View): MostOrderedItemViewHolder = MostOrderedItemViewHolder(view)

    override fun updateItemViews(viewHolder: MostOrderedItemViewHolder) {
        val cityItems = options.map {
            SingleMostOrderedItem(it)
        }
        viewHolder.itemAdapter.replaceItems(cityItems, true)
    }

    override fun isTheSame(newItem: AdapterItem<*>): Boolean {
        return newItem is MostOrderedAdapterItem
    }

    override fun isContentsTheSame(newItem: AdapterItem<*>): Boolean {
        return isTheSame(newItem) && (newItem as MostOrderedAdapterItem).options == options
    }
}


class MostOrderedItemViewHolder(view: View) : BaseViewHolder(view) {
    val itemBinding: MostOrderItemContainerBinding by BindListItem(view)
    val itemAdapter = ItemsViewAdapter()

    init {
        val gridManager = GridLayoutManager(view.context, 4)
        itemBinding.run {
            mostOrderedItems.layoutManager = gridManager
            mostOrderedItems.adapter = itemAdapter
        }
    }
}