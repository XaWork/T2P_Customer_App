package me.taste2plate.app.models

data class WalletData(
    val customer_point: Int, // 2727
    val plan: Plan,
    val point_settings: PointSettings
) {
    data class Plan(
        val cart_max_price: Int, // 0
        val cart_min_price: Int, // 0
        val discount_percentage: Int, // 0
        val exp_date: Any?, // null
        val plan_expired: Boolean, // true
        val plan_name: String,
        val plan_price: String
    )

    data class PointSettings(
        val max_redeem_point_per_order: String, // 1500
        val one_point_value_in_rupess: String, // 0.1
        val point_redeem_maximum_order_value: String, // 15000
        val point_redeem_minimum_order_value: String // 990
    )
}