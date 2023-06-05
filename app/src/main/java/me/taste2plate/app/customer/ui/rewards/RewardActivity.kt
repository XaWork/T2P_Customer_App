package me.taste2plate.app.customer.ui.rewards

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.fueled.reclaim.ItemsViewAdapter
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.GeneralChoiceAdapterItem
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.databinding.ActivityWalletBinding
import me.taste2plate.app.customer.databinding.RewardActivityBinding
import me.taste2plate.app.customer.disableChangeAnimations
import me.taste2plate.app.customer.items.TransactionAdapterItem
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.models.nullOrZero
import me.taste2plate.app.models.stringOrBlank

class RewardActivity : WooDroidActivity<RewardViewModel>() {

    override lateinit var viewModel: RewardViewModel
    lateinit var binding: RewardActivityBinding
    private val itemsAdapter = ItemsViewAdapter()
    var userId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.reward_activity)
        viewModel = getViewModel(RewardViewModel::class.java)
        setSupportActionBar(binding.toolbarContainer.toolbar)
        title = getString(R.string.rewards)
        binding.toolbarContainer.toolbar.setNavigationOnClickListener {
            finish()
        }

        getRewardData()
    }

    private fun getRewardData() {
        viewModel.getAppSettings().observe(this) {
            when (it.status()) {
                Status.LOADING -> {
                    showLoading()
                    binding.dataView.visibility = View.GONE
                    binding.pointEarnContainer.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    stopShowingLoading()
                    Log.e("Data", it.data().result.pointSetting.toString())
                    if (it.data().result.pointSetting != null) {
                        binding.errorLayout.visibility = View.GONE
                        binding.dataView.visibility = View.VISIBLE
                        binding.pointEarnContainer.visibility = View.VISIBLE
                        val settings = it.data().result.pointSetting!!
                        binding.run {
                            settings.run {
                                if (firstSetting != null) {
                                    minS1.text = firstSetting!!.minOrder.stringOrBlank()
                                    maxS1.text = firstSetting!!.maxOrder.stringOrBlank()
                                    pointS1.text = firstSetting!!.point.stringOrBlank()
                                } else {
                                    minS1.text = "N/A"
                                    maxS1.text = "N/A"
                                    pointS1.text = "N/A"
                                }

                                if (secondSetting != null) {
                                    minS2.text = secondSetting!!.minOrder.stringOrBlank()
                                    maxS2.text = secondSetting!!.maxOrder.stringOrBlank()
                                    pointS2.text = secondSetting!!.point.stringOrBlank()
                                } else {
                                    minS2.text = "N/A"
                                    maxS2.text = "N/A"
                                    pointS2.text = "N/A"
                                }

                                if (thirdSetting != null) {
                                    minS3.text = thirdSetting!!.minOrder.stringOrBlank()
                                    maxS3.text = thirdSetting!!.maxOrder.stringOrBlank()
                                    pointS3.text = thirdSetting!!.point.stringOrBlank()
                                } else {
                                    minS3.text = "N/A"
                                    maxS3.text = "N/A"
                                    pointS3.text = "N/A"
                                }
                                if(friendBonus.nullOrZero() && myBonus.nullOrZero() && bonusDigitalCod.nullOrZero() && bonusReview.nullOrZero()){
                                    referralHeading.visibility = View.GONE
                                    referralInfo.visibility = View.GONE
                                    pointInfo.visibility = View.GONE
                                    reviewInfo.visibility = View.GONE
                                }else{
                                    if(!friendBonus.nullOrZero() && !myBonus.nullOrZero()){
                                        referralInfo.visibility = View.VISIBLE
                                        referralHeading.visibility = View.VISIBLE
                                        referralInfo.text = getString(R.string.point_refer_info, friendBonus, myBonus)
                                    }else{
                                        referralInfo.visibility = View.GONE
                                        referralHeading.visibility = View.GONE
                                    }

                                    if(!bonusDigitalCod.nullOrZero()){
                                        pointInfo.visibility = View.VISIBLE
                                        pointInfo.text = getString(R.string.point_cod_info, bonusDigitalCod)
                                    }else{
                                        pointInfo.visibility = View.GONE
                                    }

                                    if(!bonusReview.nullOrZero()){
                                        reviewInfo.visibility = View.VISIBLE
                                        reviewInfo.text = getString(R.string.point_earn_info, bonusReview)
                                    }else{
                                        reviewInfo.visibility = View.GONE
                                    }
                                }
                            }
                        }
                    } else {
                        binding.errorLayout.visibility = View.VISIBLE
                        binding.dataView.visibility = View.GONE
                        binding.pointEarnContainer.visibility = View.GONE
                    }
                }

                Status.ERROR, Status.EMPTY -> {
                    stopShowingLoading()
                    binding.errorLayout.visibility = View.VISIBLE
                    binding.dataView.visibility = View.GONE
                    binding.pointEarnContainer.visibility = View.GONE
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