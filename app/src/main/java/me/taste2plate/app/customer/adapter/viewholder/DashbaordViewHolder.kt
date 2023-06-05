package me.taste2plate.app.customer.adapter.viewholder

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.interfaces.OnSelectListener
import me.taste2plate.app.models.Dashboard
import me.taste2plate.app.models.DataItem


class DashbaordViewHolder(
    val context: Context,
    itemView: View,
    val listener: OnSelectListener
) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    fun renderView(dashItem: Dashboard) {
        val ivLongImage = itemView.findViewById<ImageView>(R.id.ivDashClick)
        val ivShortImage = itemView.findViewById<ImageView>(R.id.ivShortImage)
        val itemId = itemView.findViewById<TextView>(R.id.itemId)


        if (!TextUtils.isEmpty(dashItem.icon)) {
            val finalStrUrl = dashItem.icon
            Picasso.get().load(finalStrUrl).into(ivShortImage)
        }
        if (!TextUtils.isEmpty(dashItem.backgroundImage)) {
            val finalStrUrl = dashItem.backgroundImage
            Picasso.get().load(finalStrUrl).into(ivLongImage)
        }

        itemId.text = dashItem.title
        itemView.setOnClickListener {
            listener.onSelectItem(layoutPosition, "Edit")
        }
    }
}