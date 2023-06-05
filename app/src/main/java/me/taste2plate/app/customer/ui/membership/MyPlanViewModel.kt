package me.taste2plate.app.customer.ui.membership

import androidx.lifecycle.ViewModel
import me.taste2plate.app.customer.common.WooLiveData
import me.taste2plate.app.customer.repo.ProductRepository
import me.taste2plate.app.models.CommonResponse
import me.taste2plate.app.models.membership.PlanResponse
import me.taste2plate.app.models.membership.myplan.MyPlanResponseWithPoints
import javax.inject.Inject

class MyPlanViewModel @Inject constructor(val productRepository: ProductRepository): ViewModel() {

    fun getMyPlan(userId: String): WooLiveData<MyPlanResponseWithPoints> {
        return productRepository.getMyPlan(userId)
    }
}