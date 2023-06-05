package me.taste2plate.app.customer.ui.items

import android.view.View
import com.fueled.reclaim.AdapterItem
import com.fueled.reclaim.BaseViewHolder
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.BindListItem
import me.taste2plate.app.customer.databinding.ShowLessItemBinding

class ShowLessAdapterItem(
    private val viewAllClick: ViewAllClick
) : AdapterItem<ShowLessViewHolder>() {

    override val layoutId: Int = R.layout.show_less_item

    override fun onCreateViewHolder(view: View): ShowLessViewHolder = ShowLessViewHolder(view)

    override fun updateItemViews(viewHolder: ShowLessViewHolder) {
        viewHolder.itemBinding.run {
           root.setOnClickListener {
              // viewAllClick.onClick()
           }
        }
    }

    override fun isTheSame(newItem: AdapterItem<*>): Boolean {
        return newItem is ShowLessAdapterItem
    }

    override fun isContentsTheSame(newItem: AdapterItem<*>): Boolean {
        // return isTheSame(newItem) && (newItem as SectionAdapterItem).title == title
        return true
    }
}

class ShowLessViewHolder(view: View) : BaseViewHolder(view) {
    val itemBinding: ShowLessItemBinding by BindListItem(view)
}

interface ViewAllClick{
    fun onClick(item: String)
}