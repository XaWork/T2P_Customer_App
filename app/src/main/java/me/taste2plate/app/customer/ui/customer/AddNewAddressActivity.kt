package me.taste2plate.app.customer.ui.customer

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.clevertap.android.sdk.CleverTapAPI
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_new_address.*
import kotlinx.android.synthetic.main.section_add_a_review.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.LocationPickerActivity
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.databinding.ActivityNewAddressBinding
import me.taste2plate.app.customer.hideSoftKeyboard
import me.taste2plate.app.customer.setupDropDown
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.CustomerViewModel
import me.taste2plate.app.models.address.Address
import me.taste2plate.app.models.address.cities.CityListResult
import me.taste2plate.app.models.address.states.AllStateResponse
import kotlin.system.exitProcess


class AddNewAddressActivity : WooDroidActivity<CustomerViewModel>() {

    override lateinit var viewModel: CustomerViewModel
    lateinit var binding: ActivityNewAddressBinding
    private lateinit var stateResponse: AllStateResponse
    lateinit var cites: List<CityListResult>
    var selectedStateId: String? = null
    var selectedCityId: String? = null
    var lat: Double = 0.toDouble()
    var long: Double = 0.toDouble()
    var selectedAddressType: String? = null
    var address: Address? = null;
    val pinList = mutableListOf<String>()
    var validPin = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tool)
        CleverTapAPI.getDefaultInstance(this)?.recordScreen("Add new address")
        binding.tool.setNavigationOnClickListener {
            finish()
        }
        viewModel = getViewModel(CustomerViewModel::class.java)

        binding.addressType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.home -> {
                    binding.tilOtherText.visibility = View.GONE
                    selectedAddressType = "home"
                }

                R.id.work -> {
                    selectedAddressType = "work"
                    binding.tilOtherText.visibility = View.GONE
                }

                else -> {
                    selectedAddressType = null
                    binding.tilOtherText.visibility = View.VISIBLE
                }
            }
        }

        binding.addressType.check(R.id.home)
        binding.save.setOnClickListener {
            binding.run {
                when {
                    name.text.toString().trim().isEmpty() -> {
                        showError("Enter name!")
                    }

                    etPhone.text.toString().trim().isEmpty() -> {
                        showError("Enter phone number!")
                    }

                    etAddress1.text.toString().trim().isEmpty() -> {
                        showError("Enter address")
                    }

                    etState.text.toString().trim().isEmpty() -> {
                        showError("Enter state")
                    }

                    etPin.text.toString().trim().isEmpty() || !validPin && !pinList.contains(etPin.text.toString().trim())-> {
                        showError("Enter valid pin code")
                    }

                    etCity.text.toString().trim().isEmpty() -> {
                        showError("Select City")
                    }
                    /*etpostOffice.text.toString().trim().isEmpty() -> {
                        showError("Enter landmark")
                    }*/
                    binding.tilOtherText.visibility == View.VISIBLE && binding.otherAddress.text.toString()
                        .trim().isEmpty() -> {
                        showError("Enter address type, or select an option!")
                    }

                    (lat == 0.toDouble() || long == 0.toDouble()) -> {
                        startActivityForResult(
                            Intent(
                                this@AddNewAddressActivity,
                                LocationPickerActivity::class.java
                            ), 23
                        )
                    }

                    else -> {
                        saveAddress()
                    }
                }
            }
        }


        binding.run {
            etCountry.setupDropDown(
                listOf("India").toTypedArray(),
                { it },
                {
                    etCountry.setText(it)
                }, {
                    it.show()
                    hideSoftKeyboard()
                }
            )
        }

        address = intent.getSerializableExtra("Address") as Address?
        if (address != null) {
            binding.run {
                validPin = true
                name.setText(address!!.contact_name)
                etPhone.setText(address!!.contact_mobile)
                etAddress1.setText(address!!.address)
                etState.setText(address!!.state.name)
                etAddress2.setText(address!!.address2)
                etPin.setText(address!!.pincode)
                Log.e("landmark", "landmark get : ${address!!.landmark}")
                etpostOffice.setText(address!!.landmark)
                etCountry.setText("India")
                etCity.setText(address!!.city.name)
                selectedCityId = address!!.city._id
                selectedStateId = address!!.state._id


                when {
                    address!!.title.equals("Home", ignoreCase = true) -> {
                        home.isChecked = true
                    }

                    address!!.title.equals("Work", ignoreCase = true) -> {
                        work.isChecked = true
                    }

                    else -> {
                        other.isChecked = true
                        binding.tilOtherText.editText!!.setText(address!!.title)
                    }
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 23 && resultCode == RESULT_OK) {
            lat = data!!.getDoubleExtra("lat", 0.toDouble())
            long = data.getDoubleExtra("long", 0.toDouble())
        }
    }

    private fun saveAddress() {
        if (address != null) {
            binding.run {
                viewModel.editAddress(
                    address!!._id,
                    name.text.toString(),
                    etPhone.text.toString(),
                    selectedCityId!!,
                    selectedStateId!!,
                    etPin.text.toString(),
                    etpostOffice.text.toString(),
                    etAddress1.text.toString(),
                    etAddress2.text.toString(),
                    lat,
                    long,
                    if (selectedAddressType != null) selectedAddressType else otherAddress.text.trim()
                        .toString()
                ).observe(this@AddNewAddressActivity, Observer {
                    when (it.status()) {
                        Status.SUCCESS -> {
                            stopShowingLoading()
                            showError("Address Saved!")
                            finish()
                        }

                        Status.LOADING -> {
                            showLoading()
                        }

                        else -> {
                            stopShowingLoading()
                            showError("Something went wrong! retry")
                        }
                    }
                })
            }
        } else {

            binding.run {
                viewModel.saveAddress(
                    AppUtils(this@AddNewAddressActivity).user.id,
                    name.text.toString(),
                    etPhone.text.toString(),
                    selectedCityId!!,
                    selectedStateId!!,
                    etPin.text.toString(),
                    etpostOffice.text.toString(),
                    etAddress1.text.toString(),
                    etAddress2.text.toString(),
                    lat,
                    long,
                    if (selectedAddressType != null) selectedAddressType else otherAddress.text.trim()
                        .toString()
                ).observe(this@AddNewAddressActivity, Observer {
                    when (it.status()) {
                        Status.SUCCESS -> {
                            stopShowingLoading()
                            showError("Address Saved!")
                            finish()
                        }

                        Status.LOADING -> {
                            showLoading()
                        }

                        else -> {
                            stopShowingLoading()
                            showError("Something went wrong! retry")
                        }
                    }
                })
            }
        }

    }

    override fun onResume() {
        super.onResume()
        getCityAndZip()
    }

    private fun getCityAndZip() {
        viewModel.allStates.observe(this) { response ->
            when (response.status()) {
                Status.SUCCESS -> {
                    stopShowingLoading()
                    if (response.data().result.isNullOrEmpty()) {
                        showError("No Data!")
                        finish()
                    } else {
                        //cityList.addAll(response.data().result)
                        stateResponse = response.data()
                        if (address != null) {
                            getCityByState()
                        }
                        setupOptions()
                    }
                }

                Status.LOADING -> {
                    showLoading()
                }

                else -> {
                    stopShowingLoading()
                    showError("Something went wrong!")
                    finish()
                }
            }
        }
    }

    private fun getZipList() {
        validPin = false
        viewModel.fetchZipList(selectedCityId).observe(this) { response ->
            when (response.status()) {
                Status.SUCCESS -> {
                    stopShowingLoading()
                    if (response.data().result.isNullOrEmpty()) {
                        showError("No available pin code, select another city/state")
                    }
                    binding.run {
                        pinList.addAll(response.data().result.map { it.name })
                        val arrayAdapter = ArrayAdapter(
                            this@AddNewAddressActivity,
                            android.R.layout.select_dialog_item,
                            pinList
                        )
                        etPin.setAdapter(arrayAdapter)
                        etPin.setOnItemClickListener { parent, view, position, id ->
                            validPin = true
                        }
                    }
                }

                Status.LOADING -> {
                    showLoading()
                }

                else -> {
                    stopShowingLoading()
                    showError("Something went wrong!")
                    finish()
                }
            }
        }
    }

    private fun getCityByState() {
        viewModel.fetchCityByState(selectedStateId).observe(this, Observer { response ->
            when (response.status()) {
                Status.SUCCESS -> {
                    stopShowingLoading()
                    cites = response.data().result
                    if(address!=null && selectedCityId != null){
                        getZipList()
                    }
                    selectedCityId = null
                    etCity.setupDropDown(cites.toTypedArray(), { it.name }, { city ->
                        etCity.setText(city.name)
                        etPin.setText("")
                        if (selectedCityId == null && selectedCityId != city.id) {
                            selectedCityId = city.id
                            getZipList()
                        }
                    }, {
                        if (cites.isNotEmpty()) {
                            it.show()
                        }
                    })
                }

                Status.LOADING -> {
                    showLoading()
                }

                else -> {
                    stopShowingLoading()
                    showError("Something went wrong!")
                    finish()
                }
            }
        })
    }

    private fun setupOptions() {
        binding.run {
            etState.setupDropDown(
                stateResponse.result.toTypedArray(), { it.name },
                { state ->
                    etCity.setText("")
                    etPin.setText("")
                    selectedStateId = state._id
                    etState.setText(state.name)
                    getCityByState()
                }, {
                    it.show()
                    hideSoftKeyboard()
                }
            )
        }
    }


}
