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
import me.taste2plate.app.customer.events.DeleteFromWishlistEvent
import me.taste2plate.app.customer.ui.product.ProductActivity
import me.taste2plate.app.models.wishlist.WishlistItemResponse
import org.greenrobot.eventbus.EventBus


class WishlistViewHolder(val context: Context, itemView: View) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {


    @SuppressLint("StringFormatInvalid")
    fun renderView(wishLineItem: WishlistItemResponse.Result, position: Int) {

        val ivImage = itemView.findViewById<ImageView>(R.id.ivImage)
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
        val tvPrice = itemView.findViewById<TextView>(R.id.tvPrice)
        val deleteFromWish = itemView.findViewById<ImageView>(R.id.deleteFromWishlsit)

        val product = wishLineItem.product

        if (product != null) {
            tvTitle.text = product.name
            tvDescription.text = Html.fromHtml(product.desc)

            if (product.file.isNotEmpty()) {
                val finalStrUrl = product.file!![0].location
                Picasso.get().load(finalStrUrl).resize(100, 100).into(ivImage)
            }

            tvTitle.text = product.name

            val regularPrice = product.price
            val salePrice = product.sellingPrice

            tvPrice.text = SpannableString(context.getString(R.string.Rs, regularPrice?.toLong()))

           /* if (!TextUtils.isEmpty(salePrice.toString())) {
                tvPrice.text = SpannableString(context.getString(R.string.Rs, salePrice?.toLong()))
            } else {
                tvPrice.text =
                    SpannableString(context.getString(R.string.Rs, regularPrice.toLong()))
            }*/

            itemView.setOnClickListener {
                val intent = Intent(context, ProductActivity::class.java)
                intent.putExtra("productId", product.id)
                context.startActivity(intent)
            }

            deleteFromWish.setOnClickListener {
                EventBus.getDefault().post(DeleteFromWishlistEvent(position))
            }
        }
    }

}