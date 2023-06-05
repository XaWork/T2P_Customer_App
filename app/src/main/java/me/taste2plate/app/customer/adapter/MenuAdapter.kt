package me.taste2plate.app.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.viewholder.MenuViewHolder

class MenuAdapter(private val titles: List<String>) : androidx.recyclerview.widget.RecyclerView.Adapter<MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(
            parent.context,
            LayoutInflater.from(parent.context).inflate(R.layout.single_menu_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.renderView(titles[position])
    }


    override fun getItemCount(): Int {
        return if (titles.isEmpty()) 0 else titles.size
    }
}
