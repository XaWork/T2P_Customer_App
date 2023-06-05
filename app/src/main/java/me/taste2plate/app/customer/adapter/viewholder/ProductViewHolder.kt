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
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.view.SimpleDraweeView
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.BaseActivity
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.events.DeleteFromWishlistEvent
import me.taste2plate.app.customer.ui.home.HomeFragment
import me.taste2plate.app.customer.ui.product.ProductActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.ProductViewModel
import me.taste2plate.app.models.home.ProductDeal
import org.greenrobot.eventbus.EventBus


class ProductViewHolder(
    val context: Context,
    itemView: View,
    private val viewModel: ProductViewModel,
    private val lifeCycleOwner: LifecycleOwner
) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    fun renderView(product: ProductDeal) {
        val ivImage = itemView.findViewById<SimpleDraweeView>(R.id.ivImage)
        val ivAddToWish = itemView.findViewById<ImageView>(R.id.addToWishlist)
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
        val tvCallToAction = itemView.findViewById<TextView>(R.id.tvCallToAction)
        val tvOnSale = itemView.findViewById<TextView>(R.id.tvOnSale)
        val progressBar = itemView.findViewById<ProgressBar>(R.id.progressBar)

        tvTitle.text = product.name
        tvDescription.text = Html.fromHtml(product.desc)
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

        ivAddToWish.setOnClickListener {
            EventBus.getDefault().post(DeleteFromWishlistEvent(position))

            viewModel.addToWishlist(AppUtils(context).user.id, product._id)
                .observe(lifeCycleOwner) { response ->
                    when (response.status()) {
                        Status.LOADING -> {
                            ivAddToWish.visibility = View.GONE
                            progressBar.visibility = View.VISIBLE
                        }
                        Status.SUCCESS -> {
                            Log.e("wishlist", "home product add to wishlist successfully")
                            progressBar.visibility = View.GONE
                            ivAddToWish.visibility = View.VISIBLE
                            ivAddToWish.setImageResource(R.drawable.product_in_wishlist)
                            Toast.makeText(context, response.data().message, Toast.LENGTH_SHORT)
                                .show()

                            BaseActivity().wishlistCounter+=1
                        }
                        Status.ERROR -> {
                            progressBar.visibility = View.GONE
                            ivAddToWish.visibility = View.VISIBLE
                        }
                        Status.EMPTY -> {
                            progressBar.visibility = View.GONE
                            ivAddToWish.visibility = View.VISIBLE
                        }
                    }
                }
        }

    }
}