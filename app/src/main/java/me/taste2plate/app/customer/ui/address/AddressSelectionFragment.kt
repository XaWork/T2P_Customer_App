package me.taste2plate.app.customer.ui.address

import android.app.Activity
import android.app.AppComponentFactory
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_new_address.*
import kotlinx.android.synthetic.main.item_loading.*
import me.taste2plate.app.customer.adapter.AddNewAddressAdapter
import me.taste2plate.app.customer.common.BaseActivity
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.databinding.LocationSelectionFragmentBinding
import me.taste2plate.app.customer.interfaces.OnSelectListener
import me.taste2plate.app.customer.ui.RoundedBottomSheetDialogFragment
import me.taste2plate.app.customer.ui.customer.AddNewAddressActivity
import me.taste2plate.app.customer.ui.home.HomeActivity
import me.taste2plate.app.customer.ui.product.ProductActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.ProductViewModel
import me.taste2plate.app.models.address.Address

class AddressSelectionFragment() : RoundedBottomSheetDialogFragment(), OnSelectListener {

    lateinit var userId:String
    lateinit var viewModel:ProductViewModel
    private lateinit var bindingModel:LocationSelectionFragmentBinding
    private lateinit var addressListAdapter: AddNewAddressAdapter
    private var addressList: ArrayList<Address> = ArrayList()
    lateinit var saveListener: SaveAddressListener

    fun saveSetSaveListener(saveAddressListener: SaveAddressListener){
        saveListener = saveAddressListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingModel = LocationSelectionFragmentBinding.inflate(inflater, container, false)
        return bindingModel.root
    }

    override fun onAttach(context: Context) {
        viewModel = (activity as BaseActivity).getViewModel(ProductViewModel::class.java)
        super.onAttach(context)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog as BottomSheetDialog
        val behavior: BottomSheetBehavior<*> = dialog.behavior
        behavior.state = BottomSheetBehavior.STATE_EXPANDED;
        behavior.peekHeight = 0;
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addressListAdapter = AddNewAddressAdapter(addressList, this, "sheet")
        bindingModel.addressList.adapter = addressListAdapter
        bindingModel.addressList.layoutManager = LinearLayoutManager(
            context,
            RecyclerView.VERTICAL,
            false
        )
        userId = AppUtils(context).user.id
        viewModel.fetchUserAddress(userId).observe(this, Observer {
            when (it.status()) {
                Status.SUCCESS -> {
                    bindingModel.loader.visibility = View.GONE
                    if (it.data().result.isNotEmpty()) {
                        addressList.clear()
                        addressList.addAll(it.data().result)

                        addressListAdapter.notifyDataSetChanged()
                        bindingModel.emptyHint.visibility = View.GONE

                    } else {
                        bindingModel.emptyHint.visibility = View.VISIBLE
                    }
                }
                Status.LOADING -> {
                    bindingModel.loader.visibility = View.VISIBLE
                }
                else -> {
                    bindingModel.loader.visibility = View.VISIBLE
                    dismiss()
                    Toast.makeText(context, "Something went wrong!", Toast.LENGTH_LONG).show()
                }
            }
        })

        bindingModel.newAddress.setOnClickListener {
            dismiss()
            startActivity(
                Intent(context, AddNewAddressActivity::class.java)
            )
        }
    }

    override fun onSelectItem(position: Int, type: String) {
        AppUtils(context).saveDefaultAddress(addressList[position])
        saveListener.onSaved()
        dismiss()
    }
}

interface SaveAddressListener{
    fun onSaved()
}