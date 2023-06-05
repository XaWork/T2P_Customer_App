package me.taste2plate.app.customer.ui.membership

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.fueled.reclaim.ItemsViewAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.databinding.ActivityMembershipListBinding
import me.taste2plate.app.customer.disableChangeAnimations
import me.taste2plate.app.customer.items.PlanAdapterItem
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.models.membership.Plan
import org.json.JSONObject

class MembershipListActivity : WooDroidActivity<MembershipListViewModel>(), PaymentResultListener {

    override lateinit var viewModel: MembershipListViewModel
    private val itemsAdapter = ItemsViewAdapter()
    var selectedPlan:Plan? = null
    private lateinit var memberBinding: ActivityMembershipListBinding
    lateinit var userId:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        memberBinding = DataBindingUtil.setContentView(this, R.layout.activity_membership_list)
        viewModel = getViewModel(MembershipListViewModel::class.java)
        setSupportActionBar(memberBinding.toolbarContainer.toolbar)
        title = getString(R.string.plans_title)
        memberBinding.toolbarContainer.toolbar.setNavigationOnClickListener {
            finish()
        }
        memberBinding.plans.run {
            adapter = itemsAdapter
            disableChangeAnimations()
        }

        val cityId = AppUtils(this).defaultAddress?.city?._id
        userId = AppUtils(this).user.id

        if(cityId!=null) {
            getPlans(cityId)
        }else{
            showError("Select delivery location!")
            finish()
        }
    }

    fun  getPlans(cityId: String) {
        viewModel.getMemberShipPlans(cityId).observe(this){
            when(it.status()){
                Status.LOADING -> {
                    showLoading()
                }
                Status.SUCCESS -> {
                    stopShowingLoading()
                    if(!it.data().plans.isNullOrEmpty()) {
                        memberBinding.errorLayout.visibility = View.GONE
                        val items = it.data().plans.map { plan ->
                            PlanAdapterItem(plan) {
                                selectedPlan = plan
                                buyPlan(selectedPlan!!)
                            }
                        }
                        itemsAdapter.replaceItems(items, true)
                    }else{
                        memberBinding.errorLayout.visibility = View.VISIBLE
                    }
                }
                Status.ERROR, Status.EMPTY -> {
                    stopShowingLoading()
                    memberBinding.errorLayout.visibility = View.VISIBLE
                    Toast.makeText(
                        baseContext,
                        it.error().message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


    private fun buyPlan(planItem: Plan) {
        val co = Checkout()
        try {
            val options = JSONObject();
            options.put("name", "Taste2Plate")
            options.put("description", "Taste2Plate Order")
            options.put("currency", "INR")
            options.put("amount", (planItem.price * 100).toFloat())

            val preFill = JSONObject();
            val address = AppUtils(this).defaultAddress
            val user = AppUtils(this).user
            preFill.put("email", user.email)
            preFill.put("contact", address.contact_mobile)
            options.put("prefill", preFill)
            co.open(this, options);
        } catch (e: Exception) {
            Toast.makeText(this, "Error in payment: " + e.message, Toast.LENGTH_SHORT)
                .show();
            e.printStackTrace();
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        try{
            viewModel.assignPlan(selectedPlan!!.id, userId = userId).observe(this){
                when(it.status()){
                    Status.LOADING -> {
                        showLoading()
                    }
                    Status.SUCCESS -> {
                        stopShowingLoading()
                        MaterialAlertDialogBuilder(this)
                            .setTitle("Success")
                            .setCancelable(false)
                            .setMessage("Selected plan is assigned to you, enjoy the benefits!")
                            .setPositiveButton("Ok") { dialog, _ ->
                                dialog.dismiss()
                                finish()
                                startActivity(Intent(this@MembershipListActivity, MyPlanActivity::class.java))
                            }
                            .show()
                    }
                    Status.ERROR, Status.EMPTY -> {
                        stopShowingLoading()
                        memberBinding.errorLayout.visibility = View.VISIBLE
                        Toast.makeText(
                            baseContext,
                            "Something went wrong! Contact customer support if the amount is deducted!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        showError("Payment failed!")
    }

}