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
import me.taste2plate.app.customer.databinding.ProductsContainerBinding
import me.taste2plate.app.customer.ui.home.HomeFragment
import me.taste2plate.app.customer.viewmodels.ProductViewModel
import me.taste2plate.app.models.home.ProductDeal

class ProductsAdapterItem(
    private val context: Context,
    private val productDeals: List<ProductDeal>,
    private val productViewModel: ProductViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val homeFragment: HomeFragment,
) : AdapterItem<ProductsContainerViewHolder>() {

    override val layoutId: Int = R.layout.products_container

    override fun onCreateViewHolder(view: View): ProductsContainerViewHolder =
        ProductsContainerViewHolder(view,  )

    override fun isTheSame(newItem: AdapterItem<*>): Boolean {
        return newItem is ProductsAdapterItem
    }

    override fun isContentsTheSame(newItem: AdapterItem<*>): Boolean {
        return isTheSame(newItem) && (newItem as ProductsAdapterItem).productDeals == productDeals
    }

    override fun updateItemViews(viewHolder: ProductsContainerViewHolder) {
        viewHolder.run {
            val items = productDeals.map {
                ProductAdapterItem(context, it, productViewModel, lifecycleOwner, homeFragment,  )
            }
            itemsAdapter.replaceItems(items, true)
        }
    }

}

class ProductsContainerViewHolder(view: View ) : BaseViewHolder(view) {
    private val itemBinding: ProductsContainerBinding by BindListItem(view)
    val itemsAdapter = ItemsViewAdapter()
    private val gridManager =  GridLayoutManager(view.context, 2)

    init {
        itemBinding.products.run {
            adapter = itemsAdapter
            layoutManager = gridManager
        }
    }
}