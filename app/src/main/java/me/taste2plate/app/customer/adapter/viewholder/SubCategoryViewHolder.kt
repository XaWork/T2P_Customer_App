package me.taste2plate.app.customer.adapter.viewholder

import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.view.SimpleDraweeView
import com.squareup.picasso.Picasso
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.ui.product.ShopActivity
import me.taste2plate.app.models.Category
import me.taste2plate.app.models.SubCategories


class SubCategoryViewHolder(
    val context: Context,
    itemView: View
) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    fun renderView(category: SubCategories.Result) {
        val ivImage = itemView.findViewById<SimpleDraweeView>(R.id.ivImage)
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)

        tvTitle.text = category.name
        tvDescription.text = Html.fromHtml(category.description)

        if (category.file.isNotEmpty()) {
            val finalStrUrl = category.file
            ivImage.setImageURI(finalStrUrl)
            ivImage.hierarchy.actualImageScaleType = ScalingUtils.ScaleType.CENTER_CROP;
        }

        itemView.setOnClickListener {
            val intent = Intent(context, ShopActivity::class.java)
            intent.putExtra("categoryId", category.id)
            intent.putExtra("name", category.name)
            intent.putExtra("type", "category")
            context.startActivity(intent)
        }
    }
}