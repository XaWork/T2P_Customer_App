package me.taste2plate.app.data.api


import me.taste2plate.app.models.Category
import me.taste2plate.app.models.SubCategories
import me.taste2plate.app.models.home.HomePageModel
import me.taste2plate.app.models.home.HomePageResponse
import retrofit2.Call
import retrofit2.http.*

interface ProductCategoryAPI {

    @Headers("Content-Type: application/json")
    @POST("products/categories")
    fun create(@Body body: Category): Call<Category>

    @GET("products/categories/{id}")
    fun view(@Path("id") id: Int): Call<Category>

    @GET("products/categories")
    fun list(
        @Query("taste") taste: String
    ): Call<List<Category>>

    @Headers("Content-Type: application/json")
    @PUT("products/categories/{id}")
    fun update(@Path("id") id: Int, @Body body: Category): Call<Category>

    @DELETE("products/categories/{id}")
    fun delete(@Path("id") id: Int): Call<Category>

    @DELETE("products/categories/{id}")
    fun delete(@Path("id") id: Int, @Query("force") force: Boolean): Call<Category>

    @POST("products/categories/batch")
    fun batch(@Body body: Category): Call<String>

    @GET("app/all-parent-categories")
    fun filter(
        @QueryMap filter: Map<String, String>,
        @Query("taste") taste: String
    ): Call<Category>

    @GET("app/home")
    fun homeData(): Call<HomePageResponse>

    @GET("app/home")
    fun getHome(): Call<HomePageModel>

    @GET("app/home")
    fun homeDataForCity(
        @Query("city") cityId: String,
        @Query("taste") taste: String
    ): Call<HomePageResponse>

    @GET("app/all-sub-categories")
    fun subCategories(@Query("parent") parentId: String): Call<SubCategories>

}