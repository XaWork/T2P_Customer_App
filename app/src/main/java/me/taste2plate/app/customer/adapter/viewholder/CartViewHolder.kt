package me.taste2plate.app.customer.adapter.viewholder

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.text.Html
import android.text.SpannableString
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.events.AddQuantityEvent
import me.taste2plate.app.customer.events.LessQuantityEvent
import me.taste2plate.app.customer.models.CartLineItem
import me.taste2plate.app.customer.ui.product.ProductActivity
import me.taste2plate.app.models.cart.CartItem
import org.greenrobot.eventbus.EventBus


class CartViewHolder(val context: Context, itemView: View) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {


    @SuppressLint("StringFormatInvalid")
    fun renderView(cartLineItem: CartItem) {
        val ivImage = itemView.findViewById<ImageView>(R.id.ivImage)
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
        val tvPrice = itemView.findViewById<TextView>(R.id.tvPrice)

        val tvAdd = itemView.findViewById<TextView>(R.id.tvAdd)
        val tvQty = itemView.findViewById<TextView>(R.id.tvQty)
        val tvLess = itemView.findViewById<TextView>(R.id.tvReduce)


        tvQty.text = "" + cartLineItem.quantity

        val product = cartLineItem.product!!

        tvTitle.text = cartLineItem.productname
        tvDescription.text = Html.fromHtml(product.desc)

        if (product.file != null && product.file!!.isNotEmpty()) {
            val finalStrUrl = product.file!![0].location
            Picasso.get().load(finalStrUrl).resize(100, 100).into(ivImage)
        }

        tvTitle.text = product.name

        val regularPrice = product.price
        val salePrice = product.selling_price

        if (!TextUtils.isEmpty(salePrice)) {
            tvPrice.text = SpannableString(context.getString(R.string.Rs, salePrice.toLong()))
        } else {
            tvPrice.text = SpannableString(context.getString(R.string.Rs, regularPrice.toLong()))
        }

        tvAdd.setOnClickListener {
            EventBus.getDefault().post(AddQuantityEvent(cartLineItem))
        }

        tvLess.setOnClickListener {
            if (cartLineItem.quantity > 0) {
                EventBus.getDefault().post(LessQuantityEvent(cartLineItem))
            }
        }
    }

}