package me.taste2plate.app.repo.product

import me.taste2plate.app.data.api.ProductCategoryAPI
import me.taste2plate.app.models.Category
import me.taste2plate.app.models.SubCategories
import me.taste2plate.app.models.filters.ProductCategoryFilter
import me.taste2plate.app.models.home.HomePageModel
import me.taste2plate.app.models.home.HomePageResponse
import me.taste2plate.app.repo.WooRepository
import retrofit2.Call

class CategoryRepository(baseUrl: String, consumerKey: String, consumerSecret: String) :
    WooRepository(baseUrl, consumerKey, consumerSecret) {

    private val apiService: ProductCategoryAPI

    init {
        apiService = retrofit.create(ProductCategoryAPI::class.java)
    }

    fun create(category: Category): Call<Category> {
        return apiService.create(category)
    }


    fun category(id: Int): Call<Category> {
        return apiService.view(id)
    }

    fun categories(): Call<List<Category>> {
        return apiService.list()
    }

    fun categories(productCategoryFilter: ProductCategoryFilter): Call<Category> {
        return apiService.filter(productCategoryFilter.filters)
    }


    fun homePageData(cityId: String): Call<HomePageResponse> {
        return if (cityId.isNotEmpty()) {
            apiService.homeDataForCity(cityId)
        } else {
            apiService.homeData()
        }
    }

    fun getHomePage(): Call<HomePageModel>{
        return apiService.getHome()
    }


    fun subCategories(parentId:String): Call<SubCategories> {
        return apiService.subCategories(parentId)
    }

    fun update(id: Int, category: Category): Call<Category> {
        return apiService.update(id, category)
    }

    fun delete(id: Int): Call<Category> {
        return apiService.delete(id)
    }

    fun delete(id: Int, force: Boolean): Call<Category> {
        return apiService.delete(id, force)
    }


}
