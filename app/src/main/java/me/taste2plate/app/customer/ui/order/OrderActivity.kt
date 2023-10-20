package me.taste2plate.app.customer.ui.order

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fueled.reclaim.ItemsViewAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.content_order.*
import kotlinx.android.synthetic.main.toolbar.*
import me.taste2plate.app.customer.*
import me.taste2plate.app.customer.adapter.CheckoutAdapter
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.common.UpdateEntryAdapterItem
import me.taste2plate.app.customer.common.UpdateType
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.ui.location.TrackLocationActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.utils.Utils
import me.taste2plate.app.customer.viewmodels.OrderViewModel
import me.taste2plate.app.data.api.AnalyticsAPI
import me.taste2plate.app.data.api.LogRequest
import me.taste2plate.app.models.address.toSummary
import me.taste2plate.app.models.order.*
import kotlinx.android.synthetic.main.content_order.tvPackagingChargeCost as tvPackagingChargeCost1


class OrderActivity : WooDroidActivity<OrderViewModel>() {

    override lateinit var viewModel: OrderViewModel

    var cartItems: ArrayList<OrderProductItem> = ArrayList()
    lateinit var adapter: CheckoutAdapter
    private var couponDiscount: Float = 0f
    var orderId: String = ""
    var order: Order? = null
    private var totalShippingCost: Float = 0f
    private var totalAmount: Float = 0f;
    val itemAdapter = ItemsViewAdapter()
    lateinit var serverTime: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        viewModel = getViewModel(OrderViewModel::class.java)

        val layoutManager = LinearLayoutManager(
            baseContext,
            RecyclerView.VERTICAL,
            false
        )
        rvCheckOutCart.layoutManager = layoutManager
        rvCheckOutCart.isNestedScrollingEnabled = false

        cartItems = ArrayList()
        order = intent.getSerializableExtra("order") as Order
        serverTime = intent.getStringExtra("time")!!

        //send event info
        val productIdList = order!!.products.map { it.id }
        val analytics = AnalyticsAPI()
        val appUtils = AppUtils(this)
        val logRequest = LogRequest(
            category = appUtils.referralInfo[0],
            token = appUtils.referralInfo[1],
            type = "page visit",
            event = "order details",
            event_data = order!!._id,
            page_name = "/order details",
            source = "android",
            user_id = AppUtils(this).user.id,
            geo_ip = AppUtils(this).ipAddress,
        )
        analytics.addLog(logRequest)

        val serverTimeDate = serverTime.toDateObject("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val createdAt = order!!.created_date.toDateObject("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val diffHours: Long = (serverTimeDate.time - createdAt.time) / (60 * 60 * 1000)
        if (diffHours < 4 && !order!!.status.contentEquals("cancel")) {
            btnCancelOrder.visibility = VISIBLE
        } else {
            btnCancelOrder.visibility = GONE
        }

        if (order!!.status.contentEquals("cancel")) {
            tvTrackOrder.visibility = View.GONE
        } else {
            tvTrackOrder.visibility = View.VISIBLE
        }

        adapter = CheckoutAdapter(cartItems)
        rvCheckOutCart.adapter = adapter

        tvTrackOrder.setOnClickListener {
            if (order?.status?.contentEquals("delivery_boy_started")!!) {
                startActivity(
                    Intent(this, TrackLocationActivity::class.java).putExtra(
                        "order",
                        order!!
                    )
                )
            } else {
                showTrackDialog()
            }
        }

        updates.apply {
            this.layoutManager = LinearLayoutManager(this@OrderActivity)
            isNestedScrollingEnabled = false
            adapter = itemAdapter
        }

        if (order!!.delivery_weight.isEmpty() || order!!.delivery_weight.toFloat() <= 0) {
            weight_delivery_container.visibility = GONE
        } else {
            weight_delivery_container.visibility = VISIBLE
            weight_delivery.text = "${order!!.delivery_weight} Kg"
        }

        if (order!!.pickup_weight.isEmpty() || order!!.pickup_weight.toFloat() <= 0) {
            weight_pickup_container.visibility = GONE
        } else {
            weight_pickup_container.visibility = VISIBLE
            weight_pickup.text = "${order!!.pickup_weight} Kg"
        }

        btnCancelOrder.setOnClickListener {
            MaterialAlertDialogBuilder(this@OrderActivity)
                .setTitle("Cancel Order")
                .setMessage("Are you sure, you want to cancel the order")
                .setPositiveButton("Yes") { dialog, _ ->
                    cancelOrder()
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }


        title = "Order: ${order!!.orderid}"
        if (order?.status?.contentEquals("cancelled")!!) {
            btnCancelOrder.visibility = GONE
        }
        cartItems.addAll(order!!.products)
        adapter.notifyDataSetChanged()

        order?.coupon?.let {
            if (it.isNotEmpty() && order!!.couponamount.isNotEmpty() && order!!.couponamount.toFloat() != 0f) {
                llCoupon.visibility = VISIBLE
                couponDiscount = order!!.couponamount.toFloat()
                tvCouponCost.text = "Rs  -${couponDiscount}"
            } else {
                llCoupon.visibility = GONE
            }
        }

        deliveryDate.text =
            "Delivery Date: ${order!!.delivery_date.toDate("dd-MM-yyyy")} ${order!!.timeslot}"

        /*if(order!!.otp.isNotEmpty()){
            orderOtp.text = "OTP : ${order!!.otp}"
        }*/


        totalAmount = order!!.finalprice.toFloat()

        updateShippingInfo(order!!)
        updateShippingAddress()

        getUpdates()
    }


    private fun getUpdates() {
        viewModel.getOrderUpdates(order!!._id).observe(this) { response ->
            when (response.status()) {
                Status.SUCCESS -> {
                    stopShowingLoading()
                    if (!response.data().updates.isNullOrEmpty()) {

                        updateTitle.visibility = VISIBLE
                        updates.visibility = VISIBLE
                        val items = response.data().updates.mapIndexed { index, item ->
                            when (index) {
                                0 -> {
                                    UpdateEntryAdapterItem(item.toSummary(), UpdateType.ENTRY)
                                }

                                response.data().updates.size - 1 -> {
                                    UpdateEntryAdapterItem(item.toSummary(), UpdateType.EXIT)
                                }

                                else -> {
                                    UpdateEntryAdapterItem(
                                        item.toSummary(),
                                        UpdateType.INTERMEDIATE
                                    )
                                }
                            }
                        }
                        itemAdapter.replaceItems(items, true)
                    } else {
                        updateTitle.visibility = GONE
                        updates.visibility = View.GONE
                    }
                }

                Status.LOADING -> {
                    showLoading()
                }

                else -> {
                    stopShowingLoading()
                    showError("Something went wrong while getting updates!")
                    finish()
                }
            }
        }
    }


    private fun cancelOrder() {
        viewModel.cancelOrder(order!!._id).observe(this, Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.SUCCESS -> {
                    stopShowingLoading()
                    alert(response.data().message)
                }

                Status.ERROR -> {
                    stopShowingLoading()
                    toast("Something went wrong!")
                }

                Status.EMPTY -> {
                    toast("Something went wrong!")
                    // stopShowingLoading()
                }
            }

        })

    }

    private fun updatePaymentDetails() {
        val payment = order?.gateway ?: return

        rgPaymentType.visibility = VISIBLE
        if (payment.contentEquals("cod")) {
            rbEnableCOD.isChecked = true
            rbEnablePayNow.isEnabled = false
            rbEnableCOD.isEnabled = false
        } else {
            rbEnablePayNow.isChecked = true
            rbEnableCOD.isEnabled = false
            rbEnablePayNow.isEnabled = false
        }
        rgPaymentType.isEnabled = false
    }


    private fun updateShippingInfo(response: Order) {

        totalShippingCost = response.totalShippingPrice.toFloat()

        tvTotalItemCostValue.text = getString(R.string.Rs_double, response.price.toDouble())

        tvShippingCost.text = getString(R.string.Rs_double, totalShippingCost)
        tvPackagingChargeCost1.text = "Rs  ${response.totalPackingPrice!!.toFloat()}"

        if ((response.tIGST().toFloat() != 0f)) {
            llIGSTLayout.visibility = VISIBLE
            tvIGSTCost.text = "Rs ${response.tIGST()!!.toFloat()}"
        } else {
            llSGSTLayout.visibility = VISIBLE
            llCGSTLayout.visibility = VISIBLE
            tvSGSTCost.text = getString(R.string.Rs_double, response.tSGST().toFloat())
            tvCGSTCost.text = getString(R.string.Rs_double, response.totalCGST.toFloat())
        }

        tvTotal.text = response.finalprice
        updateShippingAddress()
    }

    private fun updateShippingAddress() {
        tvShippingAddress.text = order?.address?.toSummary().orEmpty().trim()
        tvShippingAddressEdit.visibility = GONE
    }

    private fun alert(message: String) {
        val alert = AlertDialog.Builder(this)
            .setTitle("")
            .setMessage("Your order has been successfully cancelled")
            .setCancelable(false)
            .setPositiveButton(getString(R.string.alert_ok)) { dialog, which ->
                finish()
            }.create()
        alert.show()
    }


    private fun showTrackDialog() {
        var dialog: Dialog? = null
        val handleButtonClick = View.OnClickListener {
            dialog?.dismiss()
        }
        dialog = Utils.showDialog(
            this,
            AppUtils(this).appData,
            Utils.COMPANION_TRACK,
            handleButtonClick
        )
        dialog.show()
    }
}
