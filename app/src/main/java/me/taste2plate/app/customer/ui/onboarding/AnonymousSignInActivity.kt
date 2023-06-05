package me.taste2plate.app.customer.ui.onboarding

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.toolbar.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.ui.home.HomeActivity
import me.taste2plate.app.customer.ui.landing.DashboardActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.UserViewModel

class AnonymousSignInActivity : WooDroidActivity<UserViewModel>() {


    override lateinit var viewModel: UserViewModel

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anonymous_sign_in)

        setSupportActionBar(toolbar)
        viewModel = getViewModel(UserViewModel::class.java)
        anonymousSignIn()

    }

    private fun anonymousSignIn() {
        viewModel.anonymousSignIn().observe(this, Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {
                    showLoading(getString(R.string.account_setup_title), getString(R.string.account_setup_msg))
                }

                Status.SUCCESS -> {
                    stopShowingLoading()
                    startActivity(Intent(baseContext, DashboardActivity::class.java))

                }

                Status.ERROR -> {
                    stopShowingLoading()
                   // AlertDialog.Builder(this).
                    //Toast.makeText(baseContext, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
                }

                Status.EMPTY -> {
                    stopShowingLoading()
                }

            }
        })

    }
}