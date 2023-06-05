package me.taste2plate.app.customer.adapter.viewholder

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.ui.coupon.CouponListActivity
import me.taste2plate.app.customer.ui.coupon.DealsNComboListActivity
import me.taste2plate.app.models.AllOffersItem


class AllOfferViewHolder(
    val context: Context,
    itemView: View,
    private val type: String
) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    fun renderView(allOffersItem: AllOffersItem) {
        val ivImage = itemView.findViewById<ImageView>(R.id.ivImage)
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)

        tvTitle.text = allOffersItem.title
        Picasso
            .get()
            .load(allOffersItem.image)
            .placeholder(R.drawable.image_border)
            .resize(100, 100)
            .into(ivImage)


        itemView.setOnClickListener {

            when (allOffersItem.id) {
                29077, 29081, 29083 -> {
                    val intent = Intent(context, DealsNComboListActivity::class.java)
                    intent.putExtra("Title", allOffersItem.title)
                    intent.putExtra("Id", allOffersItem.id!!)
                    context.startActivity(intent)
                }

                29079 -> {
                    val intent = Intent(context, CouponListActivity::class.java)
                    intent.putExtra("Title", allOffersItem.title)
                    context.startActivity(intent)
                }

                else -> {
                    val intent = Intent(context, CouponListActivity::class.java)
                    intent.putExtra("Title", allOffersItem.title)
                    context.startActivity(intent)
                }


            }
        }

    }


}