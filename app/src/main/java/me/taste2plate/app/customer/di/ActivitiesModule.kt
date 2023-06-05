package me.taste2plate.app.customer.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.taste2plate.app.customer.SplashActivity
import me.taste2plate.app.customer.ui.checkout.CheckoutActivity
import me.taste2plate.app.customer.ui.coupon.AllOffersActivity
import me.taste2plate.app.customer.ui.coupon.DealsNComboListActivity
import me.taste2plate.app.customer.ui.customer.*
import me.taste2plate.app.customer.ui.home.HomeActivity
import me.taste2plate.app.customer.ui.landing.DashboardActivity
import me.taste2plate.app.customer.ui.landing.DetailsActivity
import me.taste2plate.app.customer.ui.location.TrackLocationActivity
import me.taste2plate.app.customer.ui.membership.MembershipListActivity
import me.taste2plate.app.customer.ui.membership.MyPlanActivity
import me.taste2plate.app.customer.ui.offers.OffersActivity
import me.taste2plate.app.customer.ui.onboarding.AnonymousSignInActivity
import me.taste2plate.app.customer.ui.onboarding.OnBoardActivity
import me.taste2plate.app.customer.ui.order.MyOrdersActivity
import me.taste2plate.app.customer.ui.order.OrderActivity
import me.taste2plate.app.customer.ui.order.OrderConfirmationActivity
import me.taste2plate.app.customer.ui.product.*
import me.taste2plate.app.customer.ui.rewards.RewardActivity
import me.taste2plate.app.customer.ui.wallet.WalletActivity

@Module
internal abstract class ActivitiesModule {

    @ContributesAndroidInjector
    internal abstract fun contributesMainActivity(): SplashActivity

    @ContributesAndroidInjector
    internal abstract fun contributesDashboardActivity(): DashboardActivity

    @ContributesAndroidInjector
    internal abstract fun contributesDetailsActivity(): DetailsActivity

    @ContributesAndroidInjector
    internal abstract fun contributesShopActivity(): ShopActivity

    @ContributesAndroidInjector
    internal abstract fun contributesProductActivity(): ProductActivity

    @ContributesAndroidInjector
    internal abstract fun contributesHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    internal abstract fun contributesOnbaordActivity(): OnBoardActivity

    @ContributesAndroidInjector
    internal abstract fun contributesBasicCustomerDetailsActivity(): BasicCustomerDetailsActivity

    @ContributesAndroidInjector
    internal abstract fun contributesBillingAddressActivity(): BillingAddressActivity

    @ContributesAndroidInjector
    internal abstract fun contributesShippingAddressActivity(): ShippingAddressActivity

    @ContributesAndroidInjector
    internal abstract fun contributesCartActivity(): CartActivity

    @ContributesAndroidInjector
    internal abstract fun contributesWishlistActivity(): WishlistActivity

    @ContributesAndroidInjector
    internal abstract fun contributesSubCategoryActivity(): SubCategoryActivity

    @ContributesAndroidInjector
    internal abstract fun contributesCheckOutActivity(): CheckoutActivity

    @ContributesAndroidInjector
    internal abstract fun contributesProfileActivity(): ProfileActivity

    @ContributesAndroidInjector
    internal abstract fun contributesAnonymousSignInActivity(): AnonymousSignInActivity

    @ContributesAndroidInjector
    internal abstract fun contributesMyOrdersActivity(): MyOrdersActivity

    @ContributesAndroidInjector
    internal abstract fun contributesOrderActivity(): OrderActivity

    @ContributesAndroidInjector
    internal abstract fun contributesProductSearchActivity(): ProductSearchActivity

    @ContributesAndroidInjector
    internal abstract fun contributesCityBrandActivity(): CityBrandActivity

    @ContributesAndroidInjector
    internal abstract fun contributesCategoryActivity(): CategoryActivity

    @ContributesAndroidInjector
    internal abstract fun contributesBulkOrderActivity(): BulkOrderActivity

    @ContributesAndroidInjector
    internal abstract fun contributesDirectFromHomeActivity(): DirectFromHomeActivity

    @ContributesAndroidInjector
    internal abstract fun contributesAddressListActivity(): AddressListActivity

    @ContributesAndroidInjector
    internal abstract fun contributesAddNewAddressActivity(): AddNewAddressActivity

    @ContributesAndroidInjector
    internal abstract fun contributesTrackLocationActivity(): TrackLocationActivity

    @ContributesAndroidInjector
    internal abstract fun contributesAllOffersActivity(): AllOffersActivity

    @ContributesAndroidInjector
    internal abstract fun contributesDealsNComboListActivity(): DealsNComboListActivity

    @ContributesAndroidInjector
    internal abstract fun contributesMembershipListActivity(): MembershipListActivity

    @ContributesAndroidInjector
    internal abstract fun contributesWalletActivity(): WalletActivity

    @ContributesAndroidInjector
    internal abstract fun contributesMyPlanActivity(): MyPlanActivity

    @ContributesAndroidInjector
    internal abstract fun contributesRewardActivity(): RewardActivity

    @ContributesAndroidInjector
    internal abstract fun contributesCheckout(): me.taste2plate.app.customer.updated_flow.CheckoutActivity

    @ContributesAndroidInjector
    internal abstract fun contributesOrderConfirmation(): OrderConfirmationActivity

    @ContributesAndroidInjector
    internal abstract fun contributesOffersActivity(): OffersActivity

}
