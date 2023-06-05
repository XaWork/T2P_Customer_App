package me.taste2plate.app.customer.ui.items

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fueled.reclaim.AdapterItem
import com.fueled.reclaim.BaseViewHolder
import com.fueled.reclaim.ItemsViewAdapter
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.BindListItem
import me.taste2plate.app.customer.databinding.FeatureRestaurantContainerBinding
import me.taste2plate.app.models.home.HiddenGems

class HiddenGemsAdapterItem(
    private val hiddenGems: List<HiddenGems>
) : AdapterItem<HiddenGemsContainerViewHolder>() {

    override val layoutId: Int = R.layout.feature_restaurant_container

    override fun onCreateViewHolder(view: View): HiddenGemsContainerViewHolder =
        HiddenGemsContainerViewHolder(view)

    override fun isTheSame(newItem: AdapterItem<*>): Boolean {
        return newItem is HiddenGemsAdapterItem
    }

    override fun isContentsTheSame(newItem: AdapterItem<*>): Boolean {
        return isTheSame(newItem) && (newItem as HiddenGemsAdapterItem).hiddenGems == hiddenGems
    }

    override fun updateItemViews(viewHolder: HiddenGemsContainerViewHolder) {
        viewHolder.run {
            val items = hiddenGems.map {
                HiddenGemsViewHolder(it)
            }
            itemsAdapter.replaceItems(items, true)
        }
    }
}

class HiddenGemsContainerViewHolder(view: View) : BaseViewHolder(view) {
    private val itemBinding: FeatureRestaurantContainerBinding by BindListItem(view)
    val itemsAdapter = ItemsViewAdapter()
    private val gridManager = GridLayoutManager(view.context, 2)

    init {
        val lManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        itemBinding.products.run {
            adapter = itemsAdapter
            layoutManager = lManager
        }
    }
}