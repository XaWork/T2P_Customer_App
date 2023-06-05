package me.taste2plate.app.customer.interfaces

interface OnApplyCouponListener {
    fun onApplyCoupon(couponId: String,couponName: String, couponAmount: Int)
}