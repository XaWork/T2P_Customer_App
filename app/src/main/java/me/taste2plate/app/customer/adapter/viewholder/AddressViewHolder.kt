package me.taste2plate.app.customer.adapter.viewholder

import android.content.Context
import android.view.View
import android.view.View.GONE
import android.widget.LinearLayout
import android.widget.TextView
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.interfaces.OnSelectListener
import me.taste2plate.app.models.UserAddress
import me.taste2plate.app.models.address.Address


class AddressViewHolder(
    val context: Context,
    itemView: View,
    val listener: OnSelectListener,
    val fromScreen: String
) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    fun renderView(userAddress: Address) {
        val tvDescription = itemView.findViewById<TextView>(R.id.tvNameAddress)
        val selectAddress = itemView.findViewById<TextView>(R.id.btnSelectAddress)
        val editAddress = itemView.findViewById<TextView>(R.id.btnEditAddress)
        val deleteAddress = itemView.findViewById<TextView>(R.id.btnDeleteAddress)
        val control = itemView.findViewById<LinearLayout>(R.id.control)

        tvDescription.text = userAddress.address

        if (fromScreen.contentEquals("Profile")) {
            selectAddress.visibility = GONE
        }
        if(fromScreen.contentEquals("sheet")){
            selectAddress.visibility = GONE
            editAddress.visibility = GONE
            deleteAddress.visibility = GONE
            control.visibility = GONE
        }
        selectAddress.setOnClickListener {
            listener.onSelectItem(layoutPosition, "Select")
        }

        deleteAddress.setOnClickListener {
            listener.onSelectItem(layoutPosition, "Delete")
        }

        editAddress.setOnClickListener {
            listener.onSelectItem(layoutPosition, "Edit")
        }

        itemView.setOnClickListener {
            listener.onSelectItem(layoutPosition, "Select")
        }
    }
}