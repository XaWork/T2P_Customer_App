package me.taste2plate.app.customer.ui.items

import android.view.View
import com.fueled.reclaim.AdapterItem
import com.fueled.reclaim.BaseViewHolder
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.BindListItem
import me.taste2plate.app.customer.databinding.ShowMoreItemBinding

class ShowMoreAdapterItem(
    private val viewAllClick: ViewAllClick
) : AdapterItem<ShowMoreViewHolder>() {

    override val layoutId: Int = R.layout.show_more_item

    override fun onCreateViewHolder(view: View): ShowMoreViewHolder = ShowMoreViewHolder(view)

    override fun updateItemViews(viewHolder: ShowMoreViewHolder) {
        viewHolder.itemBinding.run {
            showLess.setOnClickListener {
                viewAllClick.onClick("show less")
                showLess.visibility = View.GONE
                showMore.visibility = View.VISIBLE
            }
            showMore.setOnClickListener {
                viewAllClick.onClick("show more")
                showMore.visibility = View.GONE
                showLess.visibility = View.VISIBLE
            }
        }
    }

    override fun isTheSame(newItem: AdapterItem<*>): Boolean {
        return newItem is ShowMoreAdapterItem
    }

    override fun isContentsTheSame(newItem: AdapterItem<*>): Boolean {
        // return isTheSame(newItem) && (newItem as SectionAdapterItem).title == title
        return true
    }
}

class ShowMoreViewHolder(view: View) : BaseViewHolder(view) {
    val itemBinding: ShowMoreItemBinding by BindListItem(view)
}