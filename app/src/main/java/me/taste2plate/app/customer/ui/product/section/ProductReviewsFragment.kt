package me.taste2plate.app.customer.ui.product.section

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import me.taste2plate.app.customer.R
import kotlinx.android.synthetic.main.section_product_reviews.*
import me.taste2plate.app.customer.adapter.ProductReviewAdapter
import me.taste2plate.app.customer.common.BaseActivity
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.events.ReviewEvent
import me.taste2plate.app.customer.ui.product.ProductActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.ReviewViewModel
import me.taste2plate.app.models.ProductReview
import me.taste2plate.app.models.cart.review.ReviewItem
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


class ProductReviewsFragment : Fragment() {

    lateinit var viewModel: ReviewViewModel
    val TAG = "ProductReviewsFragment"
    var productId = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.section_product_reviews, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as BaseActivity).getViewModel(ReviewViewModel::class.java)
        productId = (activity as ProductActivity).intent.getStringExtra("productId")!!
        reviews(productId)
        tvAddAReview.setOnClickListener{addAReviewDialog()}
    }

    private fun reviews(productId : String) {

        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            activity,
            androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
            false
        )
        rvReviews.layoutManager = layoutManager
        rvReviews.isNestedScrollingEnabled = false

        val reviews = ArrayList<ReviewItem>()

        val productReviewAdapter = ProductReviewAdapter(reviews)
        rvReviews.adapter = productReviewAdapter


        viewModel.productById(productId).observe(this, androidx.lifecycle.Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {

                }

                Status.SUCCESS -> {
                    reviews.clear()
                    val reviewsResponse = response.data().review
                    reviews.addAll(reviewsResponse)
                    productReviewAdapter.notifyDataSetChanged()
                }

                Status.ERROR -> {

                }

                Status.EMPTY -> {

                }
            }

        })

    }


    lateinit var addAReviewFragment: AddAReviewDialogFragment

    private fun addAReviewDialog() {
        val manager = childFragmentManager
        addAReviewFragment =
            AddAReviewDialogFragment.newInstance(productId)
        addAReviewFragment.isCancelable = true
        addAReviewFragment.show(manager, "add Review")
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
    fun onMessageEvent(event: ReviewEvent) {
        if(event.review.review!=null && event.review.name!=null) {
            save(event.review)
        }
    }

    private fun save(review: ProductReview) {
        val appData = AppUtils(context).user
        val userId = appData.id
        val userName = review.name
        val mail = appData.email
        val phone = appData.mobile
        viewModel.saveReview(userId, productId, review.review, userName,mail,phone, review.rating)    .observe(this, androidx.lifecycle.Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {

                }

                Status.SUCCESS -> {
                    val reviewsResponse = response.data()
                    reviews(productId)
                    Toast.makeText(context, "Review posted", Toast.LENGTH_LONG).show()
                }

                Status.ERROR -> {
                }

                Status.EMPTY -> {
                }
            }

        })
    }

}
