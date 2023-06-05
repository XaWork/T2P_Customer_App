package me.taste2plate.app.customer.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.taste2plate.app.customer.ui.membership.MembershipListViewModel
import me.taste2plate.app.customer.ui.membership.MyPlanViewModel
import me.taste2plate.app.customer.ui.rewards.RewardViewModel
import me.taste2plate.app.customer.ui.wallet.WalletViewModel
import me.taste2plate.app.customer.utils.ViewModelFactory
import me.taste2plate.app.customer.viewmodels.*
import me.taste2plate.app.customer.updated_flow.CheckoutViewModel as NewCheckoutViewModel


@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    internal abstract fun bindUserViewModel(viewModel: UserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    internal abstract fun bindProductViewModel(viewModel: ProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    internal abstract fun bindCategoryViewModel(viewModel: CategoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CustomerViewModel::class)
    internal abstract fun bindCustomerViewModel(viewModel: CustomerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel::class)
    internal abstract fun bindCartViewModel(viewModel: CartViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CheckoutViewModel::class)
    internal abstract fun bindCheckoutViewModel(viewModel: CheckoutViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderViewModel::class)
    internal abstract fun bindOrderViewModel(viewModel: OrderViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReviewViewModel::class)
    internal abstract fun bindReviewViewModel(viewModel: ReviewViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewCheckoutViewModel::class)
    internal abstract fun bindNewCheckoutViewModel(viewModel: NewCheckoutViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MembershipListViewModel::class)
    internal abstract fun bindMembershipListViewModel(viewModel: MembershipListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyPlanViewModel::class)
    internal abstract fun bindMyPlanViewModel(viewModel: MyPlanViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(RewardViewModel::class)
    internal abstract fun bindMyRewardViewModel(viewModel: RewardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WalletViewModel::class)
    internal abstract fun bindWalletViewModel(viewModel: WalletViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}
