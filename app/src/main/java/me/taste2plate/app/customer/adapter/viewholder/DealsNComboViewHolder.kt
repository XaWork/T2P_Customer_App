package me.taste2plate.app.customer.adapter.viewholder

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.view.SimpleDraweeView
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.ui.product.ProductActivity
import me.taste2plate.app.models.Product


class DealsNComboViewHolder(val context: Context, itemView: View) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    fun renderView(product: Product) {
        val ivImage = itemView.findViewById<SimpleDraweeView>(R.id.ivImage)
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
        val tvCallToAction = itemView.findViewById<TextView>(R.id.tvCallToAction)
        val tvOnSale = itemView.findViewById<TextView>(R.id.tvOnSale)
        val tvSalePrice = itemView.findViewById<TextView>(R.id.tvSalePrice)

        tvTitle.text = product.title
        tvDescription.text = Html.fromHtml(product.description)
        var finalStrUrl = product.image.replace("https", "http")


        ivImage.setImageURI(finalStrUrl);
        ivImage.hierarchy.actualImageScaleType = ScalingUtils.ScaleType.CENTER_CROP;
        val regularPrice =
            if (TextUtils.isEmpty(product.regular_price)) "0" else product.regular_price

        if (!TextUtils.isEmpty(product.sale_price)) {
            tvSalePrice.text =
                "Sale Price: " + context.getString(R.string.Rs_double, product.sale_price.toFloat())
            tvSalePrice.visibility = View.VISIBLE
            tvOnSale.visibility = View.VISIBLE
            tvCallToAction.paintFlags = tvCallToAction.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }

        tvCallToAction.text =
            "Regular Price: " + context.getString(R.string.Rs_double, regularPrice.toFloat())



        itemView.setOnClickListener {
            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra("productId", product.post_id)

            context.startActivity(intent)
        }

    }


}