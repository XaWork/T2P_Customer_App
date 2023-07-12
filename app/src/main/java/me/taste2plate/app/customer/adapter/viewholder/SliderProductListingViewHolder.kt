package me.taste2plate.app.customer.adapter.viewholder

import android.content.Context
import android.content.Intent
import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.clevertap.android.sdk.CleverTapAPI
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.view.SimpleDraweeView
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.ui.product.ProductActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.ProductViewModel
import me.taste2plate.app.data.api.AnalyticsAPI
import me.taste2plate.app.data.api.LogRequest
import me.taste2plate.app.models.home.ProductDeal
import me.taste2plate.app.models.newproducts.NewProduct


class SliderProductListingViewHolder(
    val context: Context,
    itemView: View,
    val viewModel: ProductViewModel,
    val lifecycleOwner: LifecycleOwner
) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    fun renderView(product: ProductDeal) {
        val ivImage = itemView.findViewById<SimpleDraweeView>(R.id.ivImage)
        val productName = itemView.findViewById<TextView>(R.id.productName)
        val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
        val tvCallToAction = itemView.findViewById<TextView>(R.id.tvCallToAction)
        val tvOnSale = itemView.findViewById<TextView>(R.id.tvOnSale)
        val addToWishlist = itemView.findViewById<ImageView>(R.id.addToWishlist)
        val progressBar = itemView.findViewById<ProgressBar>(R.id.progressBar)

        Log.e("tes",product.name)

        productName.text = product.name
        if (product.desc.trim().isNotEmpty()) {
            tvDescription.visibility = View.VISIBLE
            tvDescription.text = Html.fromHtml(product.desc, )
        } else {
            tvDescription.visibility = View.GONE
        }
        var finalStrUrl = ""
        if (product.file != null && product.file!!.isNotEmpty()) {
            finalStrUrl = product.file!![0].location
        }

        ivImage.setImageURI(finalStrUrl);
        ivImage.hierarchy.actualImageScaleType = ScalingUtils.ScaleType.CENTER_CROP;
        val regularPrice =
            if (TextUtils.isEmpty(product.price)) "0" else product.price
        val salePrice = product.selling_price
        if (!TextUtils.isEmpty(salePrice)) {
            tvCallToAction.text = context.getString(R.string.Rs_double, salePrice.toFloat())
            tvOnSale.visibility = View.VISIBLE
        } else {
            tvCallToAction.text = context.getString(R.string.Rs_double, regularPrice.toFloat())
            tvOnSale.visibility = View.GONE
        }

        itemView.setOnClickListener {
            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra("productId", product._id)
            context.startActivity(intent)
        }

        addToWishlist.setOnClickListener {
            viewModel.addToWishlist(AppUtils(context).user.id, product._id)
                .observe(lifecycleOwner) { response ->
                    when (response.status()) {
                        Status.LOADING -> {
                            addToWishlist.visibility = View.GONE
                            progressBar.visibility = View.VISIBLE
                        }
                        Status.SUCCESS -> {
                            progressBar.visibility = View.GONE
                            addToWishlist.visibility = View.VISIBLE
                            addToWishlist.setImageResource(R.drawable.product_in_wishlist)
                            Toast.makeText(context, response.data().message, Toast.LENGTH_SHORT)
                                .show()

                            //send event info
                            val analytics = AnalyticsAPI()
                            val logRequest = LogRequest(
                                type = "add to cart",
                                event = "add product to wishlist",
                                page_name = "/ProductList",
                                source = "android",
                                user_id = AppUtils(context).user.id,
                                product_id = product._id
                            )
                            analytics.addLog(logRequest)
                        }
                        Status.EMPTY -> {
                            progressBar.visibility = View.GONE
                            addToWishlist.visibility = View.VISIBLE
                            Toast.makeText(context, "Try again!", Toast.LENGTH_SHORT).show()
                        }
                        Status.ERROR -> {
                            progressBar.visibility = View.GONE
                            addToWishlist.visibility = View.VISIBLE
                            Toast.makeText(context, "Try again!", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
        }

    }

    private fun sendProductInfoToCleverTap(product: ProductDeal) {
        val prodViewedAction = mapOf(
            "Product Name" to product.name,
            "Category" to product.category,
            "Brand" to product.brand,
            "City" to product.city,
            "Price" to product.price,
        )
        Log.d("clevertap", "$prodViewedAction")
        CleverTapAPI.getDefaultInstance(context)?.pushEvent("Add to wishlist", prodViewedAction)
    }
}