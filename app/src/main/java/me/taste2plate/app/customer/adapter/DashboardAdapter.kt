package me.taste2plate.app.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.viewholder.DashbaordViewHolder
import me.taste2plate.app.customer.interfaces.OnSelectListener
import me.taste2plate.app.models.Dashboard
import me.taste2plate.app.models.DataItem

class DashboardAdapter(private val options: List<Dashboard>, val listener: OnSelectListener) :
    androidx.recyclerview.widget.RecyclerView.Adapter<DashbaordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashbaordViewHolder {
        return DashbaordViewHolder(
            parent.context,
            LayoutInflater.from(parent.context).inflate(R.layout.dashbaord_item, parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: DashbaordViewHolder, position: Int) {
        holder.renderView(options[position])
    }


    override fun getItemCount(): Int {
        return if (options.isEmpty()) 0 else options.size
    }
}
