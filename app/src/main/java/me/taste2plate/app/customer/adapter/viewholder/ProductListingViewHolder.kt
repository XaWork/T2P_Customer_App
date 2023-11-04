package me.taste2plate.app.customer.adapter.viewholder

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.facebook.appevents.AppEventsConstants
import com.facebook.appevents.AppEventsLogger
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.view.SimpleDraweeView
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.ui.product.ProductActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.ProductViewModel
import me.taste2plate.app.data.api.AnalyticsAPI
import me.taste2plate.app.data.api.LogRequest
import me.taste2plate.app.models.newproducts.NewProduct
import org.json.JSONObject


class ProductListingViewHolder(
    val context: Context,
    itemView: View,
    val viewModel: ProductViewModel,
    val lifecycleOwner: LifecycleOwner
) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    fun renderView(product: NewProduct) {
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
                           // sendProductInfoToCleverTap(product)

                            //send event info
                            val analytics = AnalyticsAPI()
                            val appUtils = AppUtils(context)
                            val logRequest = LogRequest(
                                category = appUtils.referralInfo[0],
                                token = appUtils.referralInfo[1],
                                type = "wishlist",
                                event = "add",
                                page_name = "/ProductList",
                                source = "android",
                                geo_ip = AppUtils(context).ipAddress,
                                user_id = AppUtils(context).user.id,
                                product_id = product._id
                            )
                            analytics.addLog(logRequest)

                            /*//facebook
                            val pixelId = context.getString(R.string.facebook_pixel_id)
                            AccessToken.getCurrentAccessToken()
                                ?.let { it1 -> sendGraphRequest(it1, pixelId) }*/

                            val logger = AppEventsLogger.newLogger(context)
                            val params = Bundle()

                            params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, "INR");
                            params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, "product");
                            params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, product._id);
                            logger.logEvent(AppEventsConstants.EVENT_NAME_ADDED_TO_WISHLIST)

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


    fun sendGraphRequest(accessToken: AccessToken, pixelId: String) {
        val parameters = JSONObject()
        parameters.put("data", "[{\"action_source\":\"app\",\"app_data\":{\"advertiser_tracking_enabled\":false,\"application_tracking_enabled\":false,\"extinfo\":[\"a2\",\"com.some.app\",\"771\",\"Version 7.7.1\",\"10.1.1\",\"OnePlus6\",\"en_US\",\"GMT-1\",\"TMobile\",\"1920\",\"1080\",\"2.00\",\"2\",\"128\",\"8\",\"USA/New York\"],\"event_id\":12345,\"event_name\":\"TestEvent\",\"event_time\":1698745588,\"user_data\":{\"client_ip_address\":\"254.254.254.254\",\"client_user_agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0\",\"em\":\"f660ab912ec121d1b1e928a0bb4bc61b15f5ad44d5efdc4e1c92a25e99b8e44a\"},\"test_event_code\":\"TEST39443\",\"fbc\":\"$pixelId\",\"fbp\":\"$pixelId\"}], \"test_event_code\", \"TEST39443\"")


        val request = GraphRequest.newPostRequest(
            accessToken,
            "/1667197037079128/events",
            parameters,
            object : GraphRequest.Callback {
                override fun onCompleted(response: GraphResponse) {
                    // Insert your code here
                }
            }
        )
        request.executeAsync()
    }

    private fun sendProductInfoToCleverTap(product: NewProduct) {
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