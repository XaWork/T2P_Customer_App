package me.taste2plate.app.customer.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.squareup.picasso.Picasso
import me.taste2plate.app.customer.R
import me.taste2plate.app.models.home.Slider


class HoddieImagePagerAdapter(
    val context: Context,
    private val images: List<Slider>
) :
    androidx.viewpager.widget.PagerAdapter() {


    override fun instantiateItem(collection: ViewGroup, position: Int): View {
        val inflater = LayoutInflater.from(context)

        val layout = inflater.inflate(
            R.layout.single_slider_image,
            collection,
            false
        )
        val ivImage =
            layout.findViewById<ImageView>(R.id.ivImage)

        /*// Create LayoutParams and set the desired margins
        // Create LayoutParams and set the desired margins
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(20, 30, 40, 50) // left, top, right, bottom

        ivImage.layoutParams = layoutParams*/

        //ivImage.setActualImageResource(mResources[position])

        val image = images[position]
        val finalStrUrl = image.file
        Picasso.get().load(finalStrUrl).into(ivImage)

        /* ivImage.setOnClickListener {
             val intent = Intent(context, ShopActivity::class.java)
             intent.putExtra("id", images[position].city._id)
             intent.putExtra("name", images[position].city.name)
             intent.putExtra("type", "City")
             context.startActivity(intent)

            *//* if(AppUtils(context).defaultAddress!=null) {
                val intent = Intent(context, ShopActivity::class.java)
                intent.putExtra("id", images[position].city._id)
                intent.putExtra("name", images[position].city.name)
                intent.putExtra("type", "City")
                context.startActivity(intent)
            }else{
                EventBus.getDefault().post(ShowError())
            }*//*
        }*/

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