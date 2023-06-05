package me.taste2plate.app.customer.items

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fueled.reclaim.AdapterItem
import com.fueled.reclaim.BaseViewHolder
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.BindListItem
import me.taste2plate.app.customer.databinding.PlanItemBinding
import me.taste2plate.app.models.membership.Plan

class PlanAdapterItem(private val planItem:Plan, private val listener: (Plan)-> Unit):
    AdapterItem<PlanAdapterViewHolder>() {
    override val layoutId: Int = R.layout.plan_item

    override fun onCreateViewHolder(view: View) =  PlanAdapterViewHolder(view)

    override fun updateItemViews(viewHolder: PlanAdapterViewHolder) {

        viewHolder.run{
            itemBinding.run {
                planModel = planItem
            }

            itemBinding.buyButton.setOnClickListener {
                listener(planItem)
            }
        }
    }

    override fun isContentsTheSame(newItem: AdapterItem<*>): Boolean {
        return isTheSame(newItem) && (newItem as PlanAdapterItem).planItem == planItem
    }

    override fun isTheSame(newItem: AdapterItem<*>): Boolean {
        return newItem is PlanAdapterItem && newItem.planItem.id == planItem.id
    }
}


class PlanAdapterViewHolder(view:View): BaseViewHolder(view) {
    val itemBinding: PlanItemBinding by BindListItem(view)
}
