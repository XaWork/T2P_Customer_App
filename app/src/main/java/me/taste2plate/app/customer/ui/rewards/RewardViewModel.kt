package me.taste2plate.app.customer.ui.rewards

import androidx.lifecycle.ViewModel
import me.taste2plate.app.customer.common.WooLiveData
import me.taste2plate.app.customer.repo.CustomRepository
import me.taste2plate.app.customer.repo.ProductRepository
import me.taste2plate.app.models.AppDataResponse
import me.taste2plate.app.models.CommonResponse
import me.taste2plate.app.models.membership.PlanResponse
import me.taste2plate.app.models.membership.myplan.MyPlanResponseWithPoints
import me.taste2plate.app.models.wallet.TransactionResponse
import javax.inject.Inject

class RewardViewModel @Inject constructor(val customRepository: CustomRepository): ViewModel() {

    fun getAppSettings(): WooLiveData<AppDataResponse> {
        return customRepository.fetchAppData()
    }
}