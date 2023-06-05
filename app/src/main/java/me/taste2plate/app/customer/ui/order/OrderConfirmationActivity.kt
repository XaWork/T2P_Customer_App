package me.taste2plate.app.customer.ui.order

import android.content.Intent
import android.os.Bundle
import me.taste2plate.app.customer.databinding.ActivityOrderConfirmedBinding
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.ui.home.HomeActivity
import me.taste2plate.app.customer.viewmodels.OrderViewModel

class OrderConfirmationActivity : WooDroidActivity<OrderViewModel>() {


    override lateinit var viewModel: OrderViewModel
    lateinit var confirmedBinding: ActivityOrderConfirmedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        confirmedBinding = ActivityOrderConfirmedBinding.inflate(layoutInflater)
        setContentView(confirmedBinding.root)

        intent.run {
            confirmedBinding.orderId.text = getStringExtra("orderId")
            confirmedBinding.deliveryDateTime.text = "${getStringExtra("deliverDate")} ${getStringExtra("slot")}"
        }

        confirmedBinding.continueShopping.setOnClickListener{
            finishAffinity()
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

}
