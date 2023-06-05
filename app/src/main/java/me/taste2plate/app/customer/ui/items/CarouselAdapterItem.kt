package me.taste2plate.app.customer.ui.items

import android.view.View
import com.fueled.reclaim.AdapterItem
import com.fueled.reclaim.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_home.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.HoddieImagePagerAdapter
import me.taste2plate.app.customer.common.BindListItem
import me.taste2plate.app.customer.customviews.AutoScrollViewPager
import me.taste2plate.app.customer.databinding.CaraouselLayoutBinding
import me.taste2plate.app.models.home.Slider

class CarouselAdapterItem(private val sliders:ArrayList<Slider>): AdapterItem<CarouselViewHolder>() {

    override val layoutId: Int = R.layout.caraousel_layout

    override fun onCreateViewHolder(view: View): CarouselViewHolder = CarouselViewHolder(view)

    override fun isTheSame(newItem: AdapterItem<*>): Boolean {
        return newItem is CarouselAdapterItem
    }

    override fun isContentsTheSame(newItem: AdapterItem<*>): Boolean {
        return isTheSame(newItem) && (newItem as CarouselAdapterItem).sliders == sliders
    }

    override fun updateItemViews(viewHolder: CarouselViewHolder) {
        viewHolder.itemBinding.run{
            val sliderAdapter = HoddieImagePagerAdapter(root.context!!, sliders)
            homeVp.adapter = sliderAdapter
            homeVp.setInterval(4000)
            homeVp.setDirection(AutoScrollViewPager.Direction.RIGHT)
            homeVp.setBorderAnimation(true)
            homeVp.setSlideBorderMode(AutoScrollViewPager.SlideBorderMode.NONE)
            homeVp.startAutoScroll()
            indicator.setViewPager(homeVp)
        }
    }
}

class CarouselViewHolder(view: View):BaseViewHolder(view){
    val itemBinding:CaraouselLayoutBinding by BindListItem(view)
}