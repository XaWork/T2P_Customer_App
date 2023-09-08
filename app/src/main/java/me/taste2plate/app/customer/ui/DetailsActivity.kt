package me.taste2plate.app.customer.ui

import android.os.Bundle
import com.clevertap.android.sdk.CleverTapAPI
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.details_activity.*
import kotlinx.android.synthetic.main.toolbar.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.data.api.AnalyticsAPI
import me.taste2plate.app.data.api.LogRequest

class DetailsActivity:BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)
        CleverTapAPI.getDefaultInstance(this)?.recordScreen("Details")
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        //send event info
        val analytics = AnalyticsAPI()
        val appUtils = AppUtils(this)
        val logRequest = LogRequest(
            category = appUtils.referralInfo[0],
            token = appUtils.referralInfo[1],
            type = "page visit",
            event = "visit to ${intent.getStringExtra("name")} details page",
            page_name = "/CityBrand",
            source = "android",
            geo_ip = AppUtils(this).ipAddress,
            user_id = AppUtils(this).user.id,
        )
        analytics.addLog(logRequest)

        tvTitle.text = intent.getStringExtra("name")
        val type = intent.getStringExtra("type")
        when {
            type!!.contentEquals("City") -> {
                title = "City Details"
            }
            type.contentEquals("Brand") -> {
                title = "Brand Details"
            }
            type.contentEquals("Category") -> {
                title = "Category Details"
            }
        }

        tvDesc.text = intent.getStringExtra("desc")
        Picasso.get()
            .load(intent.getStringExtra("image"))
            .into(image)
    }
}