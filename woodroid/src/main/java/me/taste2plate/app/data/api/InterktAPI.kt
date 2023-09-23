package me.taste2plate.app.data.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


class Interkt {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.interakt.ai/v1/public/track/")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: InterktAPI = retrofit.create(InterktAPI::class.java)
    fun userTrack(request: RequestBodyUserTrack) {
        val call = apiService.userTrack(request)
        call.enqueue(object : retrofit2.Callback<InterktUserTrackResponse> {
            override fun onResponse(
                call: Call<InterktUserTrackResponse>,
                response: retrofit2.Response<InterktUserTrackResponse>
            ) {
                if (response.isSuccessful) {
                    Log.e("interkt", "Success : $response")
                } else {
                    Log.e("interkt", "failed : $response")
                }
            }

            override fun onFailure(call: Call<InterktUserTrackResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }

    fun eventTrack(data:RequestBodyEventTrack) {
        val call = apiService.eventTrack(data)
        call.enqueue(object : retrofit2.Callback<InterktEventTrackResponse> {
            override fun onResponse(
                call: Call<InterktEventTrackResponse>,
                response: retrofit2.Response<InterktEventTrackResponse>
            ) {
                if (response.isSuccessful) {
                    Log.e("interkt", "Success : $response")
                } else {
                    Log.e("interkt", "failed : $response")
                }
            }

            override fun onFailure(call: Call<InterktEventTrackResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }
}

interface InterktAPI {

    @Headers(
        "Content-Type: application/json",
        "Authorization: Basic LTRlZk1kdjFOT05XcHVvQ1lIeDZMbGl5cGxCWjRiRlJ4VUFOUGNPYlRuazo="
    )
    @POST("users/")
    fun userTrack(
        @Body request: RequestBodyUserTrack
    ): Call<InterktUserTrackResponse>
    @Headers(
        "Content-Type: application/json",
        "Authorization: Basic LTRlZk1kdjFOT05XcHVvQ1lIeDZMbGl5cGxCWjRiRlJ4VUFOUGNPYlRuazo="
    )
    @POST("events/")
    fun eventTrack(
        @Body data: RequestBodyEventTrack
    ): Call<InterktEventTrackResponse>
}

data class InterktUserTrackResponse(
    val message: String,
    val result: Boolean
)

data class InterktEventTrackResponse(
    val message: String,
    val result: Boolean,
    val id: String
)

data class RequestBodyUserTrack(
    val userId: String,
    val phoneNumber: String,
    val countryCode: String,
    val traits: Map<String, String>,
)
data class RequestBodyEventTrack(
    val userId: String,
    val phoneNumber: String,
    val countryCode: String,
    val event: String,
    val traits: Map<String, Any>,
)
