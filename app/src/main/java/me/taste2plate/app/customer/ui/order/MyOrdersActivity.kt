package me.taste2plate.app.customer.ui.order

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.clevertap.android.sdk.CleverTapAPI
import kotlinx.android.synthetic.main.activity_my_orders.*
import kotlinx.android.synthetic.main.content_my_orders.*
import kotlinx.android.synthetic.main.state_empty.*
import kotlinx.android.synthetic.main.toolbar.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.OrderAdapter
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.ui.home.HomeActivity
import me.taste2plate.app.customer.ui.onboarding.OnBoardActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.OrderViewModel
import me.taste2plate.app.models.order.Order

class MyOrdersActivity : WooDroidActivity<OrderViewModel>() {
    override lateinit var viewModel: OrderViewModel
    var orders: ArrayList<Order> = ArrayList()

    lateinit var adapter: OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_orders)
        setSupportActionBar(toolbar)
        CleverTapAPI.getDefaultInstance(this)?.recordScreen("My Orders")
        val customAppData = AppUtils(this).appData

        toolbar.setNavigationOnClickListener {
            finish()
        }

        viewModel = getViewModel(OrderViewModel::class.java)
        title = getString(R.string.my_orders)

        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            baseContext,
            RecyclerView.VERTICAL,
            false
        )
        rvOrders.layoutManager = layoutManager
        rvOrders.isNestedScrollingEnabled = false

        orders = ArrayList()

        flSave.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        llEmptyState_layout.visibility = View.GONE

    }

    override fun onResume() {
        super.onResume()
        orders()
    }

    private fun showEmpty(title: String, description: String) {
        tvEmptyState_title.text = title
        tvEmptyState_description.text = description

        llEmptyState_layout.visibility = View.VISIBLE

        bEmptyState_action.setOnClickListener {
            finish()
        }
    }

    private fun orders() {

        val appUtils = AppUtils(this)

        if (appUtils.user == null) {
            startActivity(Intent(baseContext, OnBoardActivity::class.java))
            finish()
            return
        }
        viewModel.orders(appUtils.user.id).observe(this) { response ->
            when (response!!.status()) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.SUCCESS -> {
                    orders.clear()
                    stopShowingLoading()

                    for (order in response.data().result) {
                        orders.add(order)
                    }
                    adapter = OrderAdapter(orders, response.data().server_time)
                    rvOrders.adapter = adapter
                    adapter.notifyDataSetChanged()
                }

                Status.ERROR -> {
                    stopShowingLoading()
                }

                Status.EMPTY -> {
                    stopShowingLoading()

                    showEmpty(getString(R.string.no_orders), getString(R.string.no_order_desc))
                }
            }

        }

    }
}
