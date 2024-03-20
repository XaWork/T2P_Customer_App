package me.taste2plate.app.customer.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.installations.FirebaseInstallations
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.info_popup.view.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.SplashActivity
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.ui.ContactUsDetailActivity
import me.taste2plate.app.customer.ui.DevUseActivity
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.ui.coupon.AllOffersActivity
import me.taste2plate.app.customer.ui.customer.BulkOrderActivity
import me.taste2plate.app.customer.ui.customer.ProfileActivity
import me.taste2plate.app.customer.ui.membership.MembershipListActivity
import me.taste2plate.app.customer.ui.membership.MyPlanActivity
import me.taste2plate.app.customer.ui.onboarding.OnBoardActivity
import me.taste2plate.app.customer.ui.order.MyOrdersActivity
import me.taste2plate.app.customer.ui.product.CartActivity
import me.taste2plate.app.customer.ui.product.CategoryActivity
import me.taste2plate.app.customer.ui.product.CityBrandActivity
import me.taste2plate.app.customer.ui.product.WishlistActivity
import me.taste2plate.app.customer.ui.rewards.RewardViewModel
import me.taste2plate.app.customer.ui.wallet.WalletActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.utils.Utils
import me.taste2plate.app.customer.viewmodels.CartViewModel
import me.taste2plate.app.models.PointSetups
import me.taste2plate.app.models.newproducts.ShowError
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class HomeActivity : WooDroidActivity<CartViewModel>() {
    // var cartCounter: String? = ""
    override lateinit var viewModel: CartViewModel
    lateinit var rewardViewModel: RewardViewModel

    // index to identify current nav menu item
    private var navItemIndex = 0

    // tags used to attach the fragments
    private val TAG_HOME = "home"
    private val TAG_ORDERS = "orders"
    private val TAG_PROFILE = "profile"
    private val TAG_CONTACT_US = "contact"
    private val TAG_RATE = "rate"
    private var CURRENT_TAG = TAG_HOME
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null


    // toolbar titles respected to selected nav menu item
    private var activityTitles: Array<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)
        title = getString(R.string.app_name)
        rewardViewModel = getViewModel(RewardViewModel::class.java)

        Log.e(CURRENT_TAG, "Check app utils: ${AppUtils(this).appData}")

        if (AppUtils(this).appData == null) {
            AppUtils(this).logOut()
            finishAffinity()
            startActivity(Intent(this, SplashActivity::class.java))
        }

        fab.setOnClickListener {
            openWhatsApp()
        }

        showInfoPopup()

        activityTitles = resources.getStringArray(R.array.nav_item_activity_titles)
        setUpNavigationView()


        val mDrawerHeaderTitle =
            navView.getHeaderView(0).findViewById(R.id.name) as TextView

        if (AppUtils(this).user != null) {
            mDrawerHeaderTitle.text = getString(R.string.hey) + AppUtils(this).user.email
        } else {
            mDrawerHeaderTitle.text = getString(R.string.app_name)
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, HomeFragment.newInstance())
        transaction.commit()
        /*
                navigation.menu.getItem(0).isChecked = false;

                navigation.setOnNavigationItemSelectedListener {
                    when (it.itemId) {
                        R.id.deals -> {
                            if (AppUtils(this).defaultAddress != null) {
                                startActivity(
                                    Intent(
                                        this@HomeActivity,
                                        AllOffersActivity::class.java
                                    )
                                )
                            } else {
                                showErrorDialog("Set delivery location")
                            }
                        }
                        R.id.city -> {
                            startActivity(
                                Intent(
                                    this@HomeActivity,
                                    CityBrandActivity::class.java
                                ).putExtra("type", "City")
                            )

                            *//* if (AppUtils(this).defaultAddress != null) {
                         startActivity(
                             Intent(
                                 this@HomeActivity,
                                 CityBrandActivity::class.java
                             ).putExtra("type", "City")
                         )
                     } else {
                         showErrorDialog("Set delivery location");
                     }*//*
                }
                R.id.brands -> {
                    startActivity(
                        Intent(
                            this@HomeActivity,
                            CityBrandActivity::class.java
                        ).putExtra("type", "Brand")
                    )

                    *//* if (AppUtils(this).defaultAddress != null) {
                         startActivity(
                             Intent(
                                 this@HomeActivity,
                                 CityBrandActivity::class.java
                             ).putExtra("type", "Brand")
                         )
                     } else {
                         showErrorDialog("Set delivery location");
                     }*//*
                }
                R.id.cuisine -> {
                    if (AppUtils(this).defaultAddress != null) {
                        startActivity(
                            Intent(
                                this@HomeActivity,
                                CityBrandActivity::class.java
                            ).putExtra("type", "Flavours Of India")
                        )
                    } else {
                        showErrorDialog("Set delivery location")
                    }
                }
                R.id.category ->
                    startActivity(
                        Intent(this@HomeActivity, CategoryActivity::class.java).putExtra(
                            "type",
                            "Explore"
                        )
                    )
                *//*if (AppUtils(this).defaultAddress != null) {
                    startActivity(
                        Intent(this@HomeActivity, CategoryActivity::class.java).putExtra(
                            "type",
                            "Categories"
                        )
                    )
                } else {
                    showErrorDialog("Set delivery location");
                }*//*
            }
            return@setOnNavigationItemSelectedListener true
        }*/

    }

    private fun openWhatsApp() {
        val customAppData = AppUtils(this).appData
        val phone = customAppData.result.whatsapp
        val intent = Intent(
            Intent.ACTION_VIEW, Uri.parse(
                "https://api.whatsapp.com/send?phone=$+91 8100709627"
            )
        )
        startActivity(intent)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        actionBarDrawerToggle?.syncState()
    }

    private fun selectNavMenu() {
        navView.menu.getItem(navItemIndex).isChecked = true
    }

    private fun setUpNavigationView() {

        actionBarDrawerToggle =
            object : ActionBarDrawerToggle(
                this,
                drawer_layout,
                toolbar,
                R.string.openDrawer,
                R.string.closeDrawer
            ) {
            }

        //Setting the actionbarToggle to drawer layout
        drawer_layout.addDrawerListener(actionBarDrawerToggle!!)

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navView.setNavigationItemSelectedListener { menuItem ->
            // This method will trigger on item Click of navigation menu
            //Check to see which item was being clicked and perform appropriate action
            when (menuItem.itemId) {
                //Replacing the main content with ContentFragment Which is our Inbox View;
                R.id.nav_home -> {
                    navItemIndex = 0
                    CURRENT_TAG = TAG_HOME
                }

                R.id.nav_orders -> {
                    navItemIndex = 1
                    CURRENT_TAG = TAG_ORDERS
                }

                R.id.nav_profile -> {
                    navItemIndex = 2
                    CURRENT_TAG = TAG_PROFILE
                }

                R.id.nav_share -> {
                    referAndEarn()
                }

                R.id.bulk_order -> {
                    startActivity(Intent(this@HomeActivity, BulkOrderActivity::class.java))
                }

                R.id.nav_rate -> {
                    val uri = Uri.parse("market://details?id=" + applicationContext.packageName)
                    val goToMarket = Intent(Intent.ACTION_VIEW, uri)
                    // To count with Play market back stack, After pressing back button,
                    // to taken back to our application, we need to add following flags to intent.
                    goToMarket.addFlags(
                        Intent.FLAG_ACTIVITY_NO_HISTORY or
                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                    )
                    try {
                        startActivity(goToMarket)
                    } catch (e: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("http://play.google.com/store/apps/details?id=" + applicationContext.packageName)
                            )
                        )
                    }
                }

                R.id.nav_contact_us -> {
                    navItemIndex = 3
                    CURRENT_TAG = TAG_CONTACT_US
                }

                R.id.nav_dev -> {
                    startActivity(Intent(this@HomeActivity, DevUseActivity::class.java))
                }

                R.id.nav_logout -> {
                    AppUtils(this).logOut()
                    FirebaseAuth.getInstance().signOut()
                    //AppUtils(this).saveToken(it.result.token)
                    startActivity(
                        Intent(
                            this,
                            OnBoardActivity::class.java
                        ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    )
                }

                R.id.member_ship -> {
                    startActivity(Intent(this@HomeActivity, MembershipListActivity::class.java))
                }

                R.id.my_plan -> {
                    startActivity(Intent(this@HomeActivity, MyPlanActivity::class.java))
                }

                R.id.wallet -> {
                    startActivity(Intent(this@HomeActivity, WalletActivity::class.java))
                }

                else -> navItemIndex = 0
            }

            //Checking if the item is in checked state or not, if not make it in checked state
            menuItem.isChecked = !menuItem.isChecked
            menuItem.isChecked = true

            loadHomeFragment()

            true
        }

    }

    private fun referAndEarn() {
        rewardViewModel.getAppSettings().observe(this) {
            when (it.status()) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.SUCCESS -> {
                    stopShowingLoading()
                    val pointSettings: PointSetups? = it.data().result.pointSetting

                    val sharingIntent = Intent(Intent.ACTION_SEND)
                    sharingIntent.type = "text/plain"
                    val shareBody = buildString {
                        appendLine("http://play.google.com/store/apps/details?id=" + applicationContext.packageName)
                        append(
                            "Register with Refer code: ${AppUtils(this@HomeActivity).user.mobile}\n" +
                                    "Earn Reward Point: ${pointSettings?.friendBonus}"
                        )
                    }
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                    startActivity(
                        Intent.createChooser(
                            sharingIntent,
                            getString(R.string.share_via)
                        )
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

    private fun loadHomeFragment() {
        selectNavMenu()

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (supportFragmentManager.findFragmentByTag(CURRENT_TAG) != null) {
            drawer_layout.closeDrawers()
            return
        }

        openActivity()
        //Closing drawer on item click
        drawer_layout.closeDrawers()

        // refresh toolbar menu
        invalidateOptionsMenu()
    }

    private fun openActivity() {
        when (navItemIndex) {
            1 ->
                // photos
                startActivity(Intent(this, MyOrdersActivity::class.java))

            2 ->
                // movies fragment
                startActivity(Intent(this, ProfileActivity::class.java))

            3 ->
                startActivity(Intent(this, ContactUsDetailActivity::class.java))
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawers()
            return
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (navItemIndex != 0) {
            navItemIndex = 0
            CURRENT_TAG = TAG_HOME
            loadHomeFragment()
            return
        }

        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search, menu)
        val item = menu.findItem(R.id.action_cart)
        val walletItem = menu.findItem(R.id.action_wallet)
        val wishlistItem = menu.findItem(R.id.action_wishlist)
        val rootView = item.actionView as FrameLayout
        val wishRootView = wishlistItem.actionView as FrameLayout

        //cart counter controller
        val control =
            rootView.findViewById<TextView>(R.id.tvCartCounter)
        if (cartCounter.isNullOrEmpty()) {
            // rootView.findViewById<TextView>(R.id.ivCart)
            control.visibility = GONE
        } else {
            control.visibility = VISIBLE
            control.text = cartCounter
        }

        rootView.setOnClickListener {
            if (AppUtils(this).defaultAddress != null) {
                startActivity(
                    Intent(applicationContext, CartActivity::class.java)
                )
            } else {
                showErrorDialog("Select Delivery Address!")
            }
        }

        //wallet
        walletItem.setOnMenuItemClickListener {
            startActivity(Intent(this@HomeActivity, WalletActivity::class.java))
            return@setOnMenuItemClickListener true
        }

        //wishlist
        //wishlist counter controller
        val wishControl =
            wishRootView.findViewById<TextView>(R.id.tvWishlistCounter)
        if (wishlistCounter.isNullOrEmpty()) {
            wishControl.visibility = GONE
        } else {
            wishControl.visibility = VISIBLE
            wishControl.text = wishlistCounter
        }

        wishRootView.setOnClickListener {
            startActivity(Intent(this@HomeActivity, WishlistActivity::class.java))
        }

        return true
    }

    private fun showInfoPopup() {
        Log.e("TAG", "showInfoPopup: start")
        rewardViewModel.getAppSettings().observe(this) {
            Log.e("TAG", "showInfoPopup: call reward viewmodel")
            when (it.status()) {

                Status.LOADING -> {
                    Log.e("TAG", "showInfoPopup: loading")
                    showLoading()
                }

                Status.SUCCESS -> {

                    Log.e("TAG", "showInfoPopup: success")
                    stopShowingLoading()
                    val infoPopup = it.data().result.appSettings.info_popup

                    if (infoPopup.info_on_off == 1) {
                        Log.e("TAG", "showInfoPopup: $infoPopup")
                        val dialog = AlertDialog.Builder(this)

                        val view = layoutInflater.inflate(R.layout.info_popup, null)
                        dialog.setView(view)
                        dialog.setCancelable(false)

                        val builder = dialog.create()
                        builder.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

                        val cancel = view.cancel
                        val text = view.popupTxt
                        val image = view.popupImg
                        text.text = infoPopup.info_popup_text

                        Glide.with(this@HomeActivity)
                            .load(infoPopup.info_popup_image)
                            .error(R.drawable.ic_launcher_foreground)
                            .centerCrop()
                            .placeholder(R.drawable.logo_new)
                            .into(image)

                        cancel.setOnClickListener {
                            builder.dismiss()
                        }

                        builder.show()
                    }
                }

                Status.ERROR, Status.EMPTY -> {
                    stopShowingLoading()
                    Log.e("TAG", "showInfoPopup: error : ${it.error()}")
                    Toast.makeText(
                        baseContext,
                        it.error().message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        }
    }

    private fun showErrorDialog(message: String) {
        var dialog: Dialog? = null
        val handleButtonClick = View.OnClickListener {
            dialog?.dismiss()
        }
        dialog = Utils.showDialog(
            this,
            AppUtils(this).appData,
            Utils.SELECTED_ADDRESS,
            handleButtonClick
        )
        dialog.show()

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ShowError) {
        showErrorDialog("select address")
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    override fun onResume() {
        super.onResume()
        navItemIndex = 0
        invalidateOptionsMenu()
        selectNavMenu()
        EventBus.getDefault().register(this)
    }

}
