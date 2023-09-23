package me.taste2plate.app.data.api

import android.util.Log
import me.taste2plate.app.models.LogCreatedResponse
import me.taste2plate.app.models.TrackerResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

class AnalyticsAPI {
    var token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjoiNjRhYmQzNDRmMmIzYWRmMjUxNWUxMDU0IiwicHJvamVjdCI6IjY0YWJkMzZkZjJiM2FkZjI1MTVlMTA1OSIsImlhdCI6MTY4ODk4MjYxNH0.-4KoXpnK-6Kx4SmaN3yZTSKF0V1q-0695XaF69K3QQM"
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    fun addLog(logRequest: LogRequest) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.trap2win.com/admin/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val call = apiService.addLog("add-log", logRequest)
        call.enqueue(object : retrofit2.Callback<Void> {
            override fun onResponse(call: Call<Void>, response: retrofit2.Response<Void>) {
                if (response.isSuccessful) {
                    Log.e("Analytics", "Success : $response")
                } else {
                    Log.e("Analytics", "failed : $response")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }
}


data class LogRequest(
    val category: String,
    val token: String,
    val type: String,
    val event: String,
    val event_data: String = "",
    val geo_ip: String = "",
    val page_name: String,
    val source: String = "android",
    val user_id: String,
    val product_id: String = "",
    val order_id: String = ""
)

data class IpAddressResponse(val ip: String)

interface ApiService {
    @POST
    fun addLog(@Url url: String, @Body request: LogRequest): Call<Void>

    @POST("add-log")
    fun addLog1(@Body request: LogRequest): Call<LogCreatedResponse>

    @POST("acquisition")
    fun install(
        @Query("tracker_record") tracker_record: String,
        @Query("click_id") clickId: String,
        @Query("security_token") security_token: String,
        @Query("gaid") gaid: String,
        @Query("sub4") sub4: String,
        @Query("type") type: String = "install",
    ): Call<TrackerResponse>

    @POST("acquisition")
    fun purchased(
        @Query("tracker_record") tracker_record: String,
        @Query("click_id") clickId: String,
        @Query("security_token") security_token: String,
        @Query("gaid") gaid: String,
        @Query("sub4") sub4: String,
        @Query("goal_name ") goal_name : String,
        @Query("sale_amount ") sale_amount : String,
        @Query("type") type: String = "checkout",
    ): Call<TrackerResponse>

    @GET("/?format=json")
    fun getIpAddress(): Call<IpAddressResponse>
}