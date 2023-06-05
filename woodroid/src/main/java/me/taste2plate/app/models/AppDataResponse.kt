package me.taste2plate.app.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import me.taste2plate.app.models.settings.AppSettings


@Keep
data class AppDataResponse (
    val status: String,
    val result: Result,
    val message: String
) 

@Keep
data class Result (
    @SerializedName("contact_email")
    val contactEmail: String,

    @SerializedName("contact_phone")
    val contactPhone: String,

    val whatsapp: String,
    val address: String,

    @SerializedName("about_us")
    val aboutUs: String,

    val terms: String,
    val privacy: String,

    @SerializedName("customer_android_version")
    val customerAndroidVersion: String,

    @SerializedName("customer_ios_version")
    val customerIosVersion: String,

    val dashboard: List<Dashboard>,

    @SerializedName("logistic_android_version")
    val logisticAndroidVersion: String,

    @SerializedName("logistic_ios_version")
    val logisticIosVersion: String,

    @SerializedName("vendor_android_version")
    val vendorAndroidVersion: String,

    @SerializedName("vendor_ios_version")
    val vendorIosVersion: String,

    @SerializedName("minimum_order_value")
    val minimumOrderValue: String,

    @SerializedName("maximum_order_value_cod")
    val maximumOrderValueCod: String,

    @SerializedName("product_not_available_message")
    val productNotAvailableMessage: String,

    @SerializedName("app_settings")
    val appSettings: AppSettings,
    
    @SerializedName("point")
    val pointSetting: PointSetups?,
/*
    @SerializedName("info_popup")
    val infoPopup: InfoPopup?*/


)

@Keep
data class Dashboard (
    @SerializedName("background_image")
    val backgroundImage: String,
    val icon: String,
    val title: String
)

@Keep
data class InfoPopup (
    @SerializedName("info_popup_text")
    val info_popup_text: String,
    @SerializedName("info_popup_image")
    val info_popup_image: String,
    @SerializedName("info_on_off")
    val info_on_off: Int
)

@Keep
data class PointSetups(
    @SerializedName("settings1")
    val firstSetting:PointSetupSetting?,
    @SerializedName("settings2")
    val secondSetting:PointSetupSetting?,
    @SerializedName("settings3")
    val thirdSetting:PointSetupSetting?,
    @SerializedName("signup_bonus_reciver")
    val friendBonus:Int?,
    @SerializedName("signup_bonus_sender")
    val myBonus:Int?,
    @SerializedName("cod_digital_payment")
    val bonusDigitalCod:Int?,
    @SerializedName("review")
    val bonusReview:Int?,
)

fun Int?.nullOrZero():Boolean = this==null || this==0

fun Int.stringOrBlank() : String = if(this>0) toString() else ""

@Keep
data class PointSetupSetting(
    @SerializedName("min_order")
    val minOrder:Int,
    @SerializedName("max_order")
    val maxOrder:Int,
    @SerializedName("point")
    val point:Int)
