package me.taste2plate.app.customer.ui.customer

import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_dfh.*
import kotlinx.android.synthetic.main.content_bulk_order.*
import kotlinx.android.synthetic.main.content_dfh.*
import kotlinx.android.synthetic.main.content_dfh.etAddress
import kotlinx.android.synthetic.main.content_dfh.etEmail
import kotlinx.android.synthetic.main.content_dfh.etFullName
import kotlinx.android.synthetic.main.content_dfh.etMobile
import kotlinx.android.synthetic.main.content_dfh.tilAddress
import kotlinx.android.synthetic.main.content_dfh.tilEmail
import kotlinx.android.synthetic.main.content_dfh.tilFullName
import kotlinx.android.synthetic.main.content_dfh.tilPhoneNumber
import kotlinx.android.synthetic.main.toolbar.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.ProductViewModel
import me.taste2plate.app.models.Customer
import me.taste2plate.app.models.custom.DirectFromHome
import java.util.*


class DirectFromHomeActivity : WooDroidActivity<ProductViewModel>() {

    private var pageid: String? = ""
    private lateinit var picker: TimePickerDialog
    override lateinit var viewModel: ProductViewModel
    var customer: Customer? = null
    var strPickTime: String? = ""
    var strDeliverTime: String? = ""

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dfh)
        setSupportActionBar(toolbar)
        pageid = intent.getStringExtra("pageId")
        viewModel = getViewModel(ProductViewModel::class.java)
        title = getString(R.string.ma_ka_khana)

        val customAppData = AppUtils(this).appData

        toolbar.setNavigationOnClickListener {
            finish()
        }
        flPlaceDFHOrder.setOnClickListener { placeOrder() }

        tvPickTime.setOnClickListener { timePicker(true) }

        tvDeliveryTime.setOnClickListener { timePicker(false) }
    }


    private fun placeOrder() {
        if (validates()) {

            val fromFromName = etFromFullName.text.toString()
            val fromEmail = etFromEmail.text.toString()
            val fromPhone = etFromMobile.text.toString()
            val fromAddress = etFromAddress.text.toString()
            val fromCity = spFromCitySpinner.selectedItem.toString()

            val firstName = etFullName.text.toString()
            val email = etEmail.text.toString()
            val phone = etMobile.text.toString()
            val address = etAddress.text.toString()
            val city = spCitySpinner.selectedItem.toString()

            val weightInKg = etWeight.text.toString()


            val directFromHome = DirectFromHome()
            directFromHome.whomName = firstName
            directFromHome.whomEmail = email
            directFromHome.whomPhone = phone
            directFromHome.whomAddress = address
            directFromHome.whomCity = city

            directFromHome.fromName = fromFromName
            directFromHome.fromEmail = fromEmail
            directFromHome.fromPhone = fromPhone
            directFromHome.fromAddress = fromAddress
            directFromHome.fromCity = fromCity
            directFromHome.productWeight = weightInKg;

            strPickTime = strPickTime?.replace(getString(R.string.pickup_at), "")
            strDeliverTime = strDeliverTime?.replace(getString(R.string.deliver_at), "")

            directFromHome.picuptime = strPickTime.toString()
            directFromHome.deleverytime = strDeliverTime.toString()


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


        tillFromFullName.isErrorEnabled = false
        tillFromEmail.isErrorEnabled = false
        tillFromPhoneNumber.isErrorEnabled = false
        tillFromAddress.isErrorEnabled = false

        var validation = true

        val fromFromName = etFromFullName.text.toString()
        val fromEmail = etFromEmail.text.toString()
        val fromPhone = etFromMobile.text.toString()
        val fromAddress = etFromAddress.text.toString()
        val fromCity = spFromCitySpinner.selectedItemPosition

        val firstName = etFullName.text.toString()
        val email = etEmail.text.toString()
        val phone = etMobile.text.toString()
        val address = etAddress.text.toString()
        val city = spCitySpinner.selectedItemPosition

        if (firstName.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || fromFromName.isEmpty() || fromEmail.isEmpty() || fromPhone.isEmpty() || fromAddress.isEmpty()) {
            tilFullName.error = getString(R.string.enter_valid_details)
            validation = false
        }


        if (city == 0) {
            showToast(getString(R.string.please_select_city))
            validation = false
        }


        if (fromCity == 0) {
            showToast(getString(R.string.please_select_city))
            validation = false
        }

        if (strPickTime?.isEmpty()!!) {
            tillFromAddress.error = getString(R.string.select_pickup_time)
            validation = false
        }

        if (strDeliverTime?.isEmpty()!!) {
            tillFromAddress.error = getString(R.string.please_select_delivery_time)
            validation = false
        }

        return validation
    }


    private fun alert(message: String) {
        val alert = AlertDialog.Builder(this)
            .setTitle(getString(R.string.app_name))
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.alert_ok)) { dialog, which ->
                finish()
            }.create()
        alert.show()

        val buttonbackground = alert.getButton(DialogInterface.BUTTON_POSITIVE)
        buttonbackground.setTextColor(Color.BLACK)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun timePicker(isPickTime: Boolean) {
        val cldr = Calendar.getInstance()
        val hour = cldr.get(Calendar.HOUR_OF_DAY)
        val minutes = cldr.get(Calendar.MINUTE)
        // time picker dialog
        picker = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { tp, sHour, sMinute ->
                updateText(isPickTime, sHour, sMinute)
            },
            hour,
            minutes,
            true
        )
        picker.show()
    }

    private fun updateText(pickTime: Boolean, sHour: Int, sMinute: Int) {
        if (pickTime) {
            strPickTime = "$sHour:$sMinute"
            tvPickTime.text = getString(R.string.pickup_at) + " " + strPickTime
        } else {
            strDeliverTime = "$sHour:$sMinute"
            tvDeliveryTime.text = getString(R.string.deliver_at) + " " + strDeliverTime
        }
    }
}
