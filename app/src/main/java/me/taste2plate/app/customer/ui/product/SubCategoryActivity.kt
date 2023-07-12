package me.taste2plate.app.customer.ui.product

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.clevertap.android.sdk.CleverTapAPI
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.content_subcategory.*
import kotlinx.android.synthetic.main.toolbar.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.SubcategoryAdapter
import me.taste2plate.app.customer.common.BaseActivity
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.ui.state.ProgressDialogFragment
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.utils.ItemOffsetDecoration
import me.taste2plate.app.customer.viewmodels.ProductViewModel
import me.taste2plate.app.data.api.AnalyticsAPI
import me.taste2plate.app.data.api.LogRequest
import me.taste2plate.app.models.Category
import me.taste2plate.app.models.filters.ProductCategoryFilter


class SubCategoryActivity : BaseActivity() {

    lateinit var adapter: SubcategoryAdapter
    var subCategoryList: ArrayList<Category.Result> = ArrayList()

    val TAG = this::getLocalClassName

    private lateinit var viewModel: ProductViewModel
    private lateinit var parentId: String
    private lateinit var progressDialog: ProgressDialogFragment

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subcategory)
        setSupportActionBar(toolbar)


        //send event info
        val analytics = AnalyticsAPI()
        val logRequest = LogRequest(
            type = "page visit",
            event = "visit to sub category page",
            page_name = "/Sub Category",
            source = "android",
            user_id = AppUtils(this).user.id,
        )
        analytics.addLog(logRequest)

        val customAppData = AppUtils(this).appData
        parentId = intent.getStringExtra("parent_id")!!

        // Log.e("subcategory", "inside subcategory and parent id is $parentId")

        viewModel = getViewModel(ProductViewModel::class.java)

        toolbar.setNavigationOnClickListener {
            finish()
        }
        title = "" + intent.getStringExtra("name")
        val itemDecoration = ItemOffsetDecoration(this, R.dimen.input_label_horizontal_spacing)
        rvSubCategory.addItemDecoration(itemDecoration)
        getSubcategories()
    }


    private fun getSubcategories() {

        //Toast.makeText(this, "inside getSuCategories function", Toast.LENGTH_SHORT).show()

        val filter = ProductCategoryFilter()
        filter.setPer_page(100)
        viewModel.subCategories(parentId).observe(this) { response ->
                when (response!!.status()) {
                    Status.LOADING -> {
                        showLoading()
                    }

                    Status.SUCCESS -> {
                        stopShowingLoading()
                        val layoutManager = GridLayoutManager(baseContext, 2)
                        rvSubCategory.layoutManager = layoutManager
                        rvSubCategory.isNestedScrollingEnabled = false

                        adapter = SubcategoryAdapter(response.data().result)
                        rvSubCategory.adapter = adapter
                    }

                    Status.ERROR -> {
                        Log.e("subcategory", "error")
                        //stopShowingLoading()
                    }

                    Status.EMPTY -> {
                        Log.e("subcategory", "empty")
                      //  stopShowingLoading()
                    }
                }
        }

    }

    fun showLoading(title: String, message: String) {
        val manager = supportFragmentManager
        progressDialog = ProgressDialogFragment.newInstance(title, message)
        progressDialog.isCancelable = false
        progressDialog.show(manager, "progress")
    }

    fun getScreenWidth(): Int {
        return Resources.getSystem().getDisplayMetrics().widthPixels
    }

    fun showLoading() {
        showLoading("This will only take a sec", "Loading")
    }

    fun stopShowingLoading() {
        progressDialog.dismiss()
    }
}
