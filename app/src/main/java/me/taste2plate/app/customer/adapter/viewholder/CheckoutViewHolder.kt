package me.taste2plate.app.customer.adapter.viewholder

import android.annotation.SuppressLint
import android.content.Context
import android.text.SpannableString
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import me.taste2plate.app.customer.R
import me.taste2plate.app.models.order.OrderProductItem


class CheckoutViewHolder(val context: Context, itemView: View) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    @SuppressLint("StringFormatInvalid")
    fun renderView(cartLineItem: OrderProductItem) {
        val ivImage = itemView.findViewById<ImageView>(R.id.ivImage)
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
        val tvPriceQuantity = itemView.findViewById<TextView>(R.id.tvPriceQuantity)
        val tvTotalItemCost = itemView.findViewById<TextView>(R.id.tvTotalItemCost)


        tvTitle.text = cartLineItem.product.name

        val files = cartLineItem.product.file
        if (!files.isNullOrEmpty()) {
            val finalStrUrl = files[0].location
            Picasso.get().load(finalStrUrl).resize(130, 100).into(ivImage)
        }

        tvDescription.text = cartLineItem.product.desc

        val perItemCost = cartLineItem.price / cartLineItem.quantity
        val totalCost = cartLineItem.price



        val precisionPrice = "%.2f".format(perItemCost)
        val totalCostPrecision = "%.2f".format(totalCost)

        tvPriceQuantity.text =  "Rs $precisionPrice  x  ${cartLineItem.quantity}"

        tvTotalItemCost.text =  "Rs $totalCostPrecision"
    }

}