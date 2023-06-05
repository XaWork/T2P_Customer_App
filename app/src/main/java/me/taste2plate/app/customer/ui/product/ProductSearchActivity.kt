package me.taste2plate.app.customer.ui.product

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import com.clevertap.android.sdk.CleverTapAPI
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.content_product_search.*
import kotlinx.android.synthetic.main.toolbar.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.ProductAdapter
import me.taste2plate.app.customer.common.BaseActivity
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.ui.state.ProgressDialogFragment
import me.taste2plate.app.customer.viewmodels.ProductViewModel
import me.taste2plate.app.models.newproducts.NewProduct
import java.util.concurrent.TimeUnit


class ProductSearchActivity : BaseActivity() {

    lateinit var adapter: ProductAdapter
    lateinit var products: ArrayList<NewProduct>
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var compositeDisposable: CompositeDisposable
    lateinit var viewModel: ProductViewModel
    val TAG = this::getLocalClassName

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        compositeDisposable = CompositeDisposable()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_search)
        CleverTapAPI.getDefaultInstance(this)?.recordScreen("Product Search")
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        viewModel = getViewModel(ProductViewModel::class.java)

        val layoutManager = androidx.recyclerview.widget.GridLayoutManager(baseContext, 2)
        rvProducts.layoutManager = layoutManager
        rvProducts.isNestedScrollingEnabled = false

        products = ArrayList()

        adapter = ProductAdapter(products, viewModel, this)

        rvProducts.adapter = adapter
    }

    private fun search(query: String, isForce: Boolean = false) {
        viewModel.productByQuery(query).observe(this, androidx.lifecycle.Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {
                    showLoading("Performing search", "This will only take a short while")
                }

                Status.SUCCESS -> {
                    stopShowingLoading()
                    products.clear()
                    val productsResponse = response.data()
                    for (product in productsResponse.result) {
                        products.add(product)
                    }
                    adapter.notifyDataSetChanged()
                }

                Status.ERROR -> {
                    stopShowingLoading()
                    Toast.makeText(baseContext, "Something went wrong!", Toast.LENGTH_LONG).show()
                }

                Status.EMPTY -> {
                    products.clear()
                    adapter.notifyDataSetChanged()
                    stopShowingLoading()
                }
            }

        })


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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.search_view, menu)
        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView =
            menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(
            searchManager.getSearchableInfo(componentName)
        )
        searchView.isIconified = false;
        // perform set on query text listener event

        compositeDisposable.add(
            Observable
                .create(ObservableOnSubscribe<String> { subscriber ->
                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextChange(newText: String?): Boolean {
                            subscriber.onNext(newText!!)
                            return false
                        }

                        override fun onQueryTextSubmit(query: String?): Boolean {
                            subscriber.onNext(query!!)
                            return false
                        }
                    })
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map { text -> text.toLowerCase().trim() }
                .debounce(250, TimeUnit.MILLISECONDS)
                .distinct()
                .filter { text -> text.isNotBlank() && text.length >= 3 }
                .subscribe({
                    Log.d("Demo", "subscriber: $it")
                    Handler(Looper.getMainLooper()).post { search(it) }
                }, {
                    it.printStackTrace()
                })
        )
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}