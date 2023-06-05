package me.taste2plate.app.customer.ui.offers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.clevertap.android.sdk.CleverTapAPI
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.HomeProductAdapter
import me.taste2plate.app.customer.common.BaseActivity
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.databinding.ActivityOffersBinding
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.updated_flow.CheckoutViewModel
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.ProductViewModel
import me.taste2plate.app.models.filters.ProductCategoryFilter

class OffersActivity : WooDroidActivity<CheckoutViewModel>() {

    override lateinit var viewModel: CheckoutViewModel
    lateinit var productViewModel: ProductViewModel
    var type:Int = 0
    private var offersAdapter: HomeProductAdapter? = null
    lateinit var offersBinding: ActivityOffersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        offersBinding = ActivityOffersBinding.inflate(layoutInflater)
        setContentView(offersBinding.root)
        CleverTapAPI.getDefaultInstance(this)?.recordScreen("Offers")
        viewModel = getViewModel(CheckoutViewModel::class.java)
        productViewModel = getViewModel(ProductViewModel::class.java)
        type = intent.getIntExtra("type", 0)
        val layoutManager2 = GridLayoutManager(this@OffersActivity,2)
        offersBinding.offers.layoutManager = layoutManager2

        offersBinding.toolbar.setNavigationOnClickListener {
            finish()
        }
        getOffers()
    }

    private fun getOffers() {

        val filter = ProductCategoryFilter()
        filter.setPer_page(100)
        viewModel.fetchOffers(AppUtils(this).defaultAddress.city!!._id).observe(this, androidx.lifecycle.Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.SUCCESS -> {
                    stopShowingLoading()
                    val offersResponse = response.data()
                    when (type) {
                        1 -> {
                            if(!offersResponse.best_seller.isNullOrEmpty()){
                                offersBinding.noOffers.visibility = View.GONE
                                offersAdapter = HomeProductAdapter(offersResponse.best_seller, productViewModel, this)
                                offersBinding.offers.adapter = offersAdapter
                            }else{
                                offersBinding.noOffers.visibility = View.VISIBLE
                                showError("No Offers!")
                            }
                        }
                        2 -> {
                            if(!offersResponse.combo.isNullOrEmpty()){
                                offersBinding.noOffers.visibility = View.GONE
                                offersAdapter = HomeProductAdapter(offersResponse.combo, productViewModel, this)
                                offersBinding.offers.adapter = offersAdapter
                            }else{
                                showError("No Offers!")
                                offersBinding.noOffers.visibility = View.VISIBLE
                            }
                        }
                        3 -> {
                            if(!offersResponse.product_deal.isNullOrEmpty()){
                                offersBinding.noOffers.visibility = View.GONE
                                offersAdapter = HomeProductAdapter(offersResponse.product_deal, productViewModel, this)
                                offersBinding.offers.adapter = offersAdapter
                            }else{
                                showError("No Offers!")
                                offersBinding.noOffers.visibility = View.VISIBLE
                            }
                        }
                        else -> {
                            finish()
                        }
                    }
                }

                Status.ERROR -> {
                    stopShowingLoading()
                    showError("No Offers!")
                    finish()
                }

                Status.EMPTY -> {
                    showError("No Offers!")
                    finish()
                }
            }

        })

    }


}