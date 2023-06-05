package me.taste2plate.app.customer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clevertap.android.sdk.CleverTapAPI
import kotlinx.android.synthetic.main.fragment_category.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.CategoryAdapter
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.viewmodels.CategoryViewModel
import me.taste2plate.app.models.Category
import me.taste2plate.app.models.filters.ProductCategoryFilter
import java.util.*


class CategoryFragment : androidx.fragment.app.Fragment() {


    lateinit var viewModel: CategoryViewModel
    val TAG = "CategoryFragment"

    lateinit var adapter: CategoryAdapter
    lateinit var categories: ArrayList<Category.Result>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
        CleverTapAPI.getDefaultInstance(context)?.recordScreen("Category")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as HomeActivity).getViewModel(CategoryViewModel::class.java)

        val layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL,
            false
        )
        rvCategory.layoutManager = layoutManager
        rvCategory.isNestedScrollingEnabled = false

        categories = ArrayList()

        //adapter = CategoryAdapter(categories, categoryMap)
        rvCategory.adapter = adapter

        categories()

    }

    private fun categories() {

        val filter = ProductCategoryFilter()
        filter.setPer_page(50)

        viewModel.categories(filter).observe(this, androidx.lifecycle.Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {
                }

                Status.SUCCESS -> {
                    categories.clear()

                    val categoriesResponse = response.data()

                    for (category in categoriesResponse.result!!) {
                        if (category.name != "Uncategorized") {
                            categories.add(category)
                        }
                    }

                    adapter.notifyDataSetChanged()

                }

                Status.ERROR -> {


                }

                Status.EMPTY -> {

                }
            }

        })

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CategoryFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

}
