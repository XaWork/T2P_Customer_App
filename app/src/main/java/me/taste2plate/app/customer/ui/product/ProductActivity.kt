package me.taste2plate.app.customer.ui.product

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.clevertap.android.sdk.CleverTapAPI
import com.facebook.appevents.AppEventsConstants
import com.facebook.appevents.AppEventsLogger
import com.google.android.material.button.MaterialButton
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.content_product.*
import kotlinx.android.synthetic.main.menu_wishlist_icon.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.SplashActivity
import me.taste2plate.app.customer.adapter.ImagePagerAdapter
import me.taste2plate.app.customer.common.BaseActivity
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.ui.address.AddressSelectionFragment
import me.taste2plate.app.customer.ui.address.SaveAddressListener
import me.taste2plate.app.customer.ui.state.ProgressDialogFragment
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.ProductViewModel
import me.taste2plate.app.data.api.AnalyticsAPI
import me.taste2plate.app.data.api.Interkt
import me.taste2plate.app.data.api.LogRequest
import me.taste2plate.app.data.api.RequestBodyEventTrack
import me.taste2plate.app.data.api.RequestBodyUserTrack
import me.taste2plate.app.models.AppDataResponse
import me.taste2plate.app.models.Image
import me.taste2plate.app.models.address.Address
import me.taste2plate.app.models.cart.CartItem
import me.taste2plate.app.models.newproducts.NewProduct


class ProductActivity : BaseActivity(), SaveAddressListener {

    var cal_dist = 0
    var d: Int = 0
    var a: String = ""
    var productInCart = false
    private var currentCartItem: CartItem? = null
    var defaultAddress: Address? = null
    lateinit var viewModel: ProductViewModel
    val TAG = this::getLocalClassName.toString()

    lateinit var currentVendorId: String

    lateinit var appData: AppDataResponse
    var cartItems: ArrayList<CartItem> = ArrayList()
    var productId = ""
    lateinit var userId: String
    private lateinit var product: NewProduct
    private var producCityId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        CleverTapAPI.getDefaultInstance(this)?.recordScreen("Product Details")
        setSupportActionBar(toolbar)
        tvCartCounter = findViewById(R.id.tvCart_counter)
        tvWishlistCounter = findViewById(R.id.tvWishlistCounter)
        viewModel = getViewModel(ProductViewModel::class.java)
        title = getString(R.string.Product)

        productId = intent.getStringExtra("productId")!!

        //send event info
        val analytics = AnalyticsAPI()
        val appUtils = AppUtils(this)
        val logRequest = LogRequest(
            category = appUtils.referralInfo[0],
            token = appUtils.referralInfo[1],
            type = "page visit",
            event = "visit to product details page",
            page_name = "/ProductDetails",
            source = "android",
            user_id = AppUtils(this).user.id,
            geo_ip = AppUtils(this).ipAddress,
            product_id = intent.getStringExtra("productId")!!
        )
        analytics.addLog(logRequest)

        if (AppUtils(this).appData == null) {
            AppUtils(this).logOut()
            finishAffinity()
            startActivity(Intent(this, SplashActivity::class.java))
        } else {
            appData = AppUtils(this).appData
        }
        userId = AppUtils(this).user.id
        defaultAddress = AppUtils(this).defaultAddress
        if (productId.isNotEmpty()) {
            product(productId)
        }

        if (defaultAddress != null) {
            edtZipCode.setText(defaultAddress!!.pincode)
        }

        llOpenCart.setOnClickListener {
            if (defaultAddress == null) {
                val addressSelection = AddressSelectionFragment()
                addressSelection.saveSetSaveListener(this)
                addressSelection.show(supportFragmentManager, addressSelection.tag)
            } else
                startActivity(Intent(this, CartActivity::class.java))
        }

        addToWishlist.setOnClickListener {
            addToWish()
        }

        checkZip.setOnClickListener {
            if (edtZipCode.text?.isNotEmpty()!!) {
                checkProductAvailability(edtZipCode.text.toString().toInt())
            } else {
                showError(getString(R.string.input_pincode))
            }
        }
        enableAddToCart(false)
        edtZipCode.requestFocus()
    }

    private fun addToWish() {
        viewModel.addToWishlist(AppUtils(this).user.id, productId).observe(this) { response ->
            when (response.status()) {
                Status.LOADING -> showLoading()
                Status.SUCCESS -> {
                    stopShowingLoading()
                    Toast.makeText(this, response.data().message, Toast.LENGTH_SHORT).show()
                    addToWishlist.setImageResource(R.drawable.product_in_wishlist)

                    //send event info
                    val analytics = AnalyticsAPI()
                    val appUtils = AppUtils(this)
                    val logRequest = LogRequest(
                        category = appUtils.referralInfo[0],
                        token = appUtils.referralInfo[1],
                        type = "wishlist",
                        event = "add",
                        page_name = "/ProductList",
                        source = "android",
                        geo_ip = AppUtils(this).ipAddress,
                        user_id = AppUtils(this).user.id,
                        product_id = product._id
                    )
                    analytics.addLog(logRequest)
                }

                Status.ERROR -> stopShowingLoading()
                Status.EMPTY -> stopShowingLoading()
            }
        }
    }

    private fun sendProductInfoToCleverTap(eventName: String) {
        val prodViewedAction = mapOf(
            "Product Name" to product.name,
            "Category" to product.category,
            "Brand" to product.brand,
            "City" to product.city,
            "Price" to product.price,
        )
        Log.d("clevertap", "$prodViewedAction")
        CleverTapAPI.getDefaultInstance(this)?.pushEvent(eventName, prodViewedAction)
    }


    override fun onResume() {
        super.onResume()
        val address = AppUtils(this).defaultAddress
        if (address != null) {
            cart(userId, address.city._id, address.pincode)
        } else {
            showError("Set default address!")
        }
    }

    private fun getWishlist() {
        viewModel.getWishlist(AppUtils(this).user.id).observe(this) { response ->
            when (response.status()) {
                Status.LOADING -> showLoading()
                Status.SUCCESS -> {
                    stopShowingLoading()
                    val wishlistItem = response.data().result

                    if (wishlistItem.isEmpty())
                        tvWishlistCounter?.visibility = View.GONE
                    else {
                        Log.e("wishlist", wishlistItem.size.toString())
                        tvWishlistCounter?.visibility = View.VISIBLE
                        tvWishlistCounter?.text = wishlistItem.toString()
                    }
                    wishlistCounter = wishlistItem.toString()
                }

                Status.ERROR -> stopShowingLoading()
                Status.EMPTY -> stopShowingLoading()
            }
        }
    }

    private fun addToCart() {
        viewModel.addToCart(tvQty!!.text.toString().toInt(), productId, AppUtils(this).user.id)
            .observe(this, Observer { response ->
                when (response!!.status()) {
                    Status.LOADING -> {
                        showLoading()
                    }

                    Status.SUCCESS -> {
                        stopShowingLoading()

                        //added in application for conversion
                        val bundle = Bundle()
                        val mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
                        bundle.putString(FirebaseAnalytics.Param.CURRENCY, "INR")
                        bundle.putDouble(FirebaseAnalytics.Param.VALUE, 0.0)
                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, bundle)

                        addAppEvent()


                        //send event info
                        val analytics = AnalyticsAPI()
                        val appUtils = AppUtils(this)
                        val logRequest = LogRequest(
                            category = appUtils.referralInfo[0],
                            token = appUtils.referralInfo[1],
                            type = "add to cart",
                            event = "add product to cart",
                            event_data = "Item added to cart : ${tvQty!!.text.toString()}",
                            page_name = "/ProductList",
                            source = "android",
                            geo_ip = AppUtils(this).ipAddress,
                            user_id = AppUtils(this).user.id,
                            product_id = productId
                        )
                        analytics.addLog(logRequest)

                        sendUserEventInfoToInterkt()
                    }

                    Status.ERROR, Status.EMPTY -> {
                        stopShowingLoading()
                        toast(getString(R.string.error_add_product))
                    }
                }

            })
    }


    private fun sendUserEventInfoToInterkt() {
        val appUtils = AppUtils(this)
        val user = appUtils.user
        val interkt = Interkt()
        val traits = mapOf(
            "productName" to product.name,
            "quantity" to tvQty.text.toString(),
            "price" to product.selling_price,
            "currency" to "INR"
        )
        val eventInfo = RequestBodyEventTrack(
            userId = user.id,
            phoneNumber = user.mobile,
            countryCode = "+91",
            event = "Product Added To Cart",
            traits = traits,
        )

        interkt.eventTrack(eventInfo)
    }

    private fun addAppEvent() {
        val logger = AppEventsLogger.newLogger(this)
        logger.logEvent(AppEventsConstants.EVENT_NAME_ADDED_TO_CART)
    }

    private fun updateCart() {
        viewModel.updateCart(tvQty!!.text.toString().toInt(), productId, AppUtils(this).user.id)
            .observe(this, Observer { response ->
                when (response!!.status()) {
                    Status.LOADING -> {
                        showLoading()
                    }

                    Status.SUCCESS -> {
                        stopShowingLoading()
                    }

                    Status.ERROR, Status.EMPTY -> {
                        stopShowingLoading()
                        toast(getString(R.string.error_add_product))
                    }
                }

            })
    }

    private fun deleteFromCart() {
        viewModel.deleteItem(productId, userId).observe(this, Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.SUCCESS -> {
                    stopShowingLoading()
                    showError("Removed from cart")
                }

                Status.ERROR -> {
                    showError(getString(R.string.error) + response.error().message)
                }

                else -> showError(getString(R.string.something_went_wrong))
            }

        })
    }

    private fun product(productId: String) {
        viewModel.productById(productId).observe(this, Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {
                    flLoading.visibility = View.VISIBLE
                }

                Status.SUCCESS -> {
                    product = response.data().result.first()
                    setUpPage(product)
                    //getProductCity(product._id)
                    flLoading.visibility = View.GONE
                    //EventBus.getDefault().post(ProductEvent(product))
                }

                Status.ERROR -> {
                    showError(getString(R.string.error) + response.error().message)
                }

                else -> showError(getString(R.string.something_went_wrong))
            }
        })

    }

    private fun checkProductAvailability(pincode: Int) {

        viewModel.checkAvailability(
            pincode,
            if (this::currentVendorId.isInitialized) currentVendorId else product.vendor._id
        )
            .observe(this, Observer { response ->
                when (response!!.status()) {
                    Status.LOADING -> {
                        showLoading()
                    }

                    Status.SUCCESS -> {
                        stopShowingLoading()
                        edtZipCode.setText(pincode.toString())
                        edtZipCode.setSelection(pincode.toString().length)
                        val zipCodeResponse = response.data()
                        if (zipCodeResponse.status!!.contentEquals("success")) {
                            enableAddToCart(true)
                            if (zipCodeResponse.express) {
                                containerExpress.visibility = View.VISIBLE
                                cutOffTextExpress.text =
                                    zipCodeResponse.cutoff_response.express_remarks
                            }
                            containerStandard.visibility = View.VISIBLE
                            cutOffTextStandard.text = zipCodeResponse.cutoff_response.remarks
                            showCustomToast()
                        } else {
                            containerExpress.visibility = View.GONE
                            containerStandard.visibility = View.GONE
                            enableAddToCart(false)
                            showDialog(true)
                        }
                    }

                    Status.ERROR -> {
                        containerExpress.visibility = View.GONE
                        containerStandard.visibility = View.GONE
                        enableAddToCart(false)
                        showError(getString((R.string.something_went_wrong)))
                        stopShowingLoading()
                    }

                    Status.EMPTY -> {
                        containerExpress.visibility = View.GONE
                        containerStandard.visibility = View.GONE
                        stopShowingLoading()
                        showError(getString((R.string.something_went_wrong)))
                    }
                }

            })

    }

    private fun cart(userId: String, cityId: String, zipCode: String) {
        viewModel.getCart(userId, cityId, zipCode).observe(this, Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {
                    Log.e("TAG", "LOADING")
                }

                Status.SUCCESS -> {
                    cartItems.clear()
                    productInCart = false
                    var tempTotalItem: Int = 0
                    for (cartItem in response.data().result) {
                        cartItems.add(cartItem)
                        if (cartItem.product._id == productId) {
                            productInCart = true
                            currentCartItem = cartItem
                            tvQty!!.text = cartItem.quantity.toString()
                        }
                        tempTotalItem += cartItem.quantity
                    }

                    if (cartItems.size == 0 && tempTotalItem == 0) {
                        tvCartCounter?.visibility = View.GONE
                    } else {
                        tvCartCounter?.visibility = View.VISIBLE
                        tvCartCounter?.text = "" + tempTotalItem
                    }
                    cartCounter += tempTotalItem
                    if (currentCartItem != null) {
                        tvQty!!.text = "" + currentCartItem!!.quantity
                    }
                }

                Status.ERROR -> {
                    showError(getString((R.string.something_went_wrong)))
                }

                Status.EMPTY -> {
                    productInCart = false
                    cartItems.clear()
                    productInCart = false
                    currentCartItem = null

                    if (cartItems.size == 0) {
                        tvCartCounter?.text = ""
                        tvCartCounter?.visibility = View.GONE
                    } else {
                        tvCartCounter?.visibility = View.VISIBLE
                        tvCartCounter?.text = cartItems.size.toString()
                    }
                    if (currentCartItem != null) {
                        tvQty!!.text = "" + currentCartItem!!.quantity
                    } else {
                        tvQty!!.text = "0"
                    }
                }

            }

        })

    }


    private fun setUpPage(product: NewProduct) {
        this.product = product
        this.currentVendorId = product.vendor._id
        tvTitle.text = product.name
        tvDescription.text = Html.fromHtml(product.desc)

        if (product.file != null && product.file!!.isNotEmpty()) {
            vpImages.adapter = ImagePagerAdapter(this, product.file!!.map {
                Image().apply {
                    src = it.location
                }
            })
            indicator.setViewPager(vpImages)
        }

        val salePrice = product.selling_price
        val regularPrice = product.price

        if (!TextUtils.isEmpty(salePrice)) {

            tvCallToAction.text = getString(
                R.string.Rs_double,
                salePrice.toDouble()
            )

            tvCallToActio1n1.text = getString(
                R.string.Rs_double,
                regularPrice.toDouble()
            )
            tvCallToActio1n1.paintFlags = tvCallToActio1n1.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            tvOnSale.visibility = View.VISIBLE
        } else {
            tvCallToAction.text = getString(
                R.string.Rs_double,
                regularPrice.toDouble()
            )
            tvOnSale.visibility = View.GONE
        }

        tvAdd.setOnClickListener {
            if (cartItems.any { it.city != product.city.id }) {
                showDialog(false)
            } else {
                if (!checkExitst(producCityId)) {
                    showServiceDialog()
                    return@setOnClickListener
                }
                cal_dist = tvQty!!.text.toString().toInt()
                cal_dist++;
                tvQty.text = cal_dist.toString()
                when {
                    currentCartItem != null -> {
                        updateCart()
                    }

                    cal_dist == 1 -> {
                        addToCart()
                    }

                    else -> {
                        updateCart()
                    }
                }
            }
        }


        tvReduce.setOnClickListener {
            a = tvQty!!.text.toString()
            d = Integer.parseInt(a)
            if (d == 1) {
                cal_dist = --d
                tvQty.text = cal_dist.toString()
                deleteFromCart()
            } else if (d > 1) {
                cal_dist = --d
                tvQty.text = cal_dist.toString()
                updateCart()
            }
        }

        if (edtZipCode.text?.isNotEmpty()!!) {
            checkProductAvailability(edtZipCode.text.toString().toInt())
        }
    }

    private fun showServiceDialog() {
        var dialog: Dialog? = null
//        val handleButtonClick = View.OnClickListener {
//            dialog?.dismiss()
//        }
//        dialog = Utils.showDialog(
//            this,
//            customAppData.data!!,
//            Utils.COMPANION_SERVICE_CHECK,
//            handleButtonClick
//        )
    }

    fun toast(text: String) {
        Toast.makeText(baseContext, text, Toast.LENGTH_LONG).show()
    }

    private lateinit var progressDialog: ProgressDialogFragment

    fun showLoading(title: String, message: String) {
        val manager = supportFragmentManager
        progressDialog = ProgressDialogFragment.newInstance(title, message)
        progressDialog.isCancelable = false
        progressDialog.show(manager, "progress")
    }

    fun showLoading() {
        showLoading(getString(R.string.please_wait), getString(R.string.loading))
    }

    fun stopShowingLoading() {
        if (this::progressDialog.isInitialized)
            progressDialog.dismiss()
    }

    var tvCartCounter: TextView? = null
    var tvWishlistCounter: TextView? = null

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_cart -> {

                true
            }

            R.id.menu_wishlist -> {

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun enableAddToCart(enable: Boolean) {
        tvReduce.alpha = if (enable) 1f else .1f
        tvAdd.alpha = if (enable) 1f else .1f
        tvReduce.isEnabled = enable;
        tvAdd.isEnabled = enable;
    }

    private fun showCustomToast() {
        val toast = Toast.makeText(
            this,
            getString(R.string.serving),
            Toast.LENGTH_LONG
        )
        toast.show()
    }

    private fun checkExitst(cityId: Int): Boolean {
        return AppUtils(this).addCityId(cityId.toString())
    }

    private fun removeFromPref(cityId: Int) {
        AppUtils(this).removeCity(cityId.toString())
    }


    private fun showDialog(unavailabe: Boolean) {

        val customServiceDialog = Dialog(this)
        customServiceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view: View = View.inflate(this, R.layout.dialog_custom, null)
        val back = ColorDrawable(Color.TRANSPARENT);
        val inset = InsetDrawable(back, 10);
        customServiceDialog.window!!.setBackgroundDrawable(inset);

        customServiceDialog.setContentView(view)
        customServiceDialog.setCanceledOnTouchOutside(true)
        val okayClose = view.findViewById<MaterialButton>(R.id.okayClose)
        val title = view.findViewById<TextView>(R.id.tvTitle)
        val subTitle = view.findViewById<TextView>(R.id.tvSubTitle)
        val desc = view.findViewById<TextView>(R.id.tvDesc)
        val llMainLayout = view.findViewById<LinearLayout>(R.id.llMainLayout)

        if (this::appData.isInitialized) {
            val appSettings = appData.result.appSettings.popup
            title.text = appSettings.popup_title
            if (appSettings.popup_title_color.isNotEmpty()) {
                title.setTextColor(Color.parseColor(appSettings.popup_title_color))
            }
            if (unavailabe) {
                desc.text = "Currently we are not delivering to this Pin code"
            } else {
                desc.text =
                    "Please place one order from one city only. One order from two different cities is not possible"
            }

            subTitle.text = appSettings.popup_subtitle
            if (appSettings.subtitle_title_color.isNotEmpty()) {
                subTitle.setTextColor(Color.parseColor(appSettings.subtitle_title_color))
            }
        }

        okayClose.setOnClickListener {
            customServiceDialog.dismiss()
        }
        customServiceDialog.show()
    }

    override fun onSaved() {
        defaultAddress = AppUtils(this).defaultAddress
    }
}
