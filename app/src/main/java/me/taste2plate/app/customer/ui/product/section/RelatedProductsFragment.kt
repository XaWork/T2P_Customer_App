package me.taste2plate.app.customer.ui.product.section

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.taste2plate.app.customer.R
import kotlinx.android.synthetic.main.section_related_products.*
import me.taste2plate.app.customer.adapter.HomeProductAdapter
import me.taste2plate.app.customer.common.BaseActivity
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.events.ProductEvent
import me.taste2plate.app.customer.viewmodels.ProductViewModel
import me.taste2plate.app.models.Product
import me.taste2plate.app.models.filters.ProductFilter
import me.taste2plate.app.models.home.ProductDeal
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


class RelatedProductsFragment : androidx.fragment.app.Fragment() {


    lateinit var viewModel: ProductViewModel
    val TAG = "RelatedProductFragment"

    lateinit var adapter: HomeProductAdapter
    private lateinit var products: ArrayList<ProductDeal>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.section_related_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as BaseActivity).getViewModel(ProductViewModel::class.java)

        //similarProducts()
    }

    private fun similarProducts(product: Product) {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            activity,
            androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
            false
        )
        rvShop.layoutManager = layoutManager
        rvShop.isNestedScrollingEnabled = false

        products = ArrayList()

        adapter = HomeProductAdapter(products, viewModel, viewLifecycleOwner)
        rvShop.adapter = adapter

        val filter = ProductFilter()
        filter.setInclude(product.related_ids.toIntArray())
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
    fun onMessageEvent(event: ProductEvent) {
        similarProducts(event.product)
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            RelatedProductsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

}
