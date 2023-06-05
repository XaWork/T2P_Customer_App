package me.taste2plate.app.customer.ui.items

import android.content.Intent
import android.view.View
import com.facebook.drawee.drawable.ScalingUtils
import com.fueled.reclaim.AdapterItem
import com.fueled.reclaim.BaseViewHolder
import com.squareup.picasso.Picasso
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.BindListItem
import me.taste2plate.app.customer.databinding.SingleHomeCategoryItemBinding
import me.taste2plate.app.customer.databinding.SingleHomeTopBrandItemBinding
import me.taste2plate.app.customer.databinding.SingleHomeTopMostOrderedProductsItemBinding
import me.taste2plate.app.customer.ui.product.ShopActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.models.address.cityzip.CityOption
import me.taste2plate.app.models.home.MostOrderedItem
import me.taste2plate.app.models.home.TopBrands
import me.taste2plate.app.models.home.TopMostOrderedProducts
import me.taste2plate.app.models.newproducts.ShowError
import org.greenrobot.eventbus.EventBus

class SingleTopMostOrderProduct(private val mostOrderItems:TopMostOrderedProducts): AdapterItem<SingleTopMostOrderProductViewHolder>() {

    override val layoutId: Int = R.layout.single_home_top_most_ordered_products_item

    override fun onCreateViewHolder(view: View): SingleTopMostOrderProductViewHolder = SingleTopMostOrderProductViewHolder(view)

    override fun updateItemViews(viewHolder: SingleTopMostOrderProductViewHolder) {
        viewHolder.itemBinding.run {
           /* Picasso
                .get()
                .load(mostOrderItems.image)
                .resize(100,100)
                .into(cateImage)*/

            cateImage.setImageURI(mostOrderItems.image)
            cateImage.hierarchy.actualImageScaleType = ScalingUtils.ScaleType.CENTER_CROP

            cateImage.setOnClickListener{
                val intent = Intent(root.context, ShopActivity::class.java)
                intent.putExtra("id", mostOrderItems._id)
                intent.putExtra("name", mostOrderItems.slider_name)
                intent.putExtra("type", "slider")
                root.context.startActivity(intent)
            }

          /*  tvTitle.setOnClickListener {
                if(AppUtils(root.context).defaultAddress!=null){
                    val intent = Intent(root.context, ShopActivity::class.java)
                    intent.putExtra("id", cityOption.id)
                    intent.putExtra("name", cityOption.name)
                    intent.putExtra("type", "Brand")
                    root.context.startActivity(intent)
                }else{
                    EventBus.getDefault().post(ShowError())
                }
            }*/
        }
    }

    override fun isTheSame(newItem: AdapterItem<*>): Boolean {
        return newItem is SingleTopMostOrderProduct
    }

    override fun isContentsTheSame(newItem: AdapterItem<*>): Boolean {
        return isTheSame(newItem) && (newItem as SingleTopMostOrderProduct).mostOrderItems == mostOrderItems
    }

}
class SingleTopMostOrderProductViewHolder(view:View):BaseViewHolder(view){
    val itemBinding:SingleHomeTopMostOrderedProductsItemBinding by BindListItem(view)
}