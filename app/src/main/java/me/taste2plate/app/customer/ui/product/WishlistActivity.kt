package me.taste2plate.app.customer.ui.product

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clevertap.android.sdk.CleverTapAPI
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.content_cart.*
import kotlinx.android.synthetic.main.content_product.*
import kotlinx.android.synthetic.main.content_wishlist.*
import kotlinx.android.synthetic.main.single_wishlist_item.*
import kotlinx.android.synthetic.main.state_empty.*
import kotlinx.android.synthetic.main.state_empty_wishlist.*
import kotlinx.android.synthetic.main.toolbar.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.CartAdapter
import me.taste2plate.app.customer.adapter.WishlistAdapter
import me.taste2plate.app.customer.common.BaseActivity
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.events.AddQuantityEvent
import me.taste2plate.app.customer.events.DeleteFromWishlistEvent
import me.taste2plate.app.customer.events.LessQuantityEvent
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.ui.home.HomeActivity
import me.taste2plate.app.customer.ui.onboarding.OnBoardActivity
import me.taste2plate.app.customer.updated_flow.CheckoutActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.CartViewModel
import me.taste2plate.app.customer.viewmodels.ProductViewModel
import me.taste2plate.app.models.Customer
import me.taste2plate.app.models.address.Address
import me.taste2plate.app.models.cart.CartItem
import me.taste2plate.app.models.wishlist.WishlistItemResponse
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class WishlistActivity : WooDroidActivity<ProductViewModel>() {

    override lateinit var viewModel: ProductViewModel
    var wishlistItems: ArrayList<WishlistItemResponse.Result> = ArrayList()

    lateinit var adapter: WishlistAdapter

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)
        setSupportActionBar(toolbar)
        CleverTapAPI.getDefaultInstance(this)?.recordScreen("Wishlist")

        Log.e("wishlist", "inside wishlist activities onCreate function")

        toolbar.setNavigationOnClickListener {
            finish()
        }

        viewModel = getViewModel(ProductViewModel::class.java)
        title = "Wishlist"

        llEmptyState_layout_wishlist.visibility = View.GONE

        hideWishlist = true
        invalidateOptionsMenu()

        bEmptyState_action_wishlist.setOnClickListener {
            openProductListing()
        }
    }

    private fun getWishlist() {
        viewModel.getWishlist(AppUtils(this).user.id).observe(this){response->
            when(response.status()){
                Status.LOADING -> showLoading()
                Status.SUCCESS -> {
                    stopShowingLoading()
                    wishlistItems.clear()

                    for(singleResult in response.data().result){
                        wishlistItems.add(singleResult)
                    }
                    wishlistCounter = response.data().result.size.toString()

                    //set wishlist items
                    val layoutManager = LinearLayoutManager(
                        baseContext,
                        RecyclerView.VERTICAL,
                        false
                    )
                    rvWishlist.layoutManager = layoutManager
                    rvWishlist.isNestedScrollingEnabled = false

                    Log.e("wishlist", rvWishlist.isNestedScrollingEnabled.toString())

                    adapter = WishlistAdapter(wishlistItems)
                    rvWishlist.adapter = adapter

                    setUpView()
                }
                Status.ERROR -> {
                    stopShowingLoading()
                    setUpView()
                    llEmptyState_layout_wishlist.visibility = View.VISIBLE
                }
                Status.EMPTY -> {
                    stopShowingLoading()
                    setUpView()
                    llEmptyState_layout_wishlist.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setUpView(){
        if(wishlistItems.isEmpty()){
            llEmptyState_layout_wishlist.visibility = View.VISIBLE
        }else{
            llEmptyState_layout_wishlist.visibility = View.GONE
        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: DeleteFromWishlistEvent) {
        deleteFromWishlist(event.position)
    }

    private fun deleteFromWishlist(position: Int) {
        viewModel.deleteFromWishlist(AppUtils(this).user.id, wishlistItems[position].product.id).observe(this){response ->
            when(response.status()){
                Status.LOADING -> showLoading()
                Status.SUCCESS -> {
                    stopShowingLoading()
                    Toast.makeText(this, response.data().message, Toast.LENGTH_SHORT).show()

                   /* //remove that product from list and update the list
                    wishlistItems.removeAt(position)
                    adapter = WishlistAdapter(wishlistItems)
                    rvWishlist.adapter = adapter
                    adapter.notifyDataSetChanged()
                    BaseActivity().wishlistCounter = wishlistItems.size.toString()
                    BaseActivity().invalidateOptionsMenu()*/
                    wishlistCounter = (wishlistCounter.toInt()-1).toString()
                    getWishlist()
                }
                Status.ERROR -> stopShowingLoading()
                Status.EMPTY -> stopShowingLoading()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getWishlist()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}
