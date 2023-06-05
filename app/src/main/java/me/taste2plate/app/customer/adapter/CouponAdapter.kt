package me.taste2plate.app.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.viewholder.CouponViewHolder
import me.taste2plate.app.customer.interfaces.OnApplyCouponListener
import me.taste2plate.app.models.CouponData

class CouponAdapter(private val coupons: List<CouponData>,val isFromCheckout:Boolean,val listener: OnApplyCouponListener) : androidx.recyclerview.widget.RecyclerView.Adapter<CouponViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponViewHolder {
        return CouponViewHolder(
            parent.context,
            LayoutInflater.from(parent.context).inflate(R.layout.single_coupon_item, parent, false),
            isFromCheckout,
            listener
        )
    }

    override fun onBindViewHolder(holder: CouponViewHolder, position: Int) {
        holder.renderView(coupons[position])
    }


    override fun getItemCount(): Int {
        return if (coupons.isEmpty()) 0 else coupons.size
    }
}
