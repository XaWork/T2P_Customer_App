package me.taste2plate.app.customer.ui.items

import android.util.Log
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
import me.taste2plate.app.models.address.cityzip.CityOption

class CategoryAdapterItems(private val options: List<CityOption>) :
    AdapterItem<CategoryViewHolder>() {
    override val layoutId: Int = R.layout.category_container

    override fun onCreateViewHolder(view: View): CategoryViewHolder = CategoryViewHolder(view)

    override fun updateItemViews(viewHolder: CategoryViewHolder) {
        val cityItems = options.map {
            SingleCategoryItem(it)
        }
        viewHolder.itemAdapter.replaceItems(cityItems, true)
    }

    override fun isTheSame(newItem: AdapterItem<*>): Boolean {
        return newItem is CategoryAdapterItems
    }

    override fun isContentsTheSame(newItem: AdapterItem<*>): Boolean {
        return isTheSame(newItem) && (newItem as CategoryAdapterItems).options == options
    }
}


class CategoryViewHolder(view: View) : BaseViewHolder(view) {
    val itemBinding: CategoryContainerBinding by BindListItem(view)
    val itemAdapter = ItemsViewAdapter()

    init {
        val lManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        itemBinding.run {
            categories.layoutManager = lManager
            categories.adapter = itemAdapter
        }
    }
}