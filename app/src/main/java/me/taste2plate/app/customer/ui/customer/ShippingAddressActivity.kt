package me.taste2plate.app.customer.ui.customer

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.customer_shipping_address.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.ui.onboarding.OnBoardActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.CustomerViewModel
import me.taste2plate.app.data.api.RegistrationData
import me.taste2plate.app.models.Customer
import me.taste2plate.app.models.ShippingAddress

class ShippingAddressActivity : WooDroidActivity<CustomerViewModel>() {

    override lateinit var viewModel: CustomerViewModel
    var customer: RegistrationData? = null

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipping_address)

        viewModel = getViewModel(CustomerViewModel::class.java)
        title = "Billing Address"

        customer()

    }

    private fun customer() {
        val currentCustomer = AppUtils(this).user
        if (currentCustomer != null) {
            customer = currentCustomer;
            display()
        } else {
            startActivity(Intent(this, OnBoardActivity::class.java))
        }
        flSave.setOnClickListener { save() }

    }

    private fun display() {
        /* if (customer!!.shippingAddress != null) {
             etFirstName.setText(customer!!.shippingAddress.firstName)
             etLastName.setText(customer!!.shippingAddress.lastName)
             etCompany.setText(customer!!.shippingAddress.company)
             etStreetAddress.setText(customer!!.shippingAddress.address1)
             etStreetAddress2.setText(customer!!.shippingAddress.address2)
             etCity.setText(customer!!.shippingAddress.city)
             etState.setText(customer!!.shippingAddress.state)
             etZipcode.setText(customer!!.shippingAddress.postcode)
             etCountry.setText(customer!!.shippingAddress.country)
         } else {
             etFirstName.setText(customer!!.firstName)
             etLastName.setText(customer!!.lastName)
         }*/
    }

    private fun save() {
        if (validates()) {
            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            val company = etCompany.text.toString()
            val streetAddress = etStreetAddress.text.toString()
            val streetAddress2 = etStreetAddress2.text.toString()
            val city = etCity.text.toString()
            val state = etState.text.toString()
            val zipcode = etZipcode.text.toString()
            val country = etCountry.text.toString()

            /*   customer!!.shippingAddress = ShippingAddress()
               customer!!.billingAddress.email = customer!!.email
               customer!!.shippingAddress.firstName = firstName
               customer!!.shippingAddress.lastName = lastName
               customer!!.shippingAddress.company = company
               customer!!.shippingAddress.address1 = streetAddress
               customer!!.shippingAddress.address2 = streetAddress2

               customer!!.shippingAddress.city = city
               customer!!.shippingAddress.state = state
               customer!!.shippingAddress.postcode = zipcode
               customer!!.shippingAddress.country = country
   */
//            viewModel.update(customer!!.id, customer!!).observe(this, Observer { response ->
//                when (response!!.status()) {
//                    Status.LOADING -> {
//                        showLoading(
//                            "Uploading account details",
//                            "This will only take a short while"
//                        )
//                    }
//
//                    Status.SUCCESS -> {
//                        customer = response.data()
//                        AppUtils(baseContext).saveUser(customer!!)
//                        stopShowingLoading()
//                        setResult(Activity.RESULT_OK)
//                        finish()
//                    }
//
//                    Status.ERROR -> {
//                        stopShowingLoading()
//                        Toast.makeText(
//                            baseContext,
//                            response.error().message.toString(),
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//
//                    Status.EMPTY -> {
//
//                    }
//
//                }
//            })


        } else {
            Toast.makeText(this, "Please correct the information entered", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun validates(): Boolean {
        tilFirstName.isErrorEnabled = false
        tilLastName.isErrorEnabled = false
        tilStreetAddress.isErrorEnabled = false
        tilCity.isErrorEnabled = false
        tilZipcode.isErrorEnabled = false
        tilCountry.isErrorEnabled = false

        var validation = true

        val firstName = etFirstName.text.toString()
        val lastName = etLastName.text.toString()
        val streetAddress = etStreetAddress.text.toString()
        val city = etCity.text.toString()
        val zipcode = etZipcode.text.toString()
        val country = etCountry.text.toString()

        if (firstName.isEmpty()) {
            tilFirstName.error = "Please fill this"
            validation = false
        }

        if (lastName.isEmpty()) {
            tilLastName.error = "Please fill this"
            validation = false
        }

        if (streetAddress.isEmpty()) {
            tilStreetAddress.error = "Please fill this"
            validation = false
        }

        if (city.isEmpty()) {
            tilCity.error = "Please fill this"
            validation = false
        }

        if (zipcode.isEmpty()) {
            tilZipcode.error = "Please fill this"
            validation = false
        }

        if (country.isEmpty()) {
            tilCountry.error = "Please fill this"
            validation = false
        }

        return validation
    }

}
