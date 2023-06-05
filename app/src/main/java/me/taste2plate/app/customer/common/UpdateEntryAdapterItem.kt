package me.taste2plate.app.customer.common

import android.view.View
import com.fueled.reclaim.AdapterItem
import com.fueled.reclaim.BaseViewHolder
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.databinding.ItemGeneralChoiceBinding
import me.taste2plate.app.customer.databinding.ItemIntermediateBinding

class UpdateEntryAdapterItem(
    private val updateText: String,
    val updateType:UpdateType
) : AdapterItem<UpdateViewHolder>() {

    override val layoutId = R.layout.item_intermediate

    override fun onCreateViewHolder(view: View) = UpdateViewHolder(view)

    override fun updateItemViews(viewHolder: UpdateViewHolder) {
        viewHolder.itemBinding.run {
            updateTextValue.text = this@UpdateEntryAdapterItem.updateText
            when (updateType) {
                UpdateType.EXIT -> {
                    exit.visibility = View.VISIBLE
                    entry.visibility = View.GONE
                    intermediate.visibility = View.GONE
                }
                UpdateType.ENTRY -> {
                    exit.visibility = View.GONE
                    entry.visibility = View.VISIBLE
                    intermediate.visibility = View.GONE
                }
                else -> {
                    exit.visibility = View.GONE
                    entry.visibility = View.GONE
                    intermediate.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun isTheSame(newItem: AdapterItem<*>): Boolean {
        return newItem is UpdateEntryAdapterItem
    }

    override fun isContentsTheSame(newItem: AdapterItem<*>): Boolean {
        return isTheSame(newItem) && (newItem as UpdateEntryAdapterItem).updateText == updateText && newItem.updateType==updateType
    }
}

class UpdateViewHolder(view: View) : BaseViewHolder(view) {
    val itemBinding: ItemIntermediateBinding by BindListItem(view)
}


enum class UpdateType{
    ENTRY,
    EXIT,
    INTERMEDIATE
}