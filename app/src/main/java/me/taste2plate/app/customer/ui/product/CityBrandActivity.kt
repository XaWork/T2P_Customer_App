package me.taste2plate.app.customer.ui.product

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.content_subcategory.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.veg_nonveg_toggle.vegNonVegSwitch
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.CityBrandAdapter
import me.taste2plate.app.customer.common.BaseActivity
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.ui.home.HomeActivity
import me.taste2plate.app.customer.ui.state.ProgressDialogFragment
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.ProductViewModel
import me.taste2plate.app.data.api.AnalyticsAPI
import me.taste2plate.app.data.api.LogRequest
import me.taste2plate.app.models.CityBrand
import java.util.*


class CityBrandActivity : BaseActivity() {

    lateinit var adapter: CityBrandAdapter
    var mCityBrandList: ArrayList<CityBrand.Result> = ArrayList()

    val TAG = this::getLocalClassName

    private lateinit var viewModel: ProductViewModel

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subcategory)

        //send event info
        val analytics = AnalyticsAPI()
        val appUtils = AppUtils(this)
        val logRequest = LogRequest(
            category = appUtils.referralInfo[0],
            token = appUtils.referralInfo[1],
            type = "page visit",
            event = "visit to city list page",
            page_name = "/CityBrand",
            source = "android",
            geo_ip = AppUtils(this).ipAddress,
            user_id = AppUtils(this).user.id,
        )
        analytics.addLog(logRequest)

        setSupportActionBar(toolbar)
        searchCityBrand.visibility = View.VISIBLE

        viewModel = getViewModel(ProductViewModel::class.java)

        val customAppData = AppUtils(this).appData

       // vegNonVegToggle.visibility = View.VISIBLE
        vegNonVegSwitch.isChecked = AppUtils(this).taste != "1"
        vegNonVegSwitch.setOnCheckedChangeListener{ _, isChecked ->
            AppUtils(this).taste = if (isChecked) "0" else "1"
            getData()
        }

        toolbar.setNavigationOnClickListener {
            finish()
        }
        title = intent.getStringExtra("type");

        getData()

        searchCityBrand.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun getData(){
        if (intent.getStringExtra("type")!!.contentEquals("Flavours Of India")) {
            getCuisine()
        } else {
            cityBrand()
        }
    }

    private fun filter(text: String) {
        val cityBrandList: ArrayList<CityBrand.Result> = ArrayList()
        for (item in mCityBrandList) {
            if (item.name?.lowercase()?.contains(text.lowercase()) == true) {
                cityBrandList.add(item)
            }
        }
        if (cityBrandList.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            adapter.filterList(cityBrandList)
        }
    }

    private fun getCuisine() {
        try{
            val defaultAddress = AppUtils(this).defaultAddress
            if (defaultAddress != null) {
                viewModel.homePageData(AppUtils(this).defaultAddress.city._id, "1")
                    .observe(this) { response ->
                        when (response!!.status()) {
                            Status.LOADING -> {
                                showLoading()
                            }

                            Status.SUCCESS -> {
                                stopShowingLoading()
                                val homePageResponse = response.data()
                                mCityBrandList.addAll(homePageResponse.cuisine)
                                setUpPage()
                            }

                            Status.ERROR, Status.EMPTY -> {
                                stopShowingLoading()
                                showError(getString(R.string.something_went_wrong))
                            }
                        }

                    }
            } else {
                Toast.makeText(this, "Select default address to continue.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@CityBrandActivity, HomeActivity::class.java))
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }


    private fun setUpPage() {
        val layoutManager = LinearLayoutManager(baseContext)
        rvSubCategory.layoutManager = layoutManager
        rvSubCategory.isNestedScrollingEnabled = false

        adapter = CityBrandAdapter(mCityBrandList, intent.getStringExtra("type")!!)
        rvSubCategory.adapter = adapter
    }

    private fun cityBrand() {
        val taste = AppUtils(this).taste
        val data =
            if (intent.getStringExtra("type")!!
                    .contentEquals("City")
            ) viewModel.city else viewModel.getBrandList()

        data.observe(this) { response ->
            when (response!!.status()) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.SUCCESS -> {
                    stopShowingLoading()
                    val cartResponse = response.data()
                    mCityBrandList.addAll(cartResponse.result!!)
                    setUpPage()
                }

                Status.ERROR, Status.EMPTY -> {
                    stopShowingLoading()
                    showError(getString(R.string.something_went_wrong))
                }
            }

        }
    }

    private lateinit var progressDialog: ProgressDialogFragment

    fun showLoading(title: String, message: String) {
        val manager = supportFragmentManager
        progressDialog = ProgressDialogFragment.newInstance(title, message)
        progressDialog.isCancelable = false
        progressDialog.show(manager, "progress")
    }

    fun showLoading() {
        showLoading(getString(R.string.loading_title), getString(R.string.loading))
    }

    fun stopShowingLoading() {
        if (this::progressDialog.isInitialized)
            progressDialog.dismiss()
    }

}
