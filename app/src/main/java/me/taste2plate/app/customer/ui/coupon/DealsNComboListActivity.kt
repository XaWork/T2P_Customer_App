package me.taste2plate.app.customer.ui.coupon

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clevertap.android.sdk.CleverTapAPI
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.DealsNComboAdapter
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.CustomerViewModel
import me.taste2plate.app.models.Product

class DealsNComboListActivity() : WooDroidActivity<CustomerViewModel>() {

    private lateinit var adapterAll: DealsNComboAdapter
    override lateinit var viewModel: CustomerViewModel

    private lateinit var dashBoardItemList: List<Product>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deals_combo)
        viewModel = getViewModel(CustomerViewModel::class.java)
        CleverTapAPI.getDefaultInstance(this)?.recordScreen("Deals and Combo")


        title = intent.getStringExtra("Title")

        setSupportActionBar(toolbar)
        val customAppData = AppUtils(this).appData
        toolbar.setNavigationOnClickListener {
            finish()
        }

    }




    private fun setOffersAdapter() {

        val layoutManager = LinearLayoutManager(
            baseContext,
            RecyclerView.VERTICAL,
            false
        )
        rvDashboardList.layoutManager = layoutManager
        rvDashboardList.isNestedScrollingEnabled = false


        adapterAll = DealsNComboAdapter(dashBoardItemList)
        rvDashboardList.adapter = adapterAll
    }

}