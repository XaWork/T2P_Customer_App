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
import me.taste2plate.app.customer.ui.product.ShopActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.models.address.cityzip.CityOption
import me.taste2plate.app.models.home.MostOrderedItem
import me.taste2plate.app.models.home.TopBrands
import me.taste2plate.app.models.newproducts.ShowError
import org.greenrobot.eventbus.EventBus

class SingleTopBrandItem(private val cityOption:TopBrands): AdapterItem<SingleTopBrandViewHolder>() {

    override val layoutId: Int = R.layout.single_home_top_brand_item

    override fun onCreateViewHolder(view: View): SingleTopBrandViewHolder = SingleTopBrandViewHolder(view)

    override fun updateItemViews(viewHolder: SingleTopBrandViewHolder) {
        viewHolder.itemBinding.run {
            tvTitle.text = cityOption.name
          /*  Picasso
                .get()
                .load(cityOption.file)
                .resize(100,100)
                .into(cateImage)*/

            cateImage.setImageURI(cityOption.file)
            cateImage.hierarchy.actualImageScaleType = ScalingUtils.ScaleType.CENTER_CROP

            cateImage.setOnClickListener{
                val intent = Intent(root.context, ShopActivity::class.java)
                intent.putExtra("id", cityOption.id)
                intent.putExtra("name", cityOption.name)
                intent.putExtra("type", "Brand")
                root.context.startActivity(intent)
            }

            tvTitle.setOnClickListener {
                if(AppUtils(root.context).defaultAddress!=null){
                    val intent = Intent(root.context, ShopActivity::class.java)
                    intent.putExtra("id", cityOption.id)
                    intent.putExtra("name", cityOption.name)
                    intent.putExtra("type", "Brand")
                    root.context.startActivity(intent)
                }else{
                    EventBus.getDefault().post(ShowError())
                }
            }
        }
    }

    override fun isTheSame(newItem: AdapterItem<*>): Boolean {
        return newItem is SingleTopBrandItem
    }

    override fun isContentsTheSame(newItem: AdapterItem<*>): Boolean {
        return isTheSame(newItem) && (newItem as SingleTopBrandItem).cityOption == cityOption
    }

}
class SingleTopBrandViewHolder(view:View):BaseViewHolder(view){
    val itemBinding:SingleHomeTopBrandItemBinding by BindListItem(view)
}