package me.taste2plate.app.customer.adapter.viewholder

import android.content.Context
import android.content.Intent
import android.text.SpannableString
import android.view.View
import android.widget.TextView
import me.taste2plate.app.customer.toDate
import me.taste2plate.app.customer.ui.order.OrderActivity
import me.taste2plate.app.customer.utils.DateUtils
import me.taste2plate.app.models.order.Order
import java.text.SimpleDateFormat
import java.util.*


class OrderViewHolder(val context: Context, itemView: View) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    fun renderView(order: Order, serverTime: String) {
        val tvStatus = itemView.findViewById<TextView>(me.taste2plate.app.customer.R.id.orderStatus)
        val tvOrderId = itemView.findViewById<TextView>(me.taste2plate.app.customer.R.id.orderId)
        val tvDate = itemView.findViewById<TextView>(me.taste2plate.app.customer.R.id.orderDate)
        val orderOtp = itemView.findViewById<TextView>(me.taste2plate.app.customer.R.id.orderOtp)

        tvOrderId.text = "#" + order.orderid
        tvStatus.text = order.status.replace("_", " ").capitalize()

        tvDate.text = order.created_date.toDate()

        if (order.otp != null && order.otp.isNotEmpty())
            orderOtp.text = "OTP : ${order.otp}"
        else
            orderOtp.visibility = View.GONE

        itemView.setOnClickListener {
            val intent = Intent(context, OrderActivity::class.java)
            intent.putExtra("order", order)
            intent.putExtra("time", serverTime)
            context.startActivity(intent)
        }
    }

}