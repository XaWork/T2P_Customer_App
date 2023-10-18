package me.taste2plate.app.customer.ui.product

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.content_subcategory.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.veg_nonveg_toggle.vegNonVegSwitch
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.CategoryAdapter
import me.taste2plate.app.customer.common.BaseActivity
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.ui.state.ProgressDialogFragment
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.ProductViewModel
import me.taste2plate.app.data.api.AnalyticsAPI
import me.taste2plate.app.data.api.LogRequest
import me.taste2plate.app.models.Category
import me.taste2plate.app.models.filters.ProductCategoryFilter
import java.util.*


class CategoryActivity : BaseActivity() {

    private var categoryAdapter: CategoryAdapter? = null
    lateinit var categories: List<Category.Result>
    private val categoryMap = mutableMapOf<String, ArrayList<Category.Result>>()

    val TAG = this::getLocalClassName

    private lateinit var viewModel: ProductViewModel

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subcategory)
        setSupportActionBar(toolbar)


        //send event info
        val analytics = AnalyticsAPI()
        val appUtils = AppUtils(this)
        val logRequest = LogRequest(
            category = appUtils.referralInfo[0],
            token = appUtils.referralInfo[1],
            type = "page visit",
            event = "visit to category page",
            page_name = "/Category",
            source = "android",
            geo_ip = AppUtils(this).ipAddress,
            user_id = AppUtils(this).user.id,
        )
        analytics.addLog(logRequest)

        val customAppData = AppUtils(this).appData

        vegNonVegToggle.visibility = View.VISIBLE
        vegNonVegSwitch.isChecked = AppUtils(this).taste != "1"
        vegNonVegSwitch.setOnCheckedChangeListener{ _, isChecked ->
            AppUtils(this).taste = if (isChecked) "0" else "1"
            getCategories()
        }

        viewModel = getViewModel(ProductViewModel::class.java)

        toolbar.setNavigationOnClickListener {
            finish()
        }
        title = intent.getStringExtra("type");
        getCategories()

    }

    private fun getCategories() {
        val taste = AppUtils(this).taste
        val filter = ProductCategoryFilter()
        filter.setPer_page(100)
        viewModel.categories(filter, taste).observe(this, androidx.lifecycle.Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.SUCCESS -> {
                    stopShowingLoading()
                    val categoriesResponse = response.data()
                    prepareCateData(categoriesResponse.result!!)
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

    private fun prepareCateData(categoryList: List<Category.Result>) {
        categories = ArrayList()
        categories =
            categoryList.filterNot { it.parent != null }

        setupCategories()

    }

    private fun setupCategories() {

        val layoutManager = LinearLayoutManager(baseContext)
        rvSubCategory.layoutManager = layoutManager
        rvSubCategory.isNestedScrollingEnabled = false

        categoryAdapter = CategoryAdapter(categories, categoryMap)
        rvSubCategory.adapter = categoryAdapter

    }

    private lateinit var progressDialog: ProgressDialogFragment

    fun showLoading(title: String, message: String) {
        val manager = supportFragmentManager
        progressDialog = ProgressDialogFragment.newInstance(title, message)
        progressDialog.isCancelable = false
        progressDialog.show(manager, "progress")
    }

    fun showLoading() {
        showLoading("This will only take a sec", "Loading")
    }

    fun stopShowingLoading() {
        progressDialog.dismiss()
    }

}
