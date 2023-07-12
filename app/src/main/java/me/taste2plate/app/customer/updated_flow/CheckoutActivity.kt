package me.taste2plate.app.customer.updated_flow

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clevertap.android.sdk.CleverTapAPI
import com.clickzin.tracking.ClickzinTracker
import com.facebook.appevents.AppEventsConstants
import com.facebook.appevents.AppEventsConstants.EVENT_PARAM_CURRENCY
import com.facebook.appevents.AppEventsLogger
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.gson.Gson
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.trackier.sdk.TrackierEvent
import com.trackier.sdk.TrackierSDK
import kotlinx.android.synthetic.main.checkout_flow_layout.*
import me.taste2plate.app.customer.*
import me.taste2plate.app.customer.adapter.CartAdapter
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.common.setupDialog
import me.taste2plate.app.customer.databinding.CheckoutFlowLayoutBinding
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.ui.address.SaveAddressListener
import me.taste2plate.app.customer.ui.address.checkout.CheckoutAddressSelectionFragment
import me.taste2plate.app.customer.ui.home.HomeActivity
import me.taste2plate.app.customer.ui.order.OrderConfirmationActivity
import me.taste2plate.app.customer.ui.rewards.RewardViewModel
import me.taste2plate.app.customer.ui.wallet.WalletViewModel
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.utils.Utils
import me.taste2plate.app.customer.viewmodels.CustomerViewModel
import me.taste2plate.app.customer.viewmodels.ProductViewModel
import me.taste2plate.app.data.api.AnalyticsAPI
import me.taste2plate.app.data.api.LogRequest
import me.taste2plate.app.models.address.checkout.CouponResponse
import me.taste2plate.app.models.cart.CartItem
import me.taste2plate.app.models.cart.CartItemResponse
import me.taste2plate.app.models.cart.deliveryDate
import me.taste2plate.app.models.checkout.CheckoutResponse
import me.taste2plate.app.models.cutoff.CutOffResponse
import me.taste2plate.app.models.membership.myplan.PointSettings
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class CheckoutActivity : WooDroidActivity<CheckoutViewModel>(), SaveAddressListener,
    PaymentResultListener {

    private var productPrice: Float =
        0.0f // store final product price including gst/delivery charges
    private var priceProduct: Float =
        0.0f // store final product price including gst/delivery charges
    var cartItems: ArrayList<CartItem> = ArrayList()
    override lateinit var viewModel: CheckoutViewModel
    lateinit var customViewModel: ProductViewModel
    lateinit var settingsViewModel: RewardViewModel
    lateinit var walletViewModel: WalletViewModel
    lateinit var adapter: CartAdapter
    var checkAvailability: CutOffResponse? = null
    lateinit var binding: CheckoutFlowLayoutBinding
    var paymentMode = -1
    var deliveryMode = -1
    var shippingCost = 0f
    var checkoutResponse: CheckoutResponse? = null
    var couponResponse: CouponResponse? = null
    var cartItemResponse: CartItemResponse? = null

    var userId: String? = null

    var finalProductPrice = ""

    // deliverMode = 1 Express
    // deliverMode = anything else is  Normal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //send event info
        val analytics = AnalyticsAPI()
        val logRequest = LogRequest(
            type = "page visit",
            event = "visit to checkout page",
            event_data = "Item in cart while checkout : ${cartItems.size}",
            page_name = "/CheckoutActivity",
            source = "android",
            user_id = AppUtils(this).user.id,
        )
        analytics.addLog(logRequest)


        CleverTapAPI.getDefaultInstance(this)?.recordScreen("Checkout")
        binding = CheckoutFlowLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel(CheckoutViewModel::class.java)
        settingsViewModel = getViewModel(RewardViewModel::class.java)
        walletViewModel = getViewModel(WalletViewModel::class.java)
        customViewModel = getViewModel(ProductViewModel::class.java)

        userId = AppUtils(this).user.id

        binding.run {
            toolbar.setNavigationOnClickListener {
                finish()
            }
            val layoutManager = LinearLayoutManager(
                baseContext,
                RecyclerView.VERTICAL,
                false
            )
            products.layoutManager = layoutManager
            products.isNestedScrollingEnabled = true

            cartItems = ArrayList()

            adapter = CartAdapter(cartItems)
            products.adapter = adapter
            toolbar.title = "Order Confirmation"

            newAddress.setOnClickListener {
                val addressSelection = CheckoutAddressSelectionFragment()
                addressSelection.saveSetSaveListener(this@CheckoutActivity)
                addressSelection.show(supportFragmentManager, addressSelection.tag)
            }

            date.setOnClickListener {
                if (deliveryMode != 1)
                    clickDataPicker(it)
            }

            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.express -> {
                        if (deliveryMode != 1) {
                            showDialogExpress()
                        }
                        binding.cod.isChecked = false
                        binding.cod.isEnabled = false
                    }
                    R.id.standard -> {
                        deliveryMode = 2
                        binding.run {
                            date.text = "Date"
                            time.text = "Time"
                            when {
                                couponResponse != null -> {
                                    shippingCost =
                                        couponResponse!!.shipping.normal_shipping.toFloat()
                                    dFee.text =
                                        "Rs. ${couponResponse!!.shipping.normal_shipping.toFloat()}"

                                    productPrice = if (chooseWallet.isChecked) {
                                        couponResponse!!.new_final_price.withWallet!!.normal.toFloat()
                                    } else {
                                        couponResponse!!.new_final_price.normal.toFloat()
                                    }

                                    finalFee.text =
                                        if (chooseWallet.isChecked) "Rs. ${couponResponse!!.new_final_price.withWallet!!.normal}" else "Rs. ${couponResponse!!.new_final_price.normal}"
                                    igstValue.text =
                                        if (chooseWallet.isChecked) "Rs. ${couponResponse!!.gstWithWallet.normal.totalIgst}" else "Rs. ${couponResponse!!.gst.normal.totalIgst}"
                                    cgstValue.text =
                                        if (chooseWallet.isChecked) "Rs. ${couponResponse!!.gstWithWallet.normal.totalCgst}" else "Rs. ${couponResponse!!.gst.normal.totalCgst}"
                                    sgstValue.text =
                                        if (chooseWallet.isChecked) "Rs. ${couponResponse!!.gstWithWallet.normal.totalSgst}" else "Rs. ${couponResponse!!.gst.normal.totalSgst}"
                                    packingPrice.text =
                                        "Rs. ${couponResponse!!.total_packing_price}"
                                }
                                cartItemResponse != null -> {
                                    shippingCost =
                                        cartItemResponse!!.shipping.normal_shipping.toFloat()

                                    productPrice = if (chooseWallet.isChecked) {
                                        cartItemResponse!!.new_final_price.withWallet!!.normal.toFloat()
                                    } else {
                                        cartItemResponse!!.new_final_price.normal.toFloat()
                                    }

                                    finalFee.text =
                                        if (chooseWallet.isChecked) "Rs. ${cartItemResponse!!.new_final_price.withWallet!!.normal}" else "Rs. ${cartItemResponse!!.new_final_price.normal}"
                                    igstValue.text =
                                        if (chooseWallet.isChecked) "Rs. ${cartItemResponse!!.gstWithPoint.normal.totalIgst}" else "Rs. ${cartItemResponse!!.gst.normal.totalIgst}"
                                    cgstValue.text =
                                        if (chooseWallet.isChecked) "Rs. ${cartItemResponse!!.gstWithPoint.normal.totalCgst}" else "Rs. ${cartItemResponse!!.gst.normal.totalCgst}"
                                    sgstValue.text =
                                        if (chooseWallet.isChecked) "Rs. ${cartItemResponse!!.gstWithPoint.normal.totalSgst}" else "Rs. ${cartItemResponse!!.gst.normal.totalSgst}"
                                    packingPrice.text =
                                        "Rs. ${cartItemResponse!!.total_packing_price}"
                                    dFee.text = "Rs. ${cartItemResponse!!.shipping.normal_shipping}"
                                    dFee.setTextColor(
                                        ContextCompat.getColor(
                                            this@CheckoutActivity,
                                            R.color.black
                                        )
                                    )
                                }
                                else -> {
                                    finish()
                                    Toast.makeText(
                                        this@CheckoutActivity,
                                        "Retry later!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                            deliveryModeData.text = checkAvailability!!.result.remarks
                            deliveryModeData.visibility = View.VISIBLE
                        }
                    }
                    else -> {}
                }
            }

            paymentType.setOnCheckedChangeListener { _, checkedId ->
                val priceToCheck = if (deliveryMode == 1) {
                    cartItemResponse!!.new_final_price.express.toFloat()
                } else {
                    cartItemResponse!!.new_final_price.normal.toFloat()
                }
                val minPrice =
                    AppUtils(this@CheckoutActivity).appData.result.minimumOrderValue.toFloat()
                val maxPrice =
                    AppUtils(this@CheckoutActivity).appData.result.maximumOrderValueCod.toFloat()

                when (checkedId) {
                    R.id.online -> {
                        if (priceToCheck < minPrice) {
                            val message =
                                "Online payment order can be placed for minimum amount of Rs. $minPrice"
                            showOrderAmountDialog(message)
                        } else {
                            paymentMode = 1
                        }
                    }
                    R.id.cod -> {
                        if (priceToCheck !in minPrice..maxPrice) {
                            val message =
                                "COD order can be placed for amount between Rs. $minPrice - Rs. $maxPrice"
                            showOrderAmountDialog(message)
                        } else {
                            showDigitalCODDialog()
                        }
                    }
                }
            }

            binding.chooseWallet.setOnCheckedChangeListener { _, isChecked ->
                if (checkWalletDiscountValidation()) {
                    if (isChecked) {
                        if (couponResponse != null) {

                            Log.e(
                                "TAG",
                                "if coupon response is not null: ${Gson().toJson(couponResponse)}"
                            )

                            binding.run {
                                finalFee.text =
                                    if (deliveryMode == 1) "Rs. ${couponResponse!!.new_final_price.withWallet!!.express}"
                                    else "Rs. ${couponResponse!!.new_final_price.withWallet!!.normal}"
                                igstValue.text =
                                    if (deliveryMode == 1) "Rs. ${couponResponse!!.gst.express.totalIgst}" else "Rs. ${couponResponse!!.gst.normal.totalIgst}"
                                cgstValue.text =
                                    if (deliveryMode == 1) "Rs. ${couponResponse!!.gst.express.totalCgst}" else "Rs. ${couponResponse!!.gst.normal.totalCgst}"
                                sgstValue.text =
                                    if (deliveryMode == 1) "Rs. ${couponResponse!!.gst.express.totalSgst}" else "Rs. ${couponResponse!!.gst.normal.totalSgst}"
                            }
                        } else {
                            binding.run {
                                finalFee.text =
                                    if (deliveryMode == 1) "Rs. ${cartItemResponse!!.new_final_price.withWallet!!.express}" else "Rs. ${cartItemResponse!!.new_final_price.withWallet!!.normal}"
                                igstValue.text =
                                    if (deliveryMode == 1) "Rs. ${cartItemResponse!!.gstWithPoint.express.totalIgst}" else "Rs. ${cartItemResponse!!.gstWithPoint.normal.totalIgst}"
                                cgstValue.text =
                                    if (deliveryMode == 1) "Rs. ${cartItemResponse!!.gstWithPoint.express.totalCgst}" else "Rs. ${cartItemResponse!!.gstWithPoint.normal.totalCgst}"
                                sgstValue.text =
                                    if (deliveryMode == 1) "Rs. ${cartItemResponse!!.gstWithPoint.express.totalSgst}" else "Rs. ${cartItemResponse!!.gstWithPoint.normal.totalSgst}"
                            }
                        }
                    } else {
                        if (couponResponse != null) {
                            binding.run {
                                finalFee.text =
                                    if (deliveryMode == 1) "Rs. ${couponResponse!!.new_final_price.express}" else "Rs. ${couponResponse!!.new_final_price.normal}"
                                igstValue.text =
                                    if (deliveryMode == 1) "Rs. ${couponResponse!!.gst.express.totalIgst}" else "Rs. ${couponResponse!!.gst.normal.totalIgst}"
                                cgstValue.text =
                                    if (deliveryMode == 1) "Rs. ${couponResponse!!.gst.express.totalCgst}" else "Rs. ${couponResponse!!.gst.normal.totalCgst}"
                                sgstValue.text =
                                    if (deliveryMode == 1) "Rs. ${couponResponse!!.gst.express.totalSgst}" else "Rs. ${couponResponse!!.gst.normal.totalSgst}"
                            }
                        } else {
                            binding.run {
                                finalFee.text =
                                    if (deliveryMode == 1) "Rs. ${cartItemResponse!!.new_final_price.express}" else "Rs. ${cartItemResponse!!.new_final_price.normal}"
                                igstValue.text =
                                    if (deliveryMode == 1) "Rs. ${cartItemResponse!!.gst.express.totalIgst}" else "Rs. ${cartItemResponse!!.gst.normal.totalIgst}"
                                cgstValue.text =
                                    if (deliveryMode == 1) "Rs. ${cartItemResponse!!.gst.express.totalCgst}" else "Rs. ${cartItemResponse!!.gst.normal.totalCgst}"
                                sgstValue.text =
                                    if (deliveryMode == 1) "Rs. ${cartItemResponse!!.gst.express.totalSgst}" else "Rs. ${cartItemResponse!!.gst.normal.totalSgst}"
                            }
                        }
                    }
                } else {
                    binding.chooseWallet.isChecked = false
                    walletDiscountInfo()
                }
            }

        }

        // We have to disable COD button if selected address is blocked COD service
        //checkCODAvailability()
    }

    private fun disableCOD(enabled: Boolean) {
        binding.cod.isEnabled = enabled
    }

    private fun checkCODAvailability() {
        val pincode = AppUtils(this).defaultAddress.pincode
        val vendorId = cartItems[0].vendor._id

        customViewModel.checkAvailability(
            pincode.toInt(), vendorId
        ).observe(this) {
            val response = it
            when (response!!.status()) {
                Status.LOADING -> {
                }

                Status.SUCCESS -> {
                    stopShowingLoading()
                    disableCOD(response.data().cod)
                }

                Status.ERROR -> {
                    stopShowingLoading()
                    MaterialAlertDialogBuilder(this)
                        .setTitle("Uh oh!")
                        .setCancelable(false)
                        .setMessage("Something isn't right! Retry")
                        .setPositiveButton("Exit") { dialog, _ ->
                            dialog.dismiss()
                            finish()
                        }
                        .show()
                }

                Status.EMPTY -> {
                    stopShowingLoading()
                    finish()
                    Toast.makeText(this, "Something isn't right", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showOrderAmountDialog(message: String) {
        paymentType.clearCheck()
        MaterialAlertDialogBuilder(this@CheckoutActivity)
            .setTitle("Alert!")
            .setCancelable(false)
            .setMessage(message)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun checkWalletDiscountValidation(): Boolean {
        Log.e("product final price", "check price at wallet discount : $productPrice")
        return !(priceProduct < 990 || priceProduct > 15000)
    }

    private fun walletDiscountInfo() {
        var minOrder: String
        var maxOrder: String
        var walletPoint: String

        userId?.let {
            walletViewModel.getMyPlan(it).observe(this) {
                when (it.status()) {
                    Status.LOADING -> {
                        showLoading()
                    }
                    Status.SUCCESS -> {

                        //Log.e("TAG", "walletDiscountInfo: ${it.data().result.pointSetting}")

                        stopShowingLoading()
                        val pointSettings: PointSettings = it.data().pointSettings
                        minOrder = pointSettings.pointRedeemMinimumOrderValue
                        maxOrder = pointSettings.pointRedeemMaximumOrderValue
                        walletPoint = pointSettings.maxRedeemPointPerOrder

                        // show dialog about validation
                        MaterialAlertDialogBuilder(this@CheckoutActivity)
                            .setTitle("Alert!")
                            .setCancelable(false)
                            .setMessage(
                                "Minimum order value is ₹${minOrder} to redeem wallet points\n\n" +
                                        "Maximum $walletPoint points to be redeemed in each order"
                            )
                            .setPositiveButton("Ok") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()

                        Log.e(
                            "TAG",
                            "checkWalletDiscountValidation: $walletPoint - $minOrder - $maxOrder"
                        )
                    }

                    Status.ERROR, Status.EMPTY -> {
                        Toast.makeText(
                            baseContext,
                            it.error().message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }


    }

    override fun onResume() {
        super.onResume()
        if (deliveryMode == -1) {
            cart()
            //setupAddress()
            getOffers()
        }
        //confirmCheckout("COD","T2P-2021-246258111111", "")
    }

    private fun getOffers() {
        val address = AppUtils(this).defaultAddress
        viewModel.fetchOffers(address.city!!._id).observe(this, Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {
                    //showLoading()
                }

                Status.SUCCESS -> {

                    if (response.data().coupon.isNotEmpty()) {
                        binding.applyCoupon.setupDialog(
                            "Select Coupon",
                            response.data().coupon.toTypedArray(), { "${it.coupon}\n${it.desc}" },
                            {
                                binding.couponInfo.visibility = View.VISIBLE
                                binding.applyCoupon.visibility = View.GONE
                                binding.couponCode.text = it.coupon
                                applyCouponCode(it.coupon)
                                binding.clear.setOnClickListener {
                                    couponResponse = null
                                    binding.discountPrice.text = ""
                                    binding.discountPrice.visibility = View.GONE
                                    binding.couponInfo.visibility = View.GONE
                                    binding.applyCoupon.visibility = View.VISIBLE
                                    binding.dContainer.visibility = View.GONE
                                    cart()
                                }
                            }, {
                                hideSoftKeyboard()
                            }, {
                                binding.couponInfo.visibility = View.VISIBLE
                                binding.applyCoupon.visibility = View.GONE
                                binding.couponCode.text = it
                                applyCouponCode(it)
                                binding.clear.setOnClickListener {
                                    couponResponse = null
                                    binding.discountPrice.text = ""
                                    binding.discountPrice.visibility = View.GONE
                                    binding.couponInfo.visibility = View.GONE
                                    binding.applyCoupon.visibility = View.VISIBLE
                                    binding.dContainer.visibility = View.GONE
                                    cart()
                                }
                            }
                        )
                    } else {
                        showError("No Coupons Available!")
                    }
                }

                Status.ERROR -> {
                    //stopShowingLoading()
                }

                Status.EMPTY -> {
                    //stopShowingLoading()
                }
            }
        })
    }

    private fun setupAddress() {
        val address = AppUtils(this).defaultAddress

        binding.run {
            userName.text = address.contact_name
            addressSummary.text =
                "${address.address} ${address.address2} ${address.city!!.name} ${address.pincode}"
            phoneNumber.text = address.contact_mobile
            addressType.text = if (address.title != null) address.title else "Other"
            executePendingBindings()
        }

        binding.checkout.setOnClickListener {

            if (paymentMode != -1 && deliveryMode != -1) {
                if (date.text.toString()
                        .equals("Date", ignoreCase = true) || time.text.toString()
                        .equals("Time", ignoreCase = true)
                ) {
                    showError("Select slot and date")
                } else {
                    val priceToCheck = if (deliveryMode == 1) {
                        cartItemResponse!!.new_final_price.express.toFloat()
                    } else {
                        cartItemResponse!!.new_final_price.normal.toFloat()
                    }
                    val minPrice =
                        AppUtils(this@CheckoutActivity).appData.result.minimumOrderValue.toFloat()
                    if (priceToCheck < minPrice) {
                        paymentType.clearCheck()
                        MaterialAlertDialogBuilder(this@CheckoutActivity)
                            .setTitle("Alert!")
                            .setCancelable(false)
                            .setMessage("Order can be placed for amount bigger than Rs. $minPrice")
                            .setPositiveButton("Ok") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                    } else {
                        initCheckout()
                    }
                }
            } else {
                showError("Select Delivery Date, Dalivery Time & Payment Mode!")
            }
        }

        checkCODAvailability()
    }

    private fun initCheckout() {
        val isWalletApplied = binding.chooseWallet.isChecked
        val userId = AppUtils(this).user.id
        val addressId = AppUtils(this).defaultAddress._id
        val timeSlot = binding.time.text.toString()
        val date = binding.date.text.toString()
        val deliveryCost = shippingCost.toString()
        val express = if (deliveryMode == 1) "Y" else "N"
        val couponCode =
            if (binding.couponInfo.isVisible) binding.couponCode.text.toString() else ""
        val couponType = if (couponResponse != null) couponResponse!!.coupon_type else ""
        val couponAmount =
            if (couponResponse != null) couponResponse!!.coupon_discount.toString() else ""
        val cartPrice = cartItemResponse!!.cartprice.toString()
        val finalPrice =
            if (deliveryMode == 1) {
                if (couponResponse != null) {
                    if (isWalletApplied) couponResponse!!.new_final_price.withWallet!!.express else couponResponse!!.new_final_price.express
                } else {
                    if (isWalletApplied) cartItemResponse!!.new_final_price.withWallet!!.express else cartItemResponse!!.new_final_price.express
                }
            } else {
                if (couponResponse != null && couponResponse?.new_final_price != null) {
                    if (isWalletApplied) couponResponse!!.new_final_price.withWallet!!.normal else couponResponse!!.new_final_price.normal
                } else {
                    if (isWalletApplied) cartItemResponse!!.new_final_price.withWallet!!.normal else cartItemResponse!!.new_final_price.normal
                }
            }

        finalProductPrice = finalPrice

        val customerCity = AppUtils(this).defaultAddress.city!!._id
        val customerZip = AppUtils(this).defaultAddress.pincode ?: ""
        val addCost = 0f.toString()


        viewModel.initCheckout(
            isWalletApplied,
            userId,
            addressId,
            timeSlot,
            date,
            deliveryCost,
            express,
            couponCode,
            couponType,
            couponAmount,
            cartPrice,
            finalPrice,
            customerCity,
            addCost,
            customerZip,
            "Android"
        )
            .observe(this, Observer { response ->
                when (response!!.status()) {
                    Status.LOADING -> {
                        showLoading()
                    }
                    Status.SUCCESS -> {
                        stopShowingLoading()
                        if (response.data().status.contentEquals("success")) {
                            Toast.makeText(
                                this@CheckoutActivity,
                                "Order Created in Pending Status!",
                                Toast.LENGTH_LONG
                            ).show()
                            checkoutResponse = response.data()
                            if (paymentMode == 1) {
                                startPayment(
                                    checkoutResponse!!.orderId, finalPrice.toDouble()
                                )
                            } else {
                                confirmCheckout(
                                    binding.chooseWallet.isChecked,
                                    "COD",
                                    checkoutResponse!!.orderId,
                                    ""
                                )
                            }
                        } else {
                            MaterialAlertDialogBuilder(this@CheckoutActivity)
                                .setTitle("Alert!")
                                .setCancelable(false)
                                .setMessage(response.data().message)
                                .setPositiveButton("Ok") { dialog, _ ->
                                    dialog.dismiss()
                                }
                                .show()
                        }
                    }

                    Status.ERROR -> {
                        Toast.makeText(
                            this@CheckoutActivity,
                            "Something went wrong!",
                            Toast.LENGTH_LONG
                        ).show()
                        stopShowingLoading()
                    }

                    Status.EMPTY -> {
                        stopShowingLoading()
                    }
                }
            })
    }

    fun clickDataPicker(view: View) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,
            { view, selectedYear, monthOfYear, dayOfMonth ->
                // Display Selected date in Toast
                val calendar = Calendar.getInstance()
                calendar.set(selectedYear, monthOfYear, dayOfMonth)
                val formatter = SimpleDateFormat("yyyy-MM-dd")
                binding.date.text = "${formatter.format(calendar.time)}"
            }, year, month, day
        )
        dpd.datePicker.minDate = cartItemResponse!!.deliveryDate().time
        dpd.show()
    }

    private fun startPayment(orderId: String, price: Double) {

        val co = Checkout()
        try {
            val options = JSONObject();
            options.put("name", "Taste2Plate")
            options.put("description", "Taste2Plate Order")
            options.put("currency", "INR")
            options.put("amount", (price * 100).toFloat())

            val preFill = JSONObject();
            val address = AppUtils(this).defaultAddress
            val user = AppUtils(this).user
            preFill.put("email", user.email)
            preFill.put("contact", address.contact_mobile)
            options.put("prefill", preFill)
            co.open(this, options);
        } catch (e: Exception) {
            Toast.makeText(this, "Error in payment: " + e.message, Toast.LENGTH_SHORT)
                .show();
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    fun cart() {
        val address = AppUtils(this).defaultAddress
        if (address != null && address.city != null && address.pincode != null) {
            viewModel.cart(AppUtils(this).user.id, address.city!!._id, address.pincode!!).observe(
                this,
                Observer { response ->
                    when (response!!.status()) {
                        Status.LOADING -> {
                            showLoading()
                        }

                        Status.SUCCESS -> {
                            cartItems.clear()
                            stopShowingLoading()
                            var tempTotalItem: Int = 0
                            for (cartItem in response.data().result) {
                                cartItems.add(cartItem)
                                tempTotalItem += cartItem.quantity
                            }
                            cartItemResponse = response.data()
                            binding.run {
                                productPrice = response.data().new_final_price.normal.toFloat()
                                priceProduct = response.data().cartprice
                                price.text = "Rs. ${response.data().cartprice}"
                                finalFee.text = "Rs. ${response.data().new_final_price.normal}"
                                igstValue.text = "Rs. ${response.data().gst.normal.totalIgst}"
                                cgstValue.text = "Rs. ${response.data().gst.normal.totalCgst}"
                                sgstValue.text = "Rs. ${response.data().gst.normal.totalSgst}"
                                packingPrice.text = "Rs. ${response.data().total_packing_price}"
                                sDiscountPrice.text = "Rs. ${response.data().plan_discount}"

                                chooseWallet.isEnabled =
                                    cartItemResponse?.new_final_price?.withWallet != null

                                if (chooseWallet.isEnabled) {
                                    wDiscount.visibility = View.VISIBLE
                                    wDiscountPrice.text = cartItemResponse!!.walletDiscount
                                    pointConversion.text =
                                        "(${cartItemResponse!!.customer_point} x ${cartItemResponse!!.one_point_value})"
                                } else {
                                    wDiscount.visibility = View.GONE
                                }

                                when (deliveryMode) {
                                    1 -> {
                                        binding.run {
                                            dFee.text =
                                                "Rs. ${response.data().shipping.express_shipping}"
                                            executePendingBindings()
                                        }
                                    }
                                    else -> {
                                        binding.run {
                                            dFee.text =
                                                "Rs. ${response.data().shipping.normal_shipping}"
                                            executePendingBindings()
                                        }
                                    }
                                }

                                binding.cod.isEnabled =
                                    cartItemResponse!!.openOrderValue < cartItemResponse!!.maxCodOrderValue
                            }
                            checkCutOfftime(
                                cartItems.first().city,
                                AppUtils(this).defaultAddress.city!!._id
                            )
                            setupAddress()
                            cartCounter += tempTotalItem
                            adapter.notifyDataSetChanged()
                        }

                        Status.ERROR -> {
                            stopShowingLoading()
                            MaterialAlertDialogBuilder(this)
                                .setTitle("Uh oh!")
                                .setCancelable(false)
                                .setMessage("Something isn't right! Retry")
                                .setPositiveButton("Exit") { dialog, _ ->
                                    dialog.dismiss()
                                    finish()
                                }
                                .show()
                        }

                        Status.EMPTY -> {
                            stopShowingLoading()
                            finish()
                            Toast.makeText(this, "Something isn't right", Toast.LENGTH_LONG).show()
                        }
                    }
                })
        } else {
            AppUtils(this).logOut()
            finishAffinity()
            startActivity(Intent(this, SplashActivity::class.java))
        }
    }

    private fun applyCouponCode(couponCode: String) {
        viewModel.applyOffers(
            couponCode,
            AppUtils(this).user.id,
            AppUtils(this).defaultAddress.city!!._id,
            AppUtils(this).defaultAddress.pincode!!
        ).observe(this, Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {
                    showLoading()
                }
                Status.SUCCESS -> {
                    stopShowingLoading()

                    //Log.e("coupons ", "coupon success: $couponResponse")

                    if (response.data().status.contentEquals("error")) {
                        Toast.makeText(this, response.data().message, Toast.LENGTH_LONG).show()
                    } else {
                        couponResponse = response.data()
                        when (deliveryMode) {
                            1 -> {
                                binding.run {
                                    productPrice = response.data().new_final_price.express.toFloat()
                                    priceProduct = response.data().cartprice
                                    price.text = "Rs. ${response.data().cartprice}"
                                    finalFee.text = "Rs. ${response.data().new_final_price.express}"
                                    igstValue.text = "Rs. ${response.data().gst.express.totalIgst}"
                                    cgstValue.text = "Rs. ${response.data().gst.express.totalCgst}"
                                    sgstValue.text = "Rs. ${response.data().gst.express.totalSgst}"
                                    packingPrice.text = "Rs. ${response.data().total_packing_price}"
                                    binding.discountPrice.text =
                                        "Rs.  ${response.data().coupon_discount}"
                                    binding.discountPrice.visibility = View.VISIBLE
                                    binding.dContainer.visibility = View.VISIBLE
                                    executePendingBindings()
                                }
                            }
                            else -> {
                                binding.run {
                                    productPrice = response.data().new_final_price.normal.toFloat()
                                    priceProduct = response.data().cartprice
                                    price.text = "Rs. ${response.data().cartprice}"
                                    finalFee.text = "Rs. ${response.data().new_final_price.normal}"
                                    igstValue.text = "Rs. ${response.data().gst.normal.totalIgst}"
                                    cgstValue.text = "Rs. ${response.data().gst.normal.totalCgst}"
                                    sgstValue.text = "Rs. ${response.data().gst.normal.totalSgst}"
                                    packingPrice.text = "Rs. ${response.data().total_packing_price}"
                                    binding.discountPrice.text =
                                        "Rs.  ${response.data().coupon_discount}"
                                    binding.discountPrice.visibility = View.VISIBLE
                                    binding.dContainer.visibility = View.VISIBLE
                                    executePendingBindings()
                                }
                            }
                        }
                    }
                }

                Status.ERROR -> {
                    stopShowingLoading()
                    Log.e("coupon", "Getting error")
                }

                Status.EMPTY -> {
                    stopShowingLoading()
                    Log.e("coupon", "Empty")
                }
            }
        })

    }

    private fun checkCutOfftime(startCity: String, endCity: String) {
        viewModel.checkCutOfftime(startCity, endCity).observe(this, Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.SUCCESS -> {
                    stopShowingLoading()
                    checkAvailability = response.data()
                    val slots = mutableListOf<String>()
                    binding.express.isEnabled = response.data().result.express

                    if (checkAvailability?.result?.timeslot != null) {
                        if (checkAvailability!!.result.timeslot.contentEquals("Both")) {
                            slots.clear()
                            slots.add("Night")
                            slots.add("Afternoon")
                            time.setupDropDown(slots.toTypedArray(), { it }, {
                                time.text = it
                            }, {
                                if (deliveryMode != 1) {
                                    it.show()
                                }
                                hideSoftKeyboard()
                            })
                        } else {
                            slots.clear()
                            slots.add(checkAvailability!!.result.timeslot)
                            time.setupDropDown(slots.toTypedArray(), { it }, {
                                time.text = it
                            }, {
                                if (deliveryMode != 1) {
                                    it.show()
                                }
                                hideSoftKeyboard()
                            })
                        }
                        binding.standard.isChecked = true
                    } else {
                        showError("No available slots!, retry later")
                        stopShowingLoading()
                        finish()
                    }
                }

                Status.ERROR -> {
                    showError(getString(R.string.not_serving_area))
                    stopShowingLoading()
                    finish()
                }

                Status.EMPTY -> {
                    stopShowingLoading()
                    showError(getString((R.string.something_went_wrong)))
                    finish()
                }
            }

        })

    }

    private fun confirmCheckout(
        isWalletApplied: Boolean,
        gateWay: String,
        orderId: String,
        transactionId: String
    ) {

        //added in application for conversion
        val couponCode =
            if (binding.couponInfo.isVisible) binding.couponCode.text.toString() else ""

        val bundle = Bundle()
        val mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        bundle.putString(FirebaseAnalytics.Param.COUPON, couponCode)
        bundle.putString(FirebaseAnalytics.Param.CURRENCY, "INR")
        bundle.putString(FirebaseAnalytics.Param.PAYMENT_TYPE, gateWay)
        //bundle.putDouble(FirebaseAnalytics.Param.VALUE, "value")
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_PAYMENT_INFO, bundle)

        try {
            viewModel.confirmOrder(isWalletApplied, gateWay, orderId, transactionId)
                .observe(this, Observer { response ->
                    when (response!!.status()) {
                        Status.LOADING -> {
                            showLoading()
                        }

                        Status.SUCCESS -> {
                            //added in application for conversion
                            val bundle = Bundle()
                            bundle.putString(FirebaseAnalytics.Param.AFFILIATION, "affiliation")
                            bundle.putString(FirebaseAnalytics.Param.COUPON, couponCode)
                            bundle.putString(FirebaseAnalytics.Param.CURRENCY, "INR")
                            // bundle.putDouble(FirebaseAnalytics.Param.SHIPPING, "shipping")
                            // bundle.putDouble(FirebaseAnalytics.Param.TAX, "tax")
                            bundle.putString(FirebaseAnalytics.Param.TRANSACTION_ID, transactionId)
                            //XCV  bundle.putDouble(FirebaseAnalytics.Param.VALUE, "value")
                            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.PURCHASE, bundle)

                            Toast.makeText(this@CheckoutActivity, "Order Placed", Toast.LENGTH_LONG)
                                .show()

                            addAppEvent()
                            finishAffinity()
                            ClickzinTracker.getInstance().startTracking("order place")
                            startTrackingWithTrackier()
                            sendProductInfoToCleverTap()

                            val intent =
                                Intent(this@CheckoutActivity, OrderConfirmationActivity::class.java)
                            intent.putExtra("orderId", response.data().result.orderid)
                            intent.putExtra("slot", response.data().result.timeslot)
                            intent.putExtra(
                                "deliverDate",
                                response.data().result.delivery_date.toDate("dd-MM-yy")
                            )
                            startActivity(intent)
                        }

                        Status.ERROR -> {
                            Toast.makeText(
                                this@CheckoutActivity,
                                "Order Failed, contact support! or retry",
                                Toast.LENGTH_LONG
                            ).show()
                            stopShowingLoading()
                        }

                        Status.EMPTY -> {
                            stopShowingLoading()
                        }
                    }
                })
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun sendProductInfoToCleverTap() {

        val payment = when (paymentMode) {
            1 -> "Online"
            else -> "COD"
        }
        val couponUsed = if (couponResponse != null) "Yes" else "No"
        val walletUsed = if (binding.chooseWallet.isChecked) "Yes" else "No"

        val charges = hashMapOf<String, Any>(
            "Total Number Of Items" to cartItems.size,
            "Total Amount" to finalProductPrice,
            "Coupon used" to couponUsed,
            "Wallet used" to walletUsed,
            "Payment mode" to payment,
            "Delivery city" to AppUtils(this).defaultAddress.city.name,
        )

        val items = ArrayList<HashMap<String, Any>>()
        for (item in cartItems) {
            items.add(
                hashMapOf<String, Any>(
                    "Item name" to item.product.name,
                    "Number of Items" to item.quantity,
                    "Amount" to item.product.price,
                    "Order city" to item.product.city.name,
                ),
            )
        }

        Log.d("clevertap", "$charges, \n$items")
        CleverTapAPI.getDefaultInstance(this)?.pushChargedEvent(charges, items)
    }

    private fun addAppEvent() {
        val logger = AppEventsLogger.newLogger(this)
        val params = Bundle()
        params.putString(EVENT_PARAM_CURRENCY, "INR")

        logger.logEvent(
            AppEventsConstants.EVENT_NAME_PURCHASED,
            finalProductPrice.toDouble(),
            params
        )
    }


    private fun startTrackingWithTrackier() {

        val event = TrackierEvent("Order Placed")
        TrackierSDK.trackEvent(event)

        //Log.e("trackier", "start login tracker event: ${TrackierSDK.isEnabled()}", )
    }

    override fun onSaved() {
        setupAddress()
    }

    override fun onPaymentSuccess(transactionId: String) {
        confirmCheckout(
            binding.chooseWallet.isChecked,
            "razorpay",
            checkoutResponse!!.orderId,
            transactionId
        )
    }

    private fun showDigitalCODDialog() {
        var dialog: Dialog? = null
        val handleButtonClick = View.OnClickListener {
            paymentMode = 2
            dialog?.dismiss()
        }
        dialog = Utils.showDialog(
            this,
            AppUtils(this).appData,
            Utils.COD_DIGITAL,
            handleButtonClick
        )
        dialog.setOnCancelListener {
            binding.paymentType.clearCheck()
            paymentMode = -1
            dialog.dismiss()
        }

        dialog?.show()
    }


    private fun showDialogExpress() {
        var dialog: Dialog? = null
        val handleButtonClick = View.OnClickListener {
            deliveryMode = 1
            binding.run {
                dFee.setTextColor(ContextCompat.getColor(this@CheckoutActivity, R.color.black))
                deliveryModeData.text = checkAvailability!!.result.express_remarks
                deliveryModeData.visibility = View.VISIBLE
                if (couponResponse != null) {
                    shippingCost = couponResponse!!.shipping.express_shipping.toFloat()
                    dFee.text =
                        "Rs. ${couponResponse!!.shipping.express_shipping.toFloat()}"

                    productPrice = if (chooseWallet.isChecked) {
                        couponResponse!!.new_final_price.withWallet!!.express.toFloat()
                    } else {
                        couponResponse!!.new_final_price.express.toFloat()
                    }

                    finalFee.text =
                        if (chooseWallet.isChecked) "Rs. ${couponResponse!!.new_final_price.withWallet!!.express}" else "Rs. ${couponResponse!!.new_final_price.express}"
                    igstValue.text =
                        if (chooseWallet.isChecked) "Rs. ${couponResponse!!.gstWithWallet.express.totalIgst}" else "Rs. ${couponResponse!!.gst.express.totalIgst}"
                    cgstValue.text =
                        if (chooseWallet.isChecked) "Rs. ${couponResponse!!.gstWithWallet.express.totalCgst}" else "Rs. ${couponResponse!!.gst.express.totalCgst}"
                    sgstValue.text =
                        if (chooseWallet.isChecked) "Rs. ${couponResponse!!.gstWithWallet.express.totalSgst}" else "Rs. ${couponResponse!!.gst.express.totalSgst}"
                } else {
                    shippingCost = cartItemResponse!!.shipping.express_shipping.toFloat()
                    dFee.text =
                        "Rs. ${cartItemResponse!!.shipping.express_shipping}"

                    productPrice =
                        if (chooseWallet.isChecked) cartItemResponse!!.new_final_price.withWallet!!.express.toFloat()
                        else cartItemResponse!!.new_final_price.express.toFloat()

                    finalFee.text =
                        if (chooseWallet.isChecked) "Rs. ${cartItemResponse!!.new_final_price.withWallet!!.express}" else "Rs. ${cartItemResponse!!.new_final_price.express}"
                    igstValue.text =
                        if (chooseWallet.isChecked) "Rs. ${cartItemResponse!!.gstWithPoint.express.totalIgst}" else "Rs. ${cartItemResponse!!.gst.express.totalIgst}"
                    cgstValue.text =
                        if (chooseWallet.isChecked) "Rs. ${cartItemResponse!!.gstWithPoint.express.totalCgst}" else "Rs. ${cartItemResponse!!.gst.express.totalCgst}"
                    sgstValue.text =
                        if (chooseWallet.isChecked) "Rs. ${cartItemResponse!!.gstWithPoint.express.totalSgst}" else "Rs. ${cartItemResponse!!.gst.express.totalSgst}"
                }

                val calendar = Calendar.getInstance()
                val formatter = SimpleDateFormat("yyyy-MM-dd")

                /*if (checkAvailability!!.result.isCutOffTimePassed()) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1)
                    binding.date.text = "${formatter.format(calendar.time)}"
                    binding.time.text = "Afternoon"
                } else {
                    binding.date.text = "${formatter.format(calendar.time)}"
                    binding.time.text = "Night"
                }*/

                val format = SimpleDateFormat("HH:mm")

                val currentTime = format.parse("${format.format(calendar.getTime())}")
                // val currentTime = format.parse("23:00")
                val cut_of_time_first =
                    format.parse("${checkAvailability!!.result.express_cut_of_time_first}")
                val cut_of_time_second =
                    format.parse("${checkAvailability!!.result.express_cut_of_time_second}")
                val nightTwelve = format.parse("24:00")

                Log.e(
                    "TAG",
                    "showDialogExpress: $currentTime - $cut_of_time_first - $cut_of_time_second"
                )

                //  Toast.makeText(this@CheckoutActivity, "$currentTime - $cut_of_time_first - $cut_of_time_second", Toast.LENGTH_LONG).show()

                if (currentTime.before(cut_of_time_first)) {

                    Log.e("TAG", "showDialogExpress: less than cut of time first")

                    binding.date.text = "${formatter.format(Date())}"
                    binding.time.text = checkAvailability!!.result.timeslot_first
                } else if (currentTime > cut_of_time_first && currentTime < cut_of_time_second) {

                    Log.e("TAG", "showDialogExpress: between cut of time first and second")

                    calendar.add(Calendar.DAY_OF_MONTH + 1, 1)
                    binding.date.text = "${formatter.format(calendar.time)}"
                    binding.time.text = checkAvailability!!.result.timeslot_second
                } else if (currentTime > cut_of_time_second && currentTime < nightTwelve) {

                    Log.e("TAG", "showDialogExpress: between cut of time second and night twelve")

                    calendar.add(Calendar.DAY_OF_MONTH + 1, 1)
                    binding.date.text = "${formatter.format(calendar.time)}"
                    binding.time.text = "Night"
                }

                /*  when{
                      currentTime < cut_of_time_first->{
                          calendar.add(Calendar.DAY_OF_MONTH, 1)
                          binding.date.text = "${formatter.format(calendar.time)}"
                          binding.time.text = checkAvailability!!.result.timeslot_first
                      }
                      currentTime > cut_of_time_first && currentTime < cut_of_time_second->{
                          calendar.add(Calendar.DAY_OF_MONTH, 1)
                          binding.date.text = "${formatter.format(calendar.time)}"
                          binding.time.text = checkAvailability!!.result.timeslot_second
                      }
                  }*/


                /* val twelve = parser.parse("12:00")
                 val ten = parser.parse("10:00")
                 val twenty = parser.parse("20:00")

                 Log.e("TAG", "showDialogExpress: $currentTime")
                 if(currentTime > twelve && currentTime < ten){
                     calendar.add(Calendar.DAY_OF_MONTH, 1)
                     binding.date.text = "${formatter.format(calendar.time)}"
                     binding.time.text = "night"
                 }
                 else if(currentTime > ten && currentTime < twenty){
                     calendar.add(Calendar.DAY_OF_MONTH+1, 1)
                     binding.date.text = "${formatter.format(calendar.time)}"
                     binding.time.text = "afternoon"
                 }
                 else{
                     calendar.add(Calendar.DAY_OF_MONTH+1, 1)
                     binding.date.text = "${formatter.format(calendar.time)}"
                     binding.time.text = "night"
                 }*/
            }
            dialog?.dismiss()
        }
        dialog = Utils.showDialog(
            this,
            AppUtils(this).appData,
            Utils.EXPRESS_DELIVERY,
            handleButtonClick
        )
        dialog.setOnCancelListener {
            binding.radioGroup.clearCheck()
            deliveryMode = -1
            binding.deliveryModeData.visibility = View.GONE
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        finishAffinity()
        startActivity(Intent(this@CheckoutActivity, HomeActivity::class.java))
    }

}