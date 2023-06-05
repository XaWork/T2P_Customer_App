package me.taste2plate.app.customer.ui.customer

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.content_profile.*
import kotlinx.android.synthetic.main.toolbar.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.ui.onboarding.OnBoardActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.CustomerViewModel
import me.taste2plate.app.data.api.RegistrationData
import me.taste2plate.app.models.Customer

class ProfileActivity : WooDroidActivity<CustomerViewModel>() {

    override lateinit var viewModel: CustomerViewModel
    lateinit var customerData : RegistrationData

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        customerData = AppUtils(this).user
        viewModel = getViewModel(CustomerViewModel::class.java)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        title = getString(R.string.Profile)

        tvBasicDetailsEdit.setOnClickListener {
            startActivity(
                Intent(
                    baseContext,
                    BasicCustomerDetailsActivity::class.java
                ))
        }
        tvBillingAddressEdit.setOnClickListener {
            startActivity(
                Intent(
                    baseContext,
                    BillingAddressActivity::class.java
                )
            )
        }
        tvShippingAddressEdit.setOnClickListener {
            startActivity(
                Intent(
                    baseContext,
                    ShippingAddressActivity::class.java
                )
            )
        }

        llCustomerAddresses.setOnClickListener {
            startActivity(
                Intent(
                    baseContext,
                    AddressListActivity::class.java
                ).putExtra("From", "Profile")
            )
        }

    }

    override fun onResume() {
        super.onResume()
        customer()
    }


    private fun customer() {
        val user = AppUtils(this).user
        if (user != null) {
            tvBasicDetailsName.text = if(customerData.fullName.isNotEmpty()) "Name: ${customerData.fullName}" else "N/A"
            tvEmail.text = customerData.email
            tvUsername.text = "Mobile:  ${customerData.mobile}"
        } else {
            finishAffinity()
            startActivity(Intent(this, OnBoardActivity::class.java))
        }

    }
}
