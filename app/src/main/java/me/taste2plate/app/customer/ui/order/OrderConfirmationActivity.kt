package me.taste2plate.app.customer.ui.order

import android.content.Intent
import android.os.Bundle
import me.taste2plate.app.customer.databinding.ActivityOrderConfirmedBinding
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.ui.home.HomeActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.OrderViewModel
import me.taste2plate.app.data.api.AnalyticsAPI
import me.taste2plate.app.data.api.LogRequest

class OrderConfirmationActivity : WooDroidActivity<OrderViewModel>() {


    override lateinit var viewModel: OrderViewModel
    lateinit var confirmedBinding: ActivityOrderConfirmedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        confirmedBinding = ActivityOrderConfirmedBinding.inflate(layoutInflater)
        setContentView(confirmedBinding.root)

        intent.run {
            confirmedBinding.orderId.text = getStringExtra("orderId")
            confirmedBinding.deliveryDateTime.text =
                "${getStringExtra("deliverDate")} ${getStringExtra("slot")}"
        }

        //send event info
        val analytics = AnalyticsAPI()
        val logRequest = LogRequest(
            type = "order",
            event = "visit to order confirmation page",
            event_data = "order id : ${intent.getStringExtra("orderId")}",
            page_name = "/order confirmed",
            source = "android",
            user_id = AppUtils(this).user.id,
            product_id = ""
        )
        analytics.addLog(logRequest)

        confirmedBinding.continueShopping.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

}
