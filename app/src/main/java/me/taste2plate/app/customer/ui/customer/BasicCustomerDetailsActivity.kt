package me.taste2plate.app.customer.ui.customer

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.clevertap.android.sdk.CleverTapAPI
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.customer_basic_details.*
import kotlinx.android.synthetic.main.customer_basic_details.etEmail
import kotlinx.android.synthetic.main.customer_basic_details.tilEmail
import kotlinx.android.synthetic.main.customer_basic_details.tilFirstName
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.ui.onboarding.OnBoardActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.CustomerViewModel
import me.taste2plate.app.data.api.AnalyticsAPI
import me.taste2plate.app.data.api.LogRequest
import me.taste2plate.app.data.api.RegistrationData
import me.taste2plate.app.models.Customer
import java.util.regex.Matcher
import java.util.regex.Pattern

class BasicCustomerDetailsActivity : WooDroidActivity<CustomerViewModel>() {


    override lateinit var viewModel: CustomerViewModel
    lateinit var customer: RegistrationData

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_customer_details)
        CleverTapAPI.getDefaultInstance(this)?.recordScreen("Profile")

        viewModel = getViewModel(CustomerViewModel::class.java)
        title = "Basic Details"

        //send event info
        val analytics = AnalyticsAPI()
        val logRequest = LogRequest(
            type = "page visit",
            event = "edit profile",
            event_data = "edit profile",
            page_name = "/EditProfile",
            source = "android",
            geo_ip = AppUtils(this).ipAddress,
            user_id = AppUtils(this).user.id,
            product_id = ""
        )
        analytics.addLog(logRequest)

        customer()

        saveDetails.setOnClickListener {
            save()
        }

    }


    private fun customer() {
        val currentCustomer = AppUtils(this).user
        if (currentCustomer != null) {
            customer = currentCustomer;
            display()
        }else{
            startActivity(Intent(this, OnBoardActivity::class.java))
        }

    }

    private fun display() {
        etEmail.setText(customer.email)
        etEmail.isEnabled = false
        etUsername.isEnabled = false
        if(customer.fullName.isNotEmpty()){
            etFullname.setText(customer.fullName)
        }
        etUsername.setText(customer.mobile)
    }

    private fun save() {
        if (validates()) {
            val fullName = etFullname.text.toString()
            val mobile = etUsername.text.toString()

            viewModel.update(customer.id, fullName, mobile).observe(this, Observer { response ->
                when (response!!.status()) {
                    Status.LOADING -> {
                        showLoading()
                    }

                    Status.SUCCESS -> {
                        stopShowingLoading()
                        if(response.data().status.equals("success", ignoreCase = true)) {
                            val customer = AppUtils(this).user.copy(
                                fullName = fullName
                            )
                            AppUtils(this).saveUser(customer)
                            finish()
                        }else{
                            showError("Failed to update, retry!")
                        }
                    }

                    Status.ERROR -> {
                        stopShowingLoading()
                        Toast.makeText(
                            baseContext,
                            response.error().message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    Status.EMPTY -> {

                    }

                }
            })


        } else {
            Toast.makeText(this, getString(R.string.error_incorrect_data), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun validates(): Boolean {
        tilFirstName.isErrorEnabled = false
        tilUsername.isErrorEnabled = false

        var validation = true

        val username = tilEmail.editText!!.text.toString()
        val fullName = etFullname.text.toString()

        if (fullName.isEmpty()) {
            tilFirstName.error = getString(R.string.enter_valid_details)
            validation = false
        }
        if (username.isEmpty()) {
            tilUsername.error = getString(R.string.enter_valid_details)
            validation = false
        }
        return validation
    }

}
