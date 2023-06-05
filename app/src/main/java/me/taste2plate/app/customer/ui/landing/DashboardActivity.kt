package me.taste2plate.app.customer.ui.landing

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.toolbar.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.DashboardAdapter
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.customviews.LanguageChooseDialog
import me.taste2plate.app.customer.customviews.LocaleHelper
import me.taste2plate.app.customer.interfaces.OnSelectListener
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.ui.home.HomeActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.CustomerViewModel
import me.taste2plate.app.models.AppDataResponse
import me.taste2plate.app.models.DataItem

class DashboardActivity() : WooDroidActivity<CustomerViewModel>(), OnSelectListener,
    LanguageChooseDialog.LanguageChangeListener {

    private lateinit var mPref: AppUtils
    private lateinit var adapter: DashboardAdapter
    override lateinit var viewModel: CustomerViewModel
    lateinit var appData:AppDataResponse
    private lateinit var dashBoardItemList: List<DataItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        viewModel = getViewModel(CustomerViewModel::class.java)

        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(false);
            supportActionBar?.setHomeButtonEnabled(false);
        }
        appData = AppUtils(this).appData
        mPref = AppUtils(this)
        setDashboardAdapter()

        if (mPref.getString("LANG", null) == null) {
            mPref.putString("LANG", "en")
        }
        supportInvalidateOptionsMenu()

        ifd.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            finish()
            startActivity(intent)
        }

        mkk.setOnClickListener {
            showError(getString(R.string.coming_soon))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.dashbaord_menu, menu)

        val item = menu.findItem(R.id.menu_lang)
        item.title = mPref.getString("LANG", "en")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_lang -> {
                val languageChooseDialog =
                    LanguageChooseDialog(this)
                languageChooseDialog.setLanguageChangeListener(this)
                languageChooseDialog.show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setDashboardAdapter() {
        val dashboardData = appData.result.dashboard.take(2)
        ifd.visibility = View.VISIBLE
        mkk.visibility = View.VISIBLE
        Glide.with(this@DashboardActivity).load(dashboardData[0].backgroundImage).into(ifdImage)
        Glide.with(this@DashboardActivity).load(dashboardData[1].backgroundImage).into(mkkImage)
    }

    override fun onSelectItem(position: Int, type: String) {

        when (position) {
            0 -> {
                val intent = Intent(this, HomeActivity::class.java)
                finish()
                startActivity(intent)
            }
            1 -> {
                showError(getString(R.string.coming_soon))
                /* val intent = Intent(this, DetailsActivity::class.java)
                intent.putExtra("id", dashBoardItemList.get(position).id.toString())
                startActivity(intent) */
            }
            2 -> {
                showError(getString(R.string.coming_soon))
            }
            3 -> {
                showError(getString(R.string.coming_soon))
            }
        }

    }

    override fun onLanguageSelected(language: LanguageChooseDialog.Language) {
        Toast.makeText(this, java.lang.String.valueOf(language), Toast.LENGTH_LONG).show()
        if (language === LanguageChooseDialog.Language.HINDI) {
            mPref.putString("LANG", "HI")
            LocaleHelper.setLocale(this, "hi")
            recreate()
        } else if (language === LanguageChooseDialog.Language.ENGLISH) {
            mPref.putString("LANG", "EN")
            LocaleHelper.setLocale(this, "en")
            recreate()
        } else if (language === LanguageChooseDialog.Language.BENGALI) {
            mPref.putString("LANG", "BN")
            LocaleHelper.setLocale(this, "bn")
            recreate()
        } else if (language === LanguageChooseDialog.Language.PUNJABI) {
            mPref.putString("LANG", "PA")
            LocaleHelper.setLocale(this, "pa")
            recreate()
        } else if (language === LanguageChooseDialog.Language.MALAYALAM) {
            mPref.putString("LANG", "ML")
            LocaleHelper.setLocale(this, "ml")
            recreate()
        } else if (language === LanguageChooseDialog.Language.ORIYA) {
            mPref.putString("LANG", "OR")
            LocaleHelper.setLocale(this, "or")
            recreate()
        } else if (language === LanguageChooseDialog.Language.TAMIL) {
            mPref.putString("LANG", "TA")
            LocaleHelper.setLocale(this, "ta")
            recreate()
        } else if (language === LanguageChooseDialog.Language.TELUGU) {
            mPref.putString("LANG", "TE")
            LocaleHelper.setLocale(this, "te")
            recreate()
        }else if (language === LanguageChooseDialog.Language.MARATHI) {
            mPref.putString("LANG", "MR")
            LocaleHelper.setLocale(this, "mr")
            recreate()
        }
        supportInvalidateOptionsMenu()
    }


}