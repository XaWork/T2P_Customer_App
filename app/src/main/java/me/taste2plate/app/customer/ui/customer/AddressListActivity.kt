package me.taste2plate.app.customer.ui.customer

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clevertap.android.sdk.CleverTapAPI
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.content_address.*
import kotlinx.android.synthetic.main.toolbar.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.AddNewAddressAdapter
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.interfaces.OnSelectListener
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.CustomerViewModel
import me.taste2plate.app.data.api.AnalyticsAPI
import me.taste2plate.app.data.api.LogRequest
import me.taste2plate.app.models.Customer
import me.taste2plate.app.models.UserAddress
import me.taste2plate.app.models.address.Address


class AddressListActivity : WooDroidActivity<CustomerViewModel>(), OnSelectListener {

    private val UPDATE_CODE: Int = 101
    private val ADD_NEW_CODE: Int = 100
    private var addressList: ArrayList<Address> = ArrayList()
    override lateinit var viewModel: CustomerViewModel
    var customer: Customer? = null
    var addressListAdapter: AddNewAddressAdapter? = null

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_list)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        //send event info
        val analytics = AnalyticsAPI()
        val appUtils = AppUtils(this)
        val logRequest = LogRequest(
            category = appUtils.referralInfo[0],
            token = appUtils.referralInfo[1],
            type = "page visit",
            event = "address list",
            event_data = "address list",
            page_name = "/AddressList",
            source = "android",
            user_id = AppUtils(this).user.id,
            geo_ip = AppUtils(this).ipAddress,
            product_id = ""
        )
        analytics.addLog(logRequest)

        CleverTapAPI.getDefaultInstance(this)?.recordScreen("Address List")

        viewModel = getViewModel(CustomerViewModel::class.java)

        title = getString(R.string.address_list)
        tvAddNewAddress.setOnClickListener { addressNewAddress() }
    }

    override fun onResume() {
        super.onResume()
        getAddressList()
    }

    private fun addressNewAddress() {
        startActivityForResult(Intent(this, AddNewAddressActivity::class.java), ADD_NEW_CODE)
    }

    private fun deleteAddress(addressId: String) {

        viewModel.deleteAddress(AppUtils(this).user.id, addressId)
            .observe(this, Observer { response ->
                when (response!!.status()) {
                    Status.LOADING -> {
                        showLoading()
                    }

                    Status.SUCCESS -> {
                        stopShowingLoading()
                        removeFromAddressList(addressId);
                        if (!addressList.isEmpty()) {
                            noAddressFound.visibility = GONE
                            rvAddressList.visibility = VISIBLE
                            setupAdapter()

                        } else {
                            noAddressFound.visibility = VISIBLE
                            rvAddressList.visibility = GONE
                        }

                    }

                    Status.ERROR -> {
                        stopShowingLoading()
                    }

                    Status.EMPTY -> {
                        stopShowingLoading()
                    }
                }
            })
    }

    private fun removeFromAddressList(addressId: String) {

        val iter: MutableListIterator<Address> = addressList.listIterator()
        while (iter.hasNext()) {
            if (iter.next()._id == addressId) {
                iter.remove()
            }
        }

    }

    private fun getAddressList() {
        viewModel.getAddressList(AppUtils(this).user.id).observe(this, Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.SUCCESS -> {
                    stopShowingLoading()

                    addressList.clear()
                    addressList.addAll(response.data().result)
                    if (addressList.isNotEmpty()) {
                        noAddressFound.visibility = GONE
                        rvAddressList.visibility = VISIBLE
                        setupAdapter()
                    } else {
                        noAddressFound.visibility = VISIBLE
                        rvAddressList.visibility = GONE
                    }
                }

                Status.ERROR -> {
                    stopShowingLoading()
                }

                Status.EMPTY -> {
                    stopShowingLoading()
                }
            }
        })
    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(
            baseContext,
            RecyclerView.VERTICAL,
            false
        )
        rvAddressList.layoutManager = layoutManager
        rvAddressList.isNestedScrollingEnabled = false

        addressListAdapter = AddNewAddressAdapter(addressList, this, intent.getStringExtra("From")!!)
        rvAddressList.adapter = addressListAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ((requestCode == ADD_NEW_CODE || requestCode == UPDATE_CODE) && resultCode == Activity.RESULT_OK) {
            getAddressList()
        }
    }

    override fun onSelectItem(position: Int, type: String) {
        val userAddress:Address = addressList[position]
        userAddress._id = addressList[position]._id
        when {
            type.contentEquals("Delete") -> {
                MaterialAlertDialogBuilder(this@AddressListActivity)
                    .setTitle("Delete Address")
                    .setMessage("Are you sure, you want to delete the address")
                    .setPositiveButton("Delete") { dialog, _ ->
                        deleteAddress(userAddress._id)
                        dialog.dismiss()
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
            type.contentEquals("Edit") -> {
                startActivityForResult(
                    Intent(
                        this,
                        AddNewAddressActivity::class.java
                    ).putExtra("Address", userAddress), UPDATE_CODE
                )
            }
        }
    }
}
