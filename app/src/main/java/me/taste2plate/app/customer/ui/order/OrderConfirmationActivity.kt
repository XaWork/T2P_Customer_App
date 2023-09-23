package me.taste2plate.app.customer.ui.order

import android.content.Intent
import android.os.Bundle
import com.appsflyer.AFInAppEventParameterName
import com.appsflyer.AFInAppEventType
import com.appsflyer.AppsFlyerLib
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.databinding.ActivityOrderConfirmedBinding
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.ui.home.HomeActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.UserViewModel
import me.taste2plate.app.data.api.AnalyticsAPI
import me.taste2plate.app.data.api.Interkt
import me.taste2plate.app.data.api.LogRequest
import me.taste2plate.app.data.api.RequestBodyEventTrack
import me.taste2plate.app.models.LogCreatedResponse

class OrderConfirmationActivity : WooDroidActivity<UserViewModel>() {

    override lateinit var viewModel: UserViewModel
    private lateinit var confirmedBinding: ActivityOrderConfirmedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        confirmedBinding = ActivityOrderConfirmedBinding.inflate(layoutInflater)
        setContentView(confirmedBinding.root)

        viewModel = getViewModel(UserViewModel::class.java)
        intent.run {
            confirmedBinding.orderId.text = getStringExtra("orderId")
            confirmedBinding.deliveryDateTime.text =
                "${getStringExtra("deliverDate")} ${getStringExtra("slot")}"
        }

        //send event info
        val analytics = AnalyticsAPI()
        val appUtils = AppUtils(this)
        val logRequest = LogRequest(
            category = appUtils.referralInfo[0],
            token = appUtils.referralInfo[1],
            type = "checkout",
            event = "visit to order confirmation page",
            order_id = "${intent.getStringExtra("orderId")}",
            page_name = "/order confirmed",
            source = "android",
            user_id = AppUtils(this).user.id,
            geo_ip = AppUtils(this).ipAddress,
            product_id = ""
        )
        addLog(logRequest)

        sendUserEventInfoToInterkt()

         //app flyer update
         val eventParameters8: MutableMap<String, Any> = HashMap()
         eventParameters8[AFInAppEventParameterName.PRICE] =intent.getStringExtra("price").toString()
         eventParameters8[AFInAppEventParameterName.ORDER_ID] =intent.getStringExtra("orderId").toString()
         AppsFlyerLib.getInstance().logEvent(
             applicationContext,
             AFInAppEventType.PURCHASE,
             eventParameters8
         )

        confirmedBinding.continueShopping.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

    private fun sendUserEventInfoToInterkt() {
        val appUtils = AppUtils(this)
        val user = appUtils.user
        val interkt = Interkt()
        val traits = mapOf(
            "orderCreatedBy" to user.fullName,
            "orderNumber" to "${intent.getStringExtra(" orderId ")}",
        )
        val eventInfo = RequestBodyEventTrack(
            userId = user.id,
            phoneNumber = user.mobile,
            countryCode = "+91",
            event = "OrderPlaced",
            traits = traits,
        )

        interkt.eventTrack(eventInfo)
    }

    private fun addLog(request: LogRequest) {
        viewModel.addLog(request).observe(this) { response ->
            when (response!!.status()) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    /*if (!AppUtils(this).isInstallFromPlayStore)
                        purchased(response.data())*/
                }

                Status.ERROR -> {}
                Status.EMPTY -> {}
            }
        }
    }


    private fun purchased(logResponse: LogCreatedResponse) {
        viewModel.purchase(
            AppUtils(this).referralInfo[1],
            logResponse.result._id,
            "fc36e1f99d0cfdee7d34",
            AppUtils(this).token,
            logResponse.result.createdAt,
            "purchase",
            intent.getStringExtra("price")
        ).observe(this) { response ->
            when (response!!.status()) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    // getAppData()
                }

                Status.ERROR -> {}
                Status.EMPTY -> {}
            }
        }
    }

}
