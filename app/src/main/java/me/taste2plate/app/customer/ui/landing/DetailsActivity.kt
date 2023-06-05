package me.taste2plate.app.customer.ui.landing

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import android.widget.TextView.BufferType
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_dashboard_details.*
import kotlinx.android.synthetic.main.toolbar.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.ui.customer.DirectFromHomeActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.CustomerViewModel
import me.taste2plate.app.models.Details


class DetailsActivity() : WooDroidActivity<CustomerViewModel>() {

    override lateinit var viewModel: CustomerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_details)
        viewModel = getViewModel(CustomerViewModel::class.java)
    }
}