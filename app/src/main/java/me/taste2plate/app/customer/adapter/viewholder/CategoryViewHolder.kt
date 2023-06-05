package me.taste2plate.app.customer.adapter.viewholder

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.ui.DetailsActivity
import me.taste2plate.app.customer.ui.product.ShopActivity
import me.taste2plate.app.customer.ui.product.SubCategoryActivity
import me.taste2plate.app.models.Category
import me.taste2plate.app.models.Details
import me.taste2plate.app.models.address.cityzip.CityOption

class CategoryViewHolder(val context: Context, itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    fun renderView(
        category: Category.Result,
        subCategoryList: ArrayList<Category.Result>?
    ) {
        val tvTitle = itemView.findViewById<TextView>(R.id.txtTitle)
        val cateImage = itemView.findViewById<ImageView>(R.id.cbImg)
        val viewProducts = itemView.findViewById<TextView>(R.id.btnViewProducts)
        val viewDetails = itemView.findViewById<TextView>(R.id.btnViewDetails)

        tvTitle.text = category.name!!.split(" ").first()
        val finalStrUrl =category.file
        Picasso
            .get()
            .load(finalStrUrl)
            .into(cateImage)


        cateImage.setOnClickListener {
                val intent = Intent(context, SubCategoryActivity::class.java)
                intent.putExtra("name", category.name)
                intent.putExtra("parent_id", category._id)
                context.startActivity(intent)
        }

        viewProducts.setOnClickListener {
                val intent = Intent(context, SubCategoryActivity::class.java)
                intent.putExtra("name", category.name)
                intent.putExtra("parent_id", category._id)
                context.startActivity(intent)
        }

        viewDetails.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("name", category.name)
            intent.putExtra("type", "Category")
            intent.putExtra("desc", category.description)
            intent.putExtra("image", category.file)
            context.startActivity(intent)
        }
    }
}

class CityViewHolder(val context: Context, itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    fun renderView(
        city: CityOption
    ) {
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val cateImage = itemView.findViewById<ImageView>(R.id.cateImage)

        tvTitle.text = city.name!!.split(" ").first()
        val finalStrUrl =city.file
        Picasso
            .get()
            .load(finalStrUrl)
            .resize(100,100)
            .into(cateImage)

        cateImage.setOnClickListener{
            val intent = Intent(context, ShopActivity::class.java)
            intent.putExtra("id", city._id)
            intent.putExtra("name", city.name)
            intent.putExtra("type", "City")
            context.startActivity(intent)
        }

        tvTitle.setOnClickListener {
            val intent = Intent(context, ShopActivity::class.java)
            intent.putExtra("id", city._id)
            intent.putExtra("name", city.name)
            intent.putExtra("type", "City")
            context.startActivity(intent)
        }

    }


}