package me.taste2plate.app.customer.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import me.taste2plate.app.customer.R
import me.taste2plate.app.models.AppData
import me.taste2plate.app.models.AppDataResponse
import me.taste2plate.app.models.CustomAppData
import kotlin.math.floor

class Utils {

    companion object {
        const val COMPANION_SERVICE_CHECK = 1
        const val COMPANION_CANCEL = 2
        const val COMPANION_TRACK = 3
        const val COD_DIGITAL = 4
        const val EXPRESS_DELIVERY = 5
        const val SELECTED_ADDRESS = 6

        fun showDialog(
            context: Context,
            appData: AppDataResponse,
            popUpId: Int,
            onClickListener: View.OnClickListener
        ): Dialog {

            val customServiceDialog = Dialog(context)
            customServiceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            val view: View = View.inflate(context, R.layout.dialog_custom, null)
            val back = ColorDrawable(Color.TRANSPARENT);
            val inset = InsetDrawable(back, 10);
            customServiceDialog.window!!.setBackgroundDrawable(inset);

            customServiceDialog.setContentView(view)
            customServiceDialog.setCanceledOnTouchOutside(true)
            val okayClose = view.findViewById<Button>(R.id.okayClose)
            val title = view.findViewById<TextView>(R.id.tvTitle)
            val subTitle = view.findViewById<TextView>(R.id.tvSubTitle)
            val desc = view.findViewById<TextView>(R.id.tvDesc)
            val rbDigitalCOD = view.findViewById<TextView>(R.id.rbDigitalCOD)
            val llMainLayout = view.findViewById<LinearLayout>(R.id.llMainLayout)


            val pTitle: String?
            val pTitleColor: String?
            val pSubTitle: String?
            val pSubTitleColor: String?
            val pDesc: String?
            val pDescColor: String?
            val pBagColor: String?

            when (popUpId) {

                COMPANION_CANCEL -> {
                    pTitle = appData.result.appSettings.cancel.cancel_popup_title
                    pTitleColor = appData.result.appSettings.cancel.cancel_popup_title_color
                    pSubTitle = appData.result.appSettings.cancel.cancel_popup_subtitle
                    pSubTitleColor = appData.result.appSettings.cancel.cancel_popup_title_color
                    pDesc = appData.result.appSettings.cancel.cancel_popup_desctiption
                    pDescColor = appData.result.appSettings.cancel.cancel_subtitle_desctiption_color
                    pBagColor = appData.result.appSettings.cancel.cancel_popup_bg_color
                }
                COD_DIGITAL -> {
                    pTitle = appData.result.appSettings.cod.cod_popup_title
                    pTitleColor =  appData.result.appSettings.cod.cod_popup_title_color
                    pSubTitle =  appData.result.appSettings.cod.cod_popup_subtitle
                    pSubTitleColor = appData.result.appSettings.cod.cod_subtitle_title_color
                    pDesc = appData.result.appSettings.cod.cod_popup_desctiption
                    pDescColor = appData.result.appSettings.cod.cod_subtitle_desctiption_color
                    pBagColor = appData.result.appSettings.cod.cod_popup_bg_color
                    rbDigitalCOD.visibility=VISIBLE
                    desc.visibility= GONE
                    rbDigitalCOD.text = pDesc
                    rbDigitalCOD.setTextColor(Color.parseColor(pDescColor))
                }
                EXPRESS_DELIVERY -> {
                    pTitle = appData.result.appSettings.express.express_popup_title
                    pTitleColor = appData.result.appSettings.express.express_popup_title_color
                    pSubTitle =  appData.result.appSettings.express.express_popup_subtitle
                    pSubTitleColor = appData.result.appSettings.express.express_subtitle_title_color
                    pDesc = appData.result.appSettings.express.express_popup_desctiption
                    pDescColor = appData.result.appSettings.express.express_subtitle_desctiption_color
                    pBagColor = appData.result.appSettings.express.express_popup_bg_color
                    rbDigitalCOD.visibility=VISIBLE
                    desc.visibility= GONE
                    rbDigitalCOD.text = pDesc
                    rbDigitalCOD.setTextColor(Color.parseColor(pDescColor))
                }
                SELECTED_ADDRESS -> {
                    pTitle = "\"Select Delivery Location\""
                    pTitleColor = appData.result.appSettings.express.express_popup_title_color
                    pSubTitle =  "\'Delivery Location is Required to Proceed\'"
                    pSubTitleColor = appData.result.appSettings.express.express_subtitle_title_color
                    pDesc = "To continue using the app, please select or create an Address"
                    pDescColor = appData.result.appSettings.express.express_subtitle_desctiption_color
                    pBagColor = appData.result.appSettings.express.express_popup_bg_color
                    rbDigitalCOD.visibility=GONE
                    desc.visibility= VISIBLE

                    title.textSize = 18.sp.toFloat()
                }
                COMPANION_TRACK -> {
                    pTitle = appData.result.appSettings.order.order_track_popup_title
                    pTitleColor = appData.result.appSettings.order.order_track_popup_title_color
                    pSubTitle =  appData.result.appSettings.order.order_track_popup_subtitle
                    pSubTitleColor = appData.result.appSettings.order.order_track_subtitle_title_color
                    pDesc = appData.result.appSettings.order.order_track_popup_desctiption
                    pDescColor = appData.result.appSettings.order.order_track_subtitle_desctiption_color
                    pBagColor = appData.result.appSettings.order.order_track_popup_bg_color
                    rbDigitalCOD.visibility=GONE
                    desc.visibility= VISIBLE
                }
                else -> {
                    pTitle = appData.result.appSettings.express.express_popup_title
                    pTitleColor = appData.result.appSettings.express.express_popup_title_color
                    pSubTitle =  appData.result.appSettings.express.express_popup_subtitle
                    pSubTitleColor = appData.result.appSettings.express.express_subtitle_title_color
                    pDesc = appData.result.appSettings.express.express_popup_desctiption
                    pDescColor = appData.result.appSettings.express.express_subtitle_desctiption_color
                    pBagColor = appData.result.appSettings.express.express_popup_bg_color
                    rbDigitalCOD.visibility=VISIBLE
                    desc.visibility= GONE
                    rbDigitalCOD.text = pDesc
                    rbDigitalCOD.setTextColor(Color.parseColor(pDescColor))
                }
            }


            title.text = pTitle
            title.setTextColor(Color.parseColor(pTitleColor))
            subTitle.text = pSubTitle
            subTitle.setTextColor(Color.parseColor(pSubTitleColor))
            desc.text = pDesc
            desc.setTextColor(Color.parseColor(pDescColor))

            llMainLayout.setBackgroundColor(Color.parseColor(pBagColor))
            okayClose.setOnClickListener(onClickListener)
            return customServiceDialog
        }
    }


}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Int.sp: Int get() = if (this == 0) 0 else floor(AppConfig.fontDensity * this.toDouble()).toInt()

object AppConfig {

    var density = 1f
    var fontDensity = 1f

    fun onConfigChanged(context: Context, newConfiguration: Configuration?) {
        val configuration = newConfiguration ?: context.resources.configuration

        density = context.resources.displayMetrics.density
        fontDensity = context.resources.displayMetrics.scaledDensity
    }
}