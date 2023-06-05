package me.taste2plate.app.customer.items

import android.view.View
import com.fueled.reclaim.AdapterItem
import com.fueled.reclaim.BaseViewHolder
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.BindListItem
import me.taste2plate.app.customer.databinding.TransactionItemBinding
import me.taste2plate.app.models.wallet.Transaction

class TransactionAdapterItem(private val transactionItem:Transaction):
    AdapterItem<TransactionAdapterViewHolder>() {
    override val layoutId: Int = R.layout.transaction_item

    override fun onCreateViewHolder(view: View) =  TransactionAdapterViewHolder(view)

    override fun updateItemViews(viewHolder: TransactionAdapterViewHolder) {
        viewHolder.itemBinding.run {
            val transactionHeading = if(transactionItem.type==0) "Points Earned" else "Points Redeemed"
            transactionType.text = transactionHeading
            transactionNotes.text = transactionItem.note
            points.text = "${transactionItem.point} Points"
        }
    }

    override fun isContentsTheSame(newItem: AdapterItem<*>): Boolean {
        return isTheSame(newItem) && (newItem as TransactionAdapterItem).transactionItem == transactionItem
    }

    override fun isTheSame(newItem: AdapterItem<*>): Boolean {
        return newItem is TransactionAdapterItem && newItem.transactionItem.id == transactionItem.id
    }
}


class TransactionAdapterViewHolder(view:View): BaseViewHolder(view) {
    val itemBinding: TransactionItemBinding by BindListItem(view)
}
