package me.taste2plate.app.customer.utils

import com.google.gson.annotations.SerializedName

data class CustomAppResponse(

	@field:SerializedName("customdata")
	val customdata: Customdata? = null
)

data class CustomerSupportItem(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("mobile")
	val mobile: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class Customdata(

	@field:SerializedName("homeScreenText")
	val homeScreenText: String? = null,

	@field:SerializedName("service_popup_desc")
	val servicePopupDesc: String? = null,

	@field:SerializedName("headerBgColor")
	val headerBgColor: String? = null,

	@field:SerializedName("homeScreenColor")
	val homeScreenColor: String? = null,

	@field:SerializedName("cod_popup_desc")
	val codPopupDesc: String? = null,

	@field:SerializedName("homeScreenBgColor")
	val homeScreenBgColor: String? = null,

	@field:SerializedName("service_popup_subtitle")
	val servicePopupSubtitle: String? = null,

	@field:SerializedName("cod_popup_titletxt_color")
	val codPopupTitletxtColor: String? = null,

	@field:SerializedName("popup_subtitle")
	val popupSubtitle: String? = null,

	@field:SerializedName("order_track_popup_subtitle")
	val orderTrackPopupSubtitle: String? = null,

	@field:SerializedName("cancel_popup_bg_color")
	val cancelPopupBgColor: String? = null,

	@field:SerializedName("order_track_popup_subtitletxt_color")
	val orderTrackPopupSubtitletxtColor: String? = null,

	@field:SerializedName("cancel_popup_titletxt_color")
	val cancelPopupTitletxtColor: String? = null,

	@field:SerializedName("cancel_popup_subtitle")
	val cancelPopupSubtitle: String? = null,

	@field:SerializedName("cod_popup_desctxt_color")
	val codPopupDesctxtColor: String? = null,

	@field:SerializedName("cancel_popup_subtitletxt_color")
	val cancelPopupSubtitletxtColor: String? = null,

	@field:SerializedName("popup_desc")
	val popupDesc: String? = null,

	@field:SerializedName("service_popup_bg_color")
	val servicePopupBgColor: String? = null,

	@field:SerializedName("order_track_popup_desctxt_color")
	val orderTrackPopupDesctxtColor: String? = null,

	@field:SerializedName("cancel_popup_desctxt_color")
	val cancelPopupDesctxtColor: String? = null,

	@field:SerializedName("service_popup_title_color")
	val servicePopupTitleColor: String? = null,

	@field:SerializedName("order_track_popup_bg_color")
	val orderTrackPopupBgColor: String? = null,

	@field:SerializedName("customer_support")
	val customerSupport: List<CustomerSupportItem>? = null,

	@field:SerializedName("popup_bg_color")
	val popupBgColor: String? = null,

	@field:SerializedName("popup_title_color")
	val popupTitleColor: String? = null,

	@field:SerializedName("order_track_popup_titletxt_color")
	val orderTrackPopupTitletxtColor: String? = null,

	@field:SerializedName("cancel_popup_title")
	val cancelPopupTitle: String? = null,

	@field:SerializedName("cod_popup_bg_color")
	val codPopupBgColor: String? = null,

	@field:SerializedName("popup_title")
	val popupTitle: String? = null,

	@field:SerializedName("popup_subtitle_color")
	val popupSubtitleColor: String? = null,

	@field:SerializedName("popup_desc_color")
	val popupDescColor: String? = null,

	@field:SerializedName("order_track_popup_desc")
	val orderTrackPopupDesc: String? = null,

	@field:SerializedName("cod_popup_title")
	val codPopupTitle: String? = null,

	@field:SerializedName("service_popup_desc_color")
	val servicePopupDescColor: String? = null,

	@field:SerializedName("cod_popup_subtitle")
	val codPopupSubtitle: String? = null,

	@field:SerializedName("cod_popup_subtitletxt_color")
	val codPopupSubtitletxtColor: String? = null,

	@field:SerializedName("order_track_popup_title")
	val orderTrackPopupTitle: String? = null,

	@field:SerializedName("service_popup_subtitle_color")
	val servicePopupSubtitleColor: String? = null,

	@field:SerializedName("service_popup_title")
	val servicePopupTitle: String? = null,

	@field:SerializedName("cancel_popup_desc")
	val cancelPopupDesc: String? = null
)
