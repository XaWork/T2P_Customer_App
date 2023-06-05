package me.taste2plate.app.customer.ui.onboarding

import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_onbaording.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.BaseActivity
import me.taste2plate.app.customer.ui.state.ProgressDialogFragment
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.utils.MySMSBroadcastReceiver

class OnBoardActivity : BaseActivity(), MySMSBroadcastReceiver.OTPReceiveListener {

    private val smsReceiver = MySMSBroadcastReceiver()

    private lateinit var progressDialog: ProgressDialogFragment
    private lateinit var screenSlidePagerAdapter: ScreenSlidePagerAdapter
    private var hash: String = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onbaording)
        screenSlidePagerAdapter = ScreenSlidePagerAdapter(this)
        view_pager2.adapter = screenSlidePagerAdapter
        tab_layout.isTabIndicatorFullWidth = false
        TabLayoutMediator(tab_layout, view_pager2) { tab, position ->
            tab.text = if (position == 0) getString(R.string.login) else getString(R.string.signup)
        }.attach()


        smsReceiver.initOTPListener(this)
        val intentFilter = IntentFilter()
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION)

        applicationContext.registerReceiver(smsReceiver, intentFilter)

        startSMSListener()
    }

    private inner class ScreenSlidePagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int = 2 //because I have two Fragments

        override fun createFragment(position: Int): Fragment = if (position == 0) {
            SignInFragment()
        } else {
            SignUpFragment()
        }
    }

    fun showLoading() {
        showLoading(getString(R.string.please_wait), getString(R.string.loading))
    }

    fun showLoading(title: String, message: String) {
        val manager = supportFragmentManager
        progressDialog = ProgressDialogFragment.newInstance(title, message)
        progressDialog.isCancelable = false
        progressDialog.show(manager, "progress")
    }

    fun stopShowingLoading() {
        progressDialog.dismiss()
    }

    fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }


    /**
     * Starts SmsRetriever, which waits for ONE matching SMS message until timeout
     * (5 minutes). The matching SMS message will be sent via a Broadcast Intent with
     * action SmsRetriever#SMS_RETRIEVED_ACTION.
     */
    private fun startSMSListener() {
        try {


            val client = SmsRetriever.getClient(this)


            val task = client.startSmsRetriever()
            task.addOnSuccessListener {


                // API successfully started
                Log.e("TAG", "addOnSuccessListener :")
                /*  smsReceiver = MySMSBroadcastReceiver()
                  smsReceiver.initOTPListener(this)
                  val intentFilter = IntentFilter()
                  intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
                  LocalBroadcastManager.getInstance(this)
                      .registerReceiver(smsReceiver, intentFilter)*/
            }

            task.addOnFailureListener {
                Log.e("TAG", "addOnFailureListener " + it.message)
                // Fail to start API
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    override fun onOTPReceived(otp: String) {
        AppUtils.showToast(this, "OTP Received: $otp", false)
        etOtp.setText(otp)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver)
    }

    override fun onOTPTimeOut() {
        //AppUtils.showToast(this, "OTP Time out", false)
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver)
        super.onDestroy()
    }


}