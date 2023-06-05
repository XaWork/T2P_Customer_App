package me.taste2plate.app.customer.ui.items

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fueled.reclaim.AdapterItem
import com.fueled.reclaim.BaseViewHolder
import com.fueled.reclaim.ItemsViewAdapter
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.BindListItem
import me.taste2plate.app.customer.databinding.ProductsContainer1Binding
import me.taste2plate.app.customer.databinding.ProductsContainerBinding
import me.taste2plate.app.customer.ui.home.HomeFragment
import me.taste2plate.app.customer.viewmodels.ProductViewModel
import me.taste2plate.app.models.home.ProductDeal

class ProductsAdapterItemHor(
    private val context: Context,
    private val productDeals: List<ProductDeal>,
    private val productViewModel: ProductViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val homeFragment: HomeFragment,
) : AdapterItem<ProductsAdapterItemHorViewHolder>() {

    override val layoutId: Int = R.layout.products_container1

    override fun onCreateViewHolder(view: View): ProductsAdapterItemHorViewHolder =
        ProductsAdapterItemHorViewHolder(view,  )

    override fun isTheSame(newItem: AdapterItem<*>): Boolean {
        return newItem is ProductsAdapterItemHor
    }

    override fun isContentsTheSame(newItem: AdapterItem<*>): Boolean {
        return isTheSame(newItem) && (newItem as ProductsAdapterItemHor).productDeals == productDeals
    }

    override fun updateItemViews(viewHolder: ProductsAdapterItemHorViewHolder) {
        viewHolder.run {
            val items = productDeals.map {
                ProductAdapterItemHor(context, it, productViewModel, lifecycleOwner, homeFragment,  )
            }
            itemsAdapter.replaceItems(items, true)
        }
    }

}

class ProductsAdapterItemHorViewHolder(view: View ) : BaseViewHolder(view) {
    private val itemBinding: ProductsContainer1Binding by BindListItem(view)
    val itemsAdapter = ItemsViewAdapter()
    private val gridManager =  LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)

    init {
        itemBinding.products.run {
            adapter = itemsAdapter
            layoutManager = gridManager
        }
    }
}