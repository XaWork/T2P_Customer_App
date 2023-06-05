package me.taste2plate.app.customer.adapter.viewholder

import android.content.Context
import android.text.Html
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.TextView
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.interfaces.OnApplyCouponListener
import me.taste2plate.app.models.CouponData

class CouponViewHolder(
    val context: Context,
    itemView: View,
    val isFromCheckout: Boolean,
    val listener: OnApplyCouponListener
) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    fun renderView(coupon: CouponData) {
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
        val tvApplyCoupon = itemView.findViewById<Button>(R.id.tvApplyCoupon)

        tvTitle.text = coupon.couponName?.toUpperCase()
        tvDescription.text = Html.fromHtml("" + coupon.couponDescription)
        if (isFromCheckout) {
            tvApplyCoupon.visibility = VISIBLE

            tvApplyCoupon.setOnClickListener {
                listener.onApplyCoupon(
                    coupon.id.toString(),
                    coupon.couponName!!,
                    coupon.couponAmount
                )
            }
        } else {
            tvApplyCoupon.visibility = GONE
        }


    }


}