package me.taste2plate.app.customer.ui.membership

import androidx.lifecycle.ViewModel
import me.taste2plate.app.customer.common.WooLiveData
import me.taste2plate.app.customer.repo.ProductRepository
import me.taste2plate.app.models.CommonResponse
import me.taste2plate.app.models.membership.PlanResponse
import javax.inject.Inject

class MembershipListViewModel @Inject constructor(val productRepository: ProductRepository): ViewModel() {

    fun getMemberShipPlans(cityId: String?): WooLiveData<PlanResponse> {
        return productRepository.getPlans(cityId)
    }

    fun assignPlan(planId: String, userId:String): WooLiveData<CommonResponse> {
        return productRepository.assignPlan(planId, userId)
    }
}