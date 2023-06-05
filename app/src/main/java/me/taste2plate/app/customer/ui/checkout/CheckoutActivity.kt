package me.taste2plate.app.customer.ui.checkout

import android.content.Context
import android.os.Bundle
import com.razorpay.Checkout
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.toolbar.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.viewmodels.CheckoutViewModel
import me.taste2plate.app.models.TaxShippingData


class CheckoutActivity : WooDroidActivity<CheckoutViewModel>() {

    override lateinit var viewModel: CheckoutViewModel

    private lateinit var taxShippingData: TaxShippingData
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        Checkout.preload(baseContext);
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}

