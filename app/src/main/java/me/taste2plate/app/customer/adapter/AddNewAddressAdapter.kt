package me.taste2plate.app.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.viewholder.AddressViewHolder
import me.taste2plate.app.models.UserAddress
import me.taste2plate.app.customer.interfaces.OnSelectListener
import me.taste2plate.app.models.address.Address

class AddNewAddressAdapter(
    private val addressList: ArrayList<Address>,
    private val listener: OnSelectListener,
    private val fromScreen: String
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<AddressViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        return AddressViewHolder(
            parent.context,
            LayoutInflater.from(parent.context).inflate(
                R.layout.new_address_item,
                parent,
                false
            ),
            listener,fromScreen
        )
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.renderView(addressList[position])
    }


    override fun getItemCount(): Int {
        return (if (addressList.isEmpty()) 0 else addressList.size)
    }
}
