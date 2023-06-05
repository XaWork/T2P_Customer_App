package me.taste2plate.app.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.viewholder.AllOfferViewHolder
import me.taste2plate.app.models.AllOffersItem

class AllOffersAdapter(private val offerList: List<AllOffersItem>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<AllOfferViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOfferViewHolder {
        return AllOfferViewHolder(
            parent.context,
            LayoutInflater.from(parent.context).inflate(R.layout.all_offer_item, parent, false),
            ""
        )
    }

    override fun onBindViewHolder(holderAll: AllOfferViewHolder, position: Int) {
        holderAll.renderView(offerList[position])
    }


    override fun getItemCount(): Int {
        return if (offerList.isEmpty()) 0 else offerList.size
    }
}
