package me.taste2plate.app.customer.ui.coupon

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clevertap.android.sdk.CleverTapAPI
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.AllOffersAdapter
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.common.setupDialog
import me.taste2plate.app.customer.databinding.ActivityAllOffersBinding
import me.taste2plate.app.customer.hideSoftKeyboard
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.ui.offers.OffersActivity
import me.taste2plate.app.customer.ui.rewards.RewardActivity
import me.taste2plate.app.customer.updated_flow.CheckoutViewModel
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.CustomerViewModel
import me.taste2plate.app.customer.viewmodels.ProductViewModel
import me.taste2plate.app.models.AllOffersItem
import me.taste2plate.app.models.filters.ProductCategoryFilter

class AllOffersActivity : WooDroidActivity<CheckoutViewModel>() {

    private lateinit var adapterAll: AllOffersAdapter
    override lateinit var viewModel: CheckoutViewModel
    lateinit var binding: ActivityAllOffersBinding
    private lateinit var dashBoardItemList: List<AllOffersItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllOffersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = getViewModel(CheckoutViewModel::class.java)
        CleverTapAPI.getDefaultInstance(this)?.recordScreen("All Offers")

        title = "Offers & Deals"
        setSupportActionBar(toolbar)
        val customAppData = AppUtils(this).appData
        toolbar.setNavigationOnClickListener {
            finish()
        }

        if(AppUtils(this).defaultAddress!=null) {
            getOffers()
        }else{
            showError("Select delivery address")
            finish()
        }

        binding.bestSeller.setOnClickListener{
            val localIntent = Intent(this@AllOffersActivity, OffersActivity::class.java)
            localIntent.putExtra("type", 1)
            startActivity(localIntent)
        }

        binding.combo.setOnClickListener{
            val localIntent = Intent(this@AllOffersActivity, OffersActivity::class.java)
            localIntent.putExtra("type", 2)
            startActivity(localIntent)
        }

        binding.ootd.setOnClickListener {
            val localIntent = Intent(this@AllOffersActivity, OffersActivity::class.java)
            localIntent.putExtra("type", 3)
            startActivity(localIntent)
        }

        binding.gift.setOnClickListener {
            Snackbar.make(binding.root, "Coming soon!", Snackbar.LENGTH_LONG).show()
            Log.e("Coming", "Soon")
        }

        binding.rewards.setOnClickListener {
            val rewardIntent = Intent(this@AllOffersActivity, RewardActivity::class.java)
            startActivity(rewardIntent)
        }
    }


    private fun getOffers() {
        val address = AppUtils(this).defaultAddress
        viewModel.fetchOffers(address.city!!._id).observe(this, Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.SUCCESS -> {
                    stopShowingLoading()
                    if (response.data().coupon.isNotEmpty()) {
                        binding.coupon.setupDialog(
                            "Coupon Offers",
                            response.data().coupon.toTypedArray(), { "${it.coupon}\n${it.desc}" },
                            {
                                Log.e("Data", it.toString())
                            }, {
                                hideSoftKeyboard()
                            },{
                            }
                        )
                    }
                }

                Status.ERROR -> {
                    stopShowingLoading()
                }

                Status.EMPTY -> {
                    stopShowingLoading()
                }
            }
        })
    }
}