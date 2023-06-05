package me.taste2plate.app.customer.ui

import android.annotation.SuppressLint
import android.os.Bundle
import me.taste2plate.app.customer.R
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.content_menu.*
import me.taste2plate.app.customer.adapter.MenuAdapter
import java.util.*

class MenuActivity : BaseActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(toolbar)

        title = "Menu"

        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            baseContext,
            androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
            false
        )
        rvMenu.layoutManager = layoutManager
        rvMenu.isNestedScrollingEnabled = false

        var titles = ArrayList<String>()
        titles.add("Products")
        titles.add("Coupons")

        var adapter = MenuAdapter(titles)
        rvMenu.adapter = adapter

    }

}
