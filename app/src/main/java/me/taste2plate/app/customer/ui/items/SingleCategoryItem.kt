package me.taste2plate.app.customer.ui.items

import android.content.Intent
import android.view.View
import com.fueled.reclaim.AdapterItem
import com.fueled.reclaim.BaseViewHolder
import com.squareup.picasso.Picasso
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.BindListItem
import me.taste2plate.app.customer.databinding.SingleHomeCategoryItemBinding
import me.taste2plate.app.customer.ui.product.ShopActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.models.address.cityzip.CityOption
import me.taste2plate.app.models.newproducts.ShowError
import org.greenrobot.eventbus.EventBus

class SingleCategoryItem(private val cityOption:CityOption): AdapterItem<SingleCategoryViewHolder>() {

    override val layoutId: Int = R.layout.single_home_category_item

    override fun onCreateViewHolder(view: View): SingleCategoryViewHolder = SingleCategoryViewHolder(view)

    override fun updateItemViews(viewHolder: SingleCategoryViewHolder) {
        viewHolder.itemBinding.run {
            tvTitle.text = cityOption.name
            Picasso
                .get()
                .load(cityOption.file)
                .resize(100,100)
                .into(cateImage)

            cateImage.setOnClickListener{
                val intent = Intent(root.context, ShopActivity::class.java)
                intent.putExtra("id", cityOption._id)
                intent.putExtra("name", cityOption.name)
                intent.putExtra("type", "City")
                root.context.startActivity(intent)

                /*if(AppUtils(root.context).defaultAddress!=null){
                    val intent = Intent(root.context, ShopActivity::class.java)
                    intent.putExtra("id", cityOption._id)
                    intent.putExtra("name", cityOption.name)
                    intent.putExtra("type", "City")
                    root.context.startActivity(intent)
                }else{
                    EventBus.getDefault().post(ShowError())
                }*/
            }

            tvTitle.setOnClickListener {
                if(AppUtils(root.context).defaultAddress!=null){
                    val intent = Intent(root.context, ShopActivity::class.java)
                    intent.putExtra("id", cityOption._id)
                    intent.putExtra("name", cityOption.name)
                    intent.putExtra("type", "City")
                    root.context.startActivity(intent)
                }else{
                    EventBus.getDefault().post(ShowError())
                }
            }
        }
    }

    override fun isTheSame(newItem: AdapterItem<*>): Boolean {
        return newItem is SingleCategoryItem
    }

    override fun isContentsTheSame(newItem: AdapterItem<*>): Boolean {
        return isTheSame(newItem) && (newItem as SingleCategoryItem).cityOption == cityOption
    }

}
class SingleCategoryViewHolder(view:View):BaseViewHolder(view){
    val itemBinding:SingleHomeCategoryItemBinding by BindListItem(view)
}