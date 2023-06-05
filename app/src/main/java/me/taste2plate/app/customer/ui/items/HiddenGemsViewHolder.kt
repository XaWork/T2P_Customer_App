package me.taste2plate.app.customer.ui.items

import android.content.Context
import android.content.Intent
import android.text.Html
import android.text.TextUtils
import android.view.View
import com.facebook.drawee.drawable.ScalingUtils
import com.fueled.reclaim.AdapterItem
import com.fueled.reclaim.BaseViewHolder
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.BindListItem
import me.taste2plate.app.customer.databinding.SingleHomeRestaurantItemBinding
import me.taste2plate.app.customer.ui.product.ProductActivity
import me.taste2plate.app.customer.ui.product.ShopActivity
import me.taste2plate.app.models.home.HiddenGems

class HiddenGemsViewHolder(
    private val hiddenGems: HiddenGems
) : AdapterItem<RestaurantViewHolder>() {

    override val layoutId: Int = R.layout.single_home_restaurant_item

    override fun onCreateViewHolder(view: View): RestaurantViewHolder = RestaurantViewHolder(view)

    override fun isTheSame(newItem: AdapterItem<*>): Boolean {
        return newItem is HiddenGemsViewHolder
    }

    override fun isContentsTheSame(newItem: AdapterItem<*>): Boolean {
        return isTheSame(newItem) && (newItem as HiddenGemsViewHolder).hiddenGems == hiddenGems
    }

    override fun updateItemViews(viewHolder: RestaurantViewHolder) {
        viewHolder.itemBinding.run {
            tvTitle.text = hiddenGems.name
            tvDescription.text = Html.fromHtml(hiddenGems.desc)
            val finalStrUrl = hiddenGems.file
            ivImage.setImageURI(finalStrUrl)
            ivImage.hierarchy.actualImageScaleType = ScalingUtils.ScaleType.CENTER_CROP
            /*val regularPrice =
                if (TextUtils.isEmpty(hiddenGems.price)) "0" else hiddenGems.price
            val salePrice = hiddenGems.selling_price

            tvCallToAction.text =
                root.context.getString(R.string.Rs_double, salePrice.toFloat())*/

            if (hiddenGems.desc.isEmpty()) {
                tvDescription.visibility = View.GONE
            } else {
                tvDescription.visibility = View.VISIBLE
            }

            root.setOnClickListener {
                val intent = Intent(root.context, ShopActivity::class.java)
                intent.putExtra("id", hiddenGems.id)
                intent.putExtra("name", hiddenGems.name)
                intent.putExtra("type", "Brand")
                root.context.startActivity(intent)

                /*if(AppUtils(root.context).defaultAddress!=null) {
                    val intent = Intent(root.context, ProductActivity::class.java)
                    intent.putExtra("productId", product._id)
                    root.context.startActivity(intent)
                }else{
                    EventBus.getDefault().post(ShowError())
                }*/
            }
        }
    }

}

class RestaurantViewHolder(view: View) : BaseViewHolder(view) {
    val itemBinding: SingleHomeRestaurantItemBinding by BindListItem(view)
}