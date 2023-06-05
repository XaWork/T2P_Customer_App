package me.taste2plate.app.customer.ui.membership

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.databinding.ActivityMyPlanBinding
import me.taste2plate.app.customer.toDate
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.utils.AppUtils

class MyPlanActivity :  WooDroidActivity<MyPlanViewModel>(){

    override lateinit var viewModel: MyPlanViewModel
    private lateinit var binding: ActivityMyPlanBinding
    var userId:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(MyPlanViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_plan)

        setSupportActionBar(binding.toolbarContainer.toolbar)
        title = getString(R.string.my_plan)
        binding.toolbarContainer.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.planContainer.visibility = View.GONE
        userId = AppUtils(this).user.id
        if(userId!=null) {
            getMyPlanDetails(userId!!)
        }else{
            showError("Something went wrong!")
            finish()
        }
    }

    private fun getMyPlanDetails(userId:String){
        viewModel.getMyPlan(userId).observe(this){
            when(it.status()){
                Status.LOADING -> {
                    showLoading()
                }
                Status.SUCCESS -> {
                    stopShowingLoading()
                    if(it.data().plan!=null && it.data().plan!!.planName.isNotEmpty()){
                        binding.errorLayout.visibility = View.GONE
                        binding.planContainer.visibility = View.VISIBLE
                        binding.run {
                            usageText.text = it.data().customerPoint.toString()
                            expiryDate.text = it.data().plan!!.expDate.toDate("MMMM dd, yyyy")
                            benefitsContent.text = it.data().plan!!.benefits
                            planName.text = it.data().plan!!.planName
                            planPrice.text = it.data().plan!!.itemPrice
                            upgrade.setOnClickListener{
                                finish()
                                startActivity(Intent(this@MyPlanActivity, MembershipListActivity::class.java))
                            }
                        }
                    }else{
                        binding.errorLayout.visibility = View.VISIBLE
                        binding.planContainer.visibility = View.GONE
                    }
                }
                Status.ERROR, Status.EMPTY -> {
                    stopShowingLoading()
                    binding.errorLayout.visibility = View.VISIBLE
                    binding.planContainer.visibility = View.GONE
                    Toast.makeText(
                        baseContext,
                        it.error().message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
