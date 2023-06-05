package me.taste2plate.app.customer.ui.wallet

import androidx.lifecycle.ViewModel
import me.taste2plate.app.customer.common.WooLiveData
import me.taste2plate.app.customer.repo.ProductRepository
import me.taste2plate.app.models.CommonResponse
import me.taste2plate.app.models.membership.PlanResponse
import me.taste2plate.app.models.membership.myplan.MyPlanResponseWithPoints
import me.taste2plate.app.models.wallet.TransactionResponse
import javax.inject.Inject

class WalletViewModel @Inject constructor(val productRepository: ProductRepository): ViewModel() {

    fun getWalletTransactions(userId: String): WooLiveData<TransactionResponse> {
        return productRepository.getWalletTransactions(userId)
    }

    fun getMyPlan(userId: String): WooLiveData<MyPlanResponseWithPoints> {
        return productRepository.getMyPlan(userId)
    }
}