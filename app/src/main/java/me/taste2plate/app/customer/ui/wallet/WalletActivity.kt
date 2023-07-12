package me.taste2plate.app.customer.ui.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.clevertap.android.sdk.CleverTapAPI
import com.fueled.reclaim.ItemsViewAdapter
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.GeneralChoiceAdapterItem
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.databinding.ActivityWalletBinding
import me.taste2plate.app.customer.disableChangeAnimations
import me.taste2plate.app.customer.items.TransactionAdapterItem
import me.taste2plate.app.customer.toDate
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.ui.membership.MembershipListActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.data.api.AnalyticsAPI
import me.taste2plate.app.data.api.LogRequest

class WalletActivity : WooDroidActivity<WalletViewModel>() {

    override lateinit var viewModel: WalletViewModel
    lateinit var binding: ActivityWalletBinding
    val itemsAdapter = ItemsViewAdapter()
    var userId:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wallet)

        //send event info
        val analytics = AnalyticsAPI()
        val logRequest = LogRequest(
            type = "page visit",
            event = "Visit to wallet page",
            page_name = "/WalletActivity",
            source = "android",
            user_id = AppUtils(this).user.id,
        )
        analytics.addLog(logRequest)


        viewModel = getViewModel(WalletViewModel::class.java)
        CleverTapAPI.getDefaultInstance(this)?.recordScreen("Wallet")
        setSupportActionBar(binding.toolbarContainer.toolbar)
        title = getString(R.string.my_plan)
        binding.toolbarContainer.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.transactions.run {
            adapter = itemsAdapter
            disableChangeAnimations()
            addItemDecoration(
                DividerItemDecoration(
                    this@WalletActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        userId = AppUtils(this).user.id
        if(userId!=null) {
            getWalletData(userId!!)
            getWalletPoints(userId!!)
        }else{
            showError("Something went wrong!")
            finish()
        }

    }

    fun getWalletPoints(userId:String){
        viewModel.getMyPlan(userId).observe(this){
            when(it.status()){
                Status.LOADING -> {

                }
                Status.SUCCESS -> {

                    if(it.data().plan!=null){
                        binding.run{
                            walletMoney.text = it.data().walletBalance
                            walletPoint.text = it.data().customerPoint.toString()
                        }
                    }else{
                        binding.run{
                            walletMoney.text = "Error"
                            walletPoint.text = "Error"
                        }
                    }
                }
                Status.ERROR, Status.EMPTY -> {
                    stopShowingLoading()
                    binding.run{
                        walletMoney.text = "Error"
                        walletPoint.text = "Error"
                    }
                    Toast.makeText(
                        baseContext,
                        it.error().message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun getWalletData(userId: String) {
        viewModel.getWalletTransactions(userId).observe(this) { response ->
            when (response.status()) {
                Status.LOADING -> {
                    showLoading()
                }
                Status.SUCCESS -> {
                    stopShowingLoading()
                    if (response.data().totalCount != 0) {
                        binding.errorLayout.visibility = View.GONE
                        val items = buildList {
                            add(GeneralChoiceAdapterItem("Wallet Transactions", true) {})
                            addAll(
                                response.data().result.map {
                                    TransactionAdapterItem(it)
                                }.toMutableList()
                            )
                        }
                        itemsAdapter.replaceItems(items, true)
                    } else {
                        binding.errorLayout.visibility = View.VISIBLE
                    }
                    Log.e("data", response.data().toString())
                }
                Status.ERROR, Status.EMPTY -> {
                    stopShowingLoading()
                    binding.errorLayout.visibility = View.VISIBLE
                    Toast.makeText(
                        baseContext,
                        response.error().message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}