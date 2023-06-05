package me.taste2plate.app.customer.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.clevertap.android.sdk.CleverTapAPI
import kotlinx.android.synthetic.main.activity_contact_us_detail.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.activity_product.toolbar
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.utils.AppUtils

class ContactUsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us_detail)
        CleverTapAPI.getDefaultInstance(this)?.recordScreen("Contact us details")
        setSupportActionBar(toolbar)
        title = "Contact Us"

        toolbar.setNavigationOnClickListener {
            finish()
        }

        val customAppData = AppUtils(this).appData

        tvMobile.text = customAppData.result.contactPhone
        whatsapp.text = "Whatsapp: ${customAppData.result.whatsapp}"
        location.text = customAppData.result.address
        supportMail.text = customAppData.result.contactEmail

        //click listeners
        callUs.setOnClickListener {
            var mobileNumber = ""
            for(singleNumber in customAppData.result.contactPhone.trim()){
                if(singleNumber == ' ')
                    break
                else
                    mobileNumber+=singleNumber
            }

            Log.e("mobile", mobileNumber)
            dialPhone(mobileNumber)
        }

        mailUs.setOnClickListener {
            sendMail(customAppData.result.contactEmail)
        }



    }

    private fun sendMail(contactEmail: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(contactEmail))
        startActivity(Intent.createChooser(intent, "Send Via"))
    }

    private fun dialPhone(mobileNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$mobileNumber")
        startActivity(intent)
    }
}
