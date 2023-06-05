package me.taste2plate.app.customer.ui.coupon

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.clevertap.android.sdk.CleverTapAPI
import kotlinx.android.synthetic.main.activity_coupon.*
import kotlinx.android.synthetic.main.activity_coupon.toolbar
import kotlinx.android.synthetic.main.activity_coupons.*
import kotlinx.android.synthetic.main.toolbar.*
import me.taste2plate.app.customer.R

import me.taste2plate.app.customer.adapter.CouponAdapter
import me.taste2plate.app.customer.interfaces.OnApplyCouponListener
import me.taste2plate.app.customer.ui.BaseActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.models.CouponData
import me.taste2plate.app.models.CouponsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class CouponListActivity : BaseActivity(), OnApplyCouponListener {


    lateinit var adapter: CouponAdapter
    lateinit var coupons: ArrayList<CouponData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupons)
        setSupportActionBar(toolbar)
        CleverTapAPI.getDefaultInstance(this)?.recordScreen("Coupon List")


        val fromCheckout = intent.getBooleanExtra("IS_CHECKOUT", false)
        title = if (fromCheckout) "Select Coupon" else "All Coupons"

        setSupportActionBar(toolbar)
        val customAppData = AppUtils(this).appData


        toolbar.setNavigationOnClickListener {
            finish()
        }
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )
        rvCoupons.layoutManager = layoutManager
        rvCoupons.isNestedScrollingEnabled = false

        coupons = ArrayList()

        adapter = CouponAdapter(coupons, fromCheckout, this)
        rvCoupons.adapter = adapter

    }


    override fun onApplyCoupon(couponId: String, couponName: String,couponAmount: Int) {
        val intent = Intent()
        intent.putExtra("CouponId", couponId)
        intent.putExtra("CouponName", couponName)
        intent.putExtra("CouponDiscount", couponAmount)
        setResult(RESULT_OK, intent)
        finish()
    }

}
