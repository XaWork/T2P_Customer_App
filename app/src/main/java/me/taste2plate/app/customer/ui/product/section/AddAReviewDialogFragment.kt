package me.taste2plate.app.customer.ui.product.section

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.section_add_a_review.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.events.ReviewEvent
import me.taste2plate.app.models.ProductReview
import org.greenrobot.eventbus.EventBus


class AddAReviewDialogFragment : DialogFragment() {

    var productId = ""

    private val ARG_PRODUCT_ID = "productId"

    init {
        val args = Bundle()
        args.putString(ARG_PRODUCT_ID, productId)
        arguments = args
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.section_add_a_review, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productId = arguments!!.getString(ARG_PRODUCT_ID, "")
        rbRating.onRatingBarChangeListener = OnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if (rating <= 0.5f) ratingBar.rating = 0.5f
        }
        llSave.setOnClickListener{save()}
    }

    private fun save() {
        val productReview = ProductReview()
        val review = etReview.text.toString()
        val rating = rbRating.rating
        productReview.product_id = productId
        productReview.rating = rating.toFloat()
        productReview.name = etName.text.toString()
        productReview.review = review
        EventBus.getDefault().post(ReviewEvent(productReview))
        dismiss()
    }

    companion object {
        fun newInstance(productId: String): AddAReviewDialogFragment {
            val fragment = AddAReviewDialogFragment()
            val args = Bundle()
            args.putString("productId", productId)
            fragment.arguments = args
            return fragment
        }
    }
}