package me.taste2plate.app.customer.ui.items

import android.content.Intent
import android.view.View
import com.fueled.reclaim.AdapterItem
import com.fueled.reclaim.BaseViewHolder
import com.squareup.picasso.Picasso
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.BindListItem
import me.taste2plate.app.customer.databinding.SingleHomeCategoryItemBinding
import me.taste2plate.app.customer.databinding.SingleHomeTopBrandItemBinding
import me.taste2plate.app.customer.ui.product.ProductActivity
import me.taste2plate.app.customer.ui.product.ShopActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.models.address.cityzip.CityOption
import me.taste2plate.app.models.home.MostOrderedItem
import me.taste2plate.app.models.newproducts.ShowError
import org.greenrobot.eventbus.EventBus

class SingleMostOrderedItem(private val cityOption:MostOrderedItem): AdapterItem<SingleMostOrderItemViewHolder>() {

    override val layoutId: Int = R.layout.single_home_top_brand_item

    override fun onCreateViewHolder(view: View): SingleMostOrderItemViewHolder = SingleMostOrderItemViewHolder(view)

    override fun updateItemViews(viewHolder: SingleMostOrderItemViewHolder) {
        viewHolder.itemBinding.run {
            tvTitle.text = cityOption.name
            Picasso
                .get()
                .load(cityOption.file[0].location)
                .resize(100,100)
                .into(cateImage)

            /*cateImage.setOnClickListener{
                val intent = Intent(root.context, ShopActivity::class.java)
                intent.putExtra("id", cityOption.id)
                intent.putExtra("name", cityOption.name)
                intent.putExtra("type", "Brand")
                root.context.startActivity(intent)
            }*/

            root.setOnClickListener {
                if(AppUtils(root.context).defaultAddress!=null){
                    val intent = Intent(root.context, ProductActivity::class.java)
                    intent.putExtra("productId", cityOption.id)
                    root.context.startActivity(intent)
                }else{
                    EventBus.getDefault().post(ShowError())
                }
            }
        }
    }

    override fun isTheSame(newItem: AdapterItem<*>): Boolean {
        return newItem is SingleMostOrderedItem
    }

    override fun isContentsTheSame(newItem: AdapterItem<*>): Boolean {
        return isTheSame(newItem) && (newItem as SingleMostOrderedItem).cityOption == cityOption
    }

}
class SingleMostOrderItemViewHolder(view:View):BaseViewHolder(view){
    val itemBinding:SingleHomeTopBrandItemBinding by BindListItem(view)
}