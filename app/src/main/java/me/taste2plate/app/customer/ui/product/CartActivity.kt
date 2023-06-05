package me.taste2plate.app.customer.ui.product

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clevertap.android.sdk.CleverTapAPI
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.content_cart.*
import kotlinx.android.synthetic.main.content_product.*
import kotlinx.android.synthetic.main.state_empty.*
import kotlinx.android.synthetic.main.state_empty_wishlist.*
import kotlinx.android.synthetic.main.toolbar.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.CartAdapter
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.events.AddQuantityEvent
import me.taste2plate.app.customer.events.LessQuantityEvent
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.ui.home.HomeActivity
import me.taste2plate.app.customer.ui.onboarding.OnBoardActivity
import me.taste2plate.app.customer.updated_flow.CheckoutActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.CartViewModel
import me.taste2plate.app.models.Customer
import me.taste2plate.app.models.address.Address
import me.taste2plate.app.models.cart.CartItem
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class CartActivity : WooDroidActivity<CartViewModel>() {

    override lateinit var viewModel: CartViewModel
    var cartItems: ArrayList<CartItem> = ArrayList()

    lateinit var adapter: CartAdapter
    lateinit var customer: Customer
    var address: Address? = null
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        CleverTapAPI.getDefaultInstance(this)?.recordScreen("Cart")
        address = AppUtils(this).defaultAddress
        setSupportActionBar(toolbar)
        val customAppData = AppUtils(this).appData

        toolbar.setNavigationOnClickListener {
            finish()
        }
        viewModel = getViewModel(CartViewModel::class.java)
        title = "Cart"

        val layoutManager = LinearLayoutManager(
            baseContext,
            RecyclerView.VERTICAL,
            false
        )
        rvCart.layoutManager = layoutManager
        rvCart.isNestedScrollingEnabled = false

        cartItems = ArrayList()

        adapter = CartAdapter(cartItems)
        rvCart.adapter = adapter

        llEmptyState_layout.visibility = View.GONE

        bNext.setOnClickListener {
            customer()
        }

        hideCart = true
        invalidateOptionsMenu()

        bEmptyState_action.setOnClickListener {
            openProductListing()
        }
    }

    override fun onResume() {
        super.onResume()
        if(address!=null) {
            cart(AppUtils(this).user.id,address!!.city!!._id, address!!.pincode!!)
        }else{
            showError("Set default address!")
        }
    }

    fun cart(userId: String, cityId: String, zipCode:String) {
        viewModel.cart(userId, cityId, zipCode).observe(this, Observer { response ->
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
                    cartCounter += tempTotalItem
                    adapter.notifyDataSetChanged()
                    setUpView()
                }

                Status.ERROR -> {
                    stopShowingLoading()
                    setUpView()
                    Log.e("error",""+response.toString())
                }

                Status.EMPTY -> {
                    stopShowingLoading()
                    setUpView()
                    llEmptyState_layout.visibility = View.VISIBLE
                }
            }
        })

    }

    private fun setUpView(){
        if(cartItems.isEmpty()){
            bNext.visibility=View.GONE
            llEmptyState_layout.visibility = View.VISIBLE
        }else{
            bNext.visibility=View.VISIBLE
            llEmptyState_layout.visibility = View.GONE
        }
    }


    private fun updateCart(quantity:Int, productId:String) {
        viewModel.updateCart(quantity, productId,AppUtils(this).user.id).observe(this, Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.SUCCESS -> {
                    stopShowingLoading()
                    cart(AppUtils(this).user.id , address!!.city!!._id, address!!.pincode!!)
                }

                Status.ERROR, Status.EMPTY -> {
                    stopShowingLoading()
                    toast(getString(R.string.error_add_product))
                }
            }

        })
    }


    private fun deleteFromCart(productId:String, userId:String) {
        viewModel.deleteItem(productId, userId).observe(this, Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.SUCCESS -> {
                    stopShowingLoading()
                    cart(AppUtils(this).user.id , address!!.city!!._id, address!!.pincode!!)
                }

                Status.ERROR -> {
                    showError(getString(R.string.error) + response.error().message)
                }
                else -> showError(getString(R.string.something_went_wrong))
            }

        })
    }

    private fun openProductListing() {
        startActivity(
            Intent(
                this,
                HomeActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
        finishAffinity()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: AddQuantityEvent) {
        var cartItem = event.cartLineItem
        var quantity = cartItem.quantity + 1
        updateCart(quantity, cartItem.product._id)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: LessQuantityEvent) {
        var cartItem = event.cartLineItem
        var quantity = cartItem.quantity - 1
        if (quantity == 0) {
            deleteFromCart(cartItem.product._id, AppUtils(this).user.id)
        } else {
            updateCart(quantity, cartItem.product._id)
        }
    }

    private fun customer() {
        val appUtils = AppUtils(this)
        if (appUtils.user != null) {
            startActivity(Intent(this, CheckoutActivity::class.java))
        } else  {
            startActivity(Intent(this, OnBoardActivity::class.java))
        }
    }


    private fun clearCityData() {
        cartItems.clear()
        AppUtils(this).clearCityData()
        cartCounter = ""
    }
}
