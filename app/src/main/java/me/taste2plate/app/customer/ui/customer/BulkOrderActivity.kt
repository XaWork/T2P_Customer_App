package me.taste2plate.app.customer.ui.customer

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_bulk_order.*
import kotlinx.android.synthetic.main.content_bulk_order.*
import kotlinx.android.synthetic.main.toolbar.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.hideSoftKeyboard
import me.taste2plate.app.customer.setupDropDown
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.ProductViewModel
import me.taste2plate.app.models.CityBrand
import me.taste2plate.app.models.Customer
import me.taste2plate.app.models.custom.BulkOrder
import java.util.ArrayList

class BulkOrderActivity : WooDroidActivity<ProductViewModel>() {

    override lateinit var viewModel: ProductViewModel
    var customer: Customer? = null

    var mCityBrandList: ArrayList<CityBrand.Result> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bulk_order)
        setSupportActionBar(toolbar)
        viewModel = getViewModel(ProductViewModel::class.java)
        title = getString(R.string.bulk_order)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        flPlaceBulkOrder.setOnClickListener { save() }
        getCities();
    }

    private fun getCities(){
        viewModel.city.observe(this, androidx.lifecycle.Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.SUCCESS -> {
                    stopShowingLoading()
                    val cartResponse = response.data()
                    mCityBrandList.addAll(cartResponse.result!!)
                    etBulkCity.setupDropDown(
                        mCityBrandList.toTypedArray(), {
                            it.name!!
                        },{
                            etBulkCity.setText(it.name)
                        },{
                                if(mCityBrandList.isNotEmpty()){
                                    it.show()
                                }
                            hideSoftKeyboard()
                        }
                    )
                }

                Status.ERROR, Status.EMPTY -> {
                    stopShowingLoading()
                    showError(getString(R.string.something_went_wrong))
                }
            }

        })
    }


    private fun save() {
        if (validates()) {
            val firstName = etFullName.text.toString()
            val email = etEmail.text.toString()
            val phone = etMobile.text.toString()
            val address = etAddress.text.toString()
            val city = etBulkCity.text.toString()
            val message = etMessage.text.toString()

            val bulkOrder = BulkOrder()
            bulkOrder.firstName = firstName
            bulkOrder.email = email
            bulkOrder.phone = phone
            bulkOrder.address = address
            bulkOrder.city = city
            bulkOrder.message = message

            viewModel.createBulkOrder(bulkOrder).observe(this, Observer { response ->
                when (response!!.status()) {
                    Status.LOADING -> {
                        showLoading()
                    }
                    Status.SUCCESS -> {
                        stopShowingLoading()
                        alert(response.data().message)
                    }
                    Status.ERROR, Status.EMPTY -> {
                        stopShowingLoading()
                        Toast.makeText(
                            baseContext,
                            response.error().message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })
        } else {
            Toast.makeText(this, getString(R.string.error_incorrect_data), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun validates(): Boolean {
        tilFullName.isErrorEnabled = false
        tilEmail.isErrorEnabled = false
        tilPhoneNumber.isErrorEnabled = false
        tilAddress.isErrorEnabled = false
        tilMessage.isErrorEnabled = false

        var validation = true

        val firstName = etFullName.text.toString()
        val email = etEmail.text.toString()
        val phone = etMobile.text.toString()
        val address = etAddress.text.toString()
        val city = etBulkCity.text.toString()
        val message = etMessage.text.toString()

        if (firstName.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || message.isEmpty()) {
            tilFullName.error = getString(R.string.enter_valid_details)
            validation = false
        }

        if (city.isEmpty()) {
            showError("Please select the city")
            validation = false
        }
        return validation
    }


    private fun alert(msg: String) {
        val alert = AlertDialog.Builder(this)
            .setTitle(getString(R.string.app_name))
            .setMessage(msg)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.alert_ok)) { dialog, which ->
                finish()
            }.create()
        alert.show()

        val buttonbackground = alert.getButton(DialogInterface.BUTTON_POSITIVE)
        buttonbackground.setTextColor(Color.BLACK)

    }
}
