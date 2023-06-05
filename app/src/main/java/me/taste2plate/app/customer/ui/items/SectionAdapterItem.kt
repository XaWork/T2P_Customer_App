package me.taste2plate.app.customer.ui.items

import android.view.View
import com.fueled.reclaim.AdapterItem
import com.fueled.reclaim.BaseViewHolder
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.BindListItem
import me.taste2plate.app.customer.databinding.TitleItemBinding

class SectionAdapterItem(private val title:String):AdapterItem<TitleViewHolder>() {
    override val layoutId: Int = R.layout.title_item

    override fun onCreateViewHolder(view: View): TitleViewHolder = TitleViewHolder(view)

    override fun updateItemViews(viewHolder: TitleViewHolder) {
        viewHolder.itemBinding.run {
            sectionTitle = title

        }
    }

    override fun isTheSame(newItem: AdapterItem<*>): Boolean {
        return newItem is SectionAdapterItem
    }

    override fun isContentsTheSame(newItem: AdapterItem<*>): Boolean {
        return isTheSame(newItem) && (newItem as SectionAdapterItem).title == title
    }
}

class TitleViewHolder(view:View):BaseViewHolder(view){
    val itemBinding: TitleItemBinding by BindListItem(view)
}