package me.taste2plate.app.customer.ui.coupon

import android.os.Bundle
import com.clevertap.android.sdk.CleverTapAPI
import me.taste2plate.app.customer.R
import kotlinx.android.synthetic.main.activity_add_coupon.*
import kotlinx.android.synthetic.main.content_add_coupon.*
import me.taste2plate.app.customer.ui.BaseActivity
import me.taste2plate.app.models.Coupon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCouponActivity : BaseActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_coupon)
        setSupportActionBar(toolbar)
        CleverTapAPI.getDefaultInstance(this)?.recordScreen("Add Coupon")

        title = "Add Coupon"

        bCreate.setOnClickListener { submit() }




    }

    private fun submit() {
        val code = etCode.text.toString()
        val description = etDescription.text.toString()
        
        val coupon = Coupon()
        coupon.code = code
        coupon.description = description
        
        createCoupon(coupon)
    }

    private fun createCoupon(coupon: Coupon) {
        showLoading("Loading", "This won't take long")

        woocommerce.CouponRepository().create(coupon).enqueue(object : Callback<Coupon> {
            override fun onResponse(call: Call<Coupon>, response: Response<Coupon>) {
                val couponResponse = response.body()
                stopShowingLoading()
                finish()
            }

            override fun onFailure(call: Call<Coupon>, t: Throwable) {
                stopShowingLoading()
            }
        })
    }

}
