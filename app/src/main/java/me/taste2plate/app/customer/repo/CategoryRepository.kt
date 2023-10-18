package me.taste2plate.app.customer.repo


import me.taste2plate.app.Woocommerce
import me.taste2plate.app.customer.common.WooLiveData
import me.taste2plate.app.models.Category
import me.taste2plate.app.models.SubCategories
import me.taste2plate.app.models.filters.ProductCategoryFilter
import me.taste2plate.app.models.home.HomePageModel
import me.taste2plate.app.models.home.HomePageResponse
import javax.inject.Inject

class CategoryRepository @Inject
constructor() {

    @Inject
    lateinit var woocommerce: Woocommerce


    fun create(category: Category): WooLiveData<Category> {
        val callBack = WooLiveData<Category>()

        woocommerce.CategoryRepository().create(category).enqueue(callBack)
        return callBack
    }


    fun category(id: Int): WooLiveData<Category> {
        val callBack = WooLiveData<Category>()

        woocommerce.CategoryRepository().category(id).enqueue(callBack)
        return callBack
    }

    fun categories(taste: String): WooLiveData<List<Category>> {
        val callBack = WooLiveData<List<Category>>()

        woocommerce.CategoryRepository().categories(taste).enqueue(callBack)
        return callBack
    }

    fun categories(productCategoryFilter: ProductCategoryFilter, taste: String): WooLiveData<Category> {
        val callBack = WooLiveData<Category>()

        woocommerce.CategoryRepository().categories(productCategoryFilter, taste).enqueue(callBack)
        return callBack
    }


    fun homePageData(cityId:String, taste: String): WooLiveData<HomePageResponse> {
        val callBack = WooLiveData<HomePageResponse>()
        woocommerce.CategoryRepository().homePageData(cityId, taste).enqueue(callBack)
        return callBack
    }


    fun getHomePage(): WooLiveData<HomePageModel> {
        val callBack = WooLiveData<HomePageModel>()
        woocommerce.CategoryRepository().getHomePage().enqueue(callBack)
        return callBack
    }


    fun subCategories(parentId:String): WooLiveData<SubCategories> {
        val callBack = WooLiveData<SubCategories>()

        woocommerce.CategoryRepository().subCategories(parentId).enqueue(callBack)
        return callBack
    }

    fun update(id: Int, category: Category): WooLiveData<Category> {
        val callBack = WooLiveData<Category>()

        woocommerce.CategoryRepository().update(id, category).enqueue(callBack)
        return callBack
    }

    fun delete(id: Int): WooLiveData<Category> {
        val callBack = WooLiveData<Category>()

        woocommerce.CategoryRepository().delete(id).enqueue(callBack)
        return callBack
    }

    fun delete(id: Int, force: Boolean): WooLiveData<Category> {
        val callBack = WooLiveData<Category>()

        woocommerce.CategoryRepository().delete(id, force).enqueue(callBack)
        return callBack
    }


}
