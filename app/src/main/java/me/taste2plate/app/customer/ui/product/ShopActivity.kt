package me.taste2plate.app.customer.ui.product

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.clevertap.android.sdk.CleverTapAPI
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_shop.*
import kotlinx.android.synthetic.main.content_shop.*
import kotlinx.android.synthetic.main.drawer_filter.*
import kotlinx.android.synthetic.main.toolbar.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.ProductAdapter
import me.taste2plate.app.customer.adapter.SliderProductAdapter
import me.taste2plate.app.customer.common.BaseActivity
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.ui.state.ProgressDialogFragment
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.utils.ItemOffsetDecoration
import me.taste2plate.app.customer.viewmodels.ProductViewModel
import me.taste2plate.app.models.Product
import me.taste2plate.app.models.CityBrandFilter
import me.taste2plate.app.models.filters.ProductFilter
import me.taste2plate.app.models.home.ProductDeal
import me.taste2plate.app.models.newproducts.NewProduct
import me.taste2plate.app.models.newproducts.NewProductResponse
import java.util.*


class ShopActivity : BaseActivity() {

    lateinit var adapter: ProductAdapter
    lateinit var sliderAdapter: SliderProductAdapter
    var products: ArrayList<NewProduct> = ArrayList()
    var sliderProducts: ArrayList<ProductDeal> = ArrayList()

    private lateinit var viewModel: ProductViewModel
    val TAG = this::getLocalClassName

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(me.taste2plate.app.customer.R.layout.activity_shop)
        setSupportActionBar(toolbar)
        CleverTapAPI.getDefaultInstance(this)?.recordScreen("Product List/Shop")
        val customAppData = AppUtils(this).appData

        toolbar.setNavigationOnClickListener {
            finish()
        }

        viewModel = getViewModel(ProductViewModel::class.java)

        title = "Shop"
        setUpPage()

        bFilter.setOnClickListener { filter() }
        val type = intent.getStringExtra("type")
        if (type != null && intent.getStringExtra("type")!!.contentEquals("category")) {
            Log.e("shop", "category")
            productsBySubcategory(intent.getStringExtra("categoryId")!!)
        } else if (type != null && intent.getStringExtra("type")!!.contentEquals("City")) {
            Log.e("shop", "city")
            productsByCity(intent.getStringExtra("id")!!)
        } else if (type != null && intent.getStringExtra("type")!!.contentEquals("slider")) {
            Log.e("shop", "slider")
            productsBySlider(intent.getStringExtra("name")!!)
        } else if (type != null && intent.getStringExtra("type")!!.contentEquals("Brand")) {
            Log.e("shop", "brand")
            productsByBrand(intent.getStringExtra("id")!!)
        } else if (type != null && intent.getStringExtra("type")!!
                .contentEquals("Flavours Of India")
        ) {
            Log.e("shop", "cuisine")
            productsByCuisine(intent.getStringExtra("id")!!)
        }
        title = intent.getStringExtra("name")
    }

    private fun setUpPage() {
        val itemDecoration = ItemOffsetDecoration(this, R.dimen.input_label_horizontal_spacing)
        rvShop.addItemDecoration(itemDecoration)
        val layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        rvShop.layoutManager = layoutManager
        rvShop.isNestedScrollingEnabled = false

        products = ArrayList()


        val type = intent.getStringExtra("type")
        adapter = ProductAdapter(products, viewModel, this)
        sliderAdapter = SliderProductAdapter(sliderProducts, viewModel, this)
        if (type != null && intent.getStringExtra("type")!!.contentEquals("slider")) {
            rvShop.adapter = sliderAdapter
        } else {
            rvShop.adapter = adapter
        }
    }

    private fun filter() {
        val filterMap = mutableMapOf<String, String>()
        val type = intent.getStringExtra("type")
        if (type != null && intent.getStringExtra("type")!!.contentEquals("category")) {
            filterMap["sub_category"] = intent.getStringExtra("categoryId")!!
        } else if (type != null && intent.getStringExtra("type")!!.contentEquals("City")) {
            filterMap["city"] = intent.getStringExtra("id")!!
        } else if (type != null && intent.getStringExtra("type")!!.contentEquals("slider")) {
            filterMap["slider"] = intent.getStringExtra("name")!!
        } else if (type != null && intent.getStringExtra("type")!!.contentEquals("Brand")) {
            filterMap["brand"] = intent.getStringExtra("id")!!
        } else if (type != null && intent.getStringExtra("type")!!
                .contentEquals("Flavours Of India")
        ) {
            filterMap["cuisine"] = intent.getStringExtra("id")!!
        }
        if (etSearch.text.toString().isNotEmpty()) {
            filterMap["search"] = etSearch.text.toString()
        }
        if (etMinPrice.text.toString().isNotEmpty()) {
            filterMap["price_start"] = etMinPrice.text.toString()
        }

        if (etMaxPrice.text.toString().isNotEmpty()) {
            filterMap["price_end"] = etMaxPrice.text.toString()
        }

        if (cbFeatured.isChecked) {
            filterMap["featured"] = "Y"
        }

        if (cbOnSale.isChecked) {
            filterMap["sale"] = "Y"
        }

        val selectedSortItem = sSort.selectedItem.toString()
        when {
            selectedSortItem.contentEquals("Popularity") -> {
                filterMap["sort"] = "popularity"
            }

            selectedSortItem.contentEquals("Date added") -> {
                filterMap["sort"] = "date_added"
            }

            selectedSortItem.contentEquals("Price lowest first") -> {
                filterMap["sort"] = "price_low_first"
            }

            else -> {
                filterMap["sort"] = "price_high_first"
            }
        }
        applyFilters(filterMap)
        toggleDrawer()
    }

    private fun applyFilters(filters: Map<String, String>) {
        viewModel.productByFilter(filters).observe(this, androidx.lifecycle.Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {
                    showLoading()
                    products.clear()
                    adapter.notifyDataSetChanged()
                }

                Status.SUCCESS -> {
                    stopShowingLoading()
                    if (response.data().result.isEmpty()) {
                        showNoProductFound()
                    } else {
                        products.clear()
                        Log.e("data", response.data().toString())
                        products.addAll(response.data().result)
                        adapter.notifyDataSetChanged()
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


    private fun productsBySubcategory(subcategoryId: String) {
        viewModel.productsBySubcategory(subcategoryId)
            .observe(this, androidx.lifecycle.Observer { response ->
                when (response!!.status()) {
                    Status.LOADING -> {
                        showLoading()
                    }

                    Status.SUCCESS -> {
                        stopShowingLoading()
                        if (response.data().result.isEmpty()) {
                            showNoProductFound()
                        } else {
                            products.clear()
                            Log.e("data", response.data().toString())
                            val productsResponse = response.data()
                            products.addAll(response.data().result)
                            adapter.notifyDataSetChanged()
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


    private fun productsByCity(cityId: String) {
        val filter = ProductFilter()
        filter.setPer_page(100)
        viewModel.productsByCity(cityId).observe(this, androidx.lifecycle.Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.SUCCESS -> {
                    stopShowingLoading()
                    if (response.data().result.isEmpty()) {
                        showNoProductFound()
                    } else {
                        products.clear()
                        Log.e("data", response.data().toString())
                        val productsResponse = response.data()
                        products.addAll(response.data().result)
                        adapter.notifyDataSetChanged()
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


    private fun productsByBrand(brandId: String) {
        val filter = ProductFilter()
        filter.setPer_page(100)
        viewModel.productsByBrand(brandId).observe(this, androidx.lifecycle.Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.SUCCESS -> {
                    stopShowingLoading()
                    if (response.data().result.isEmpty()) {
                        showNoProductFound()
                    } else {
                        products.clear()
                        Log.e("data", response.data().toString())
                        val productsResponse = response.data()
                        products.addAll(response.data().result)
                        adapter.notifyDataSetChanged()
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

    private fun productsBySlider(sliderName: String) {
        val filter = ProductFilter()
        filter.setPer_page(100)
        viewModel.productsBySlider(sliderName)
            .observe(this, androidx.lifecycle.Observer { response ->
                when (response!!.status()) {
                    Status.LOADING -> {
                        showLoading()
                    }

                    Status.SUCCESS -> {
                        stopShowingLoading()
                        if (response.data().result.isEmpty()) {
                            showNoProductFound()
                        } else {
                            products.clear()
                            Log.e("data", response.data().toString())
                            val productsResponse = response.data()
                            sliderProducts.addAll(response.data().result[0].products)
                            sliderAdapter.notifyDataSetChanged()
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

    private fun productsByCuisine(cuisineId: String) {
        Log.e("cuisine", cuisineId)
        val filter = ProductFilter()
        filter.setPer_page(100)
        viewModel.productsByCuisine(cuisineId)
            .observe(this, androidx.lifecycle.Observer { response ->
                when (response!!.status()) {
                    Status.LOADING -> {
                        showLoading()
                    }

                    Status.SUCCESS -> {
                        stopShowingLoading()
                        if (response.data().result.isEmpty()) {
                            showNoProductFound()
                        } else {
                            products.clear()
                            Log.e("data", response.data().toString())
                            val productsResponse = response.data()
                            products.addAll(response.data().result)
                            adapter.notifyDataSetChanged()
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

    private fun showNoProductFound() {
        noProductFound.visibility = View.VISIBLE
        val message = AppUtils(this).appData.result.productNotAvailableMessage
        noProductFound.text = message
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.products, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                toggleDrawer()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toast(text: String) {
        Toast.makeText(baseContext, text, Toast.LENGTH_LONG).show()
    }

    private fun toggleDrawer() {
        if (drawer_layout.isDrawerOpen(GravityCompat.END)) {
            drawer_layout.closeDrawer(GravityCompat.END)
        } else {
            drawer_layout.openDrawer(GravityCompat.END)
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
        showLoading("This will only take a sec", "Loading")
    }

    fun stopShowingLoading() {
        if (this::progressDialog.isInitialized) {
            progressDialog.dismiss()
        }
    }

}
