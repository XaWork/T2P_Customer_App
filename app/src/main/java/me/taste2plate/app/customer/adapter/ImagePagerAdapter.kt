package me.taste2plate.app.customer.adapter


import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.view.SimpleDraweeView
import me.taste2plate.app.customer.R
import me.taste2plate.app.models.Image


class ImagePagerAdapter(val context: Context, private val images: List<Image>) :
    androidx.viewpager.widget.PagerAdapter() {


    override fun instantiateItem(collection: ViewGroup, position: Int): View {
        val inflater = LayoutInflater.from(context)

        val layout = inflater.inflate(R.layout.single_product_image, collection, false)
        val ivImage = layout.findViewById<SimpleDraweeView>(R.id.ivImage)

        val image = images[position]
        val finalStrUrl=image.src
        ivImage.setImageURI(finalStrUrl);
        ivImage.hierarchy.actualImageScaleType = ScalingUtils.ScaleType.CENTER_CROP;
        collection.addView(layout)
        return layout
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    override fun getCount(): Int {
        return this.images.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

}