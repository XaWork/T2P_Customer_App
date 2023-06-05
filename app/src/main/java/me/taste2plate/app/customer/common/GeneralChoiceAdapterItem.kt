package me.taste2plate.app.customer.common

import android.view.Gravity
import android.view.View
import com.fueled.reclaim.AdapterItem
import com.fueled.reclaim.BaseViewHolder
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.databinding.ItemGeneralChoiceBinding

class GeneralChoiceAdapterItem(
    private val choice: String,
    private val isCenter:Boolean= false,
    val onChoiceSelection: (Int) -> Unit
) : AdapterItem<GeneralChoiceViewHolder>() {

    override val layoutId = R.layout.item_general_choice

    override fun onCreateViewHolder(view: View) = GeneralChoiceViewHolder(view)

    override fun updateItemViews(viewHolder: GeneralChoiceViewHolder) {
        viewHolder.itemBinding.run {
            choice = this@GeneralChoiceAdapterItem.choice
            if(isCenter){
                text.gravity = Gravity.CENTER
            }else{
                text.gravity = Gravity.START
            }
            root.setOnClickListener {
                onChoiceSelection(positionInAdapter)
            }
        }
    }

    override fun isTheSame(newItem: AdapterItem<*>): Boolean {
        return newItem is GeneralChoiceAdapterItem
    }

    override fun isContentsTheSame(newItem: AdapterItem<*>): Boolean {
        return isTheSame(newItem) && (newItem as GeneralChoiceAdapterItem).choice == choice
    }
}

class GeneralChoiceViewHolder(view: View) : BaseViewHolder(view) {
    val itemBinding: ItemGeneralChoiceBinding by BindListItem(view)
}
