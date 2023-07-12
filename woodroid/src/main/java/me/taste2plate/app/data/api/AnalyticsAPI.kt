package me.taste2plate.app.data.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.Collections

class AnalyticsAPI {
    var token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjoiNjRhYmQzNDRmMmIzYWRmMjUxNWUxMDU0IiwicHJvamVjdCI6IjY0YWJkMzZkZjJiM2FkZjI1MTVlMTA1OSIsImlhdCI6MTY4ODk4MjYxNH0.-4KoXpnK-6Kx4SmaN3yZTSKF0V1q-0695XaF69K3QQM"

    fun addLog(logRequest: LogRequest) {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://analytics-api-weld.vercel.app/admin/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val ipAddress = getDeviceIpAddress()
        println("Device IP Address: $ipAddress")

        val call = apiService.addLog("add-log?log_category=64abd456600481c67e58853a", logRequest)
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

    fun getDeviceIpAddress(): String {
        try {
            val interfaces: List<NetworkInterface> = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (networkInterface in interfaces) {
                val addresses: List<InetAddress> = Collections.list(networkInterface.inetAddresses)
                for (address in addresses) {
                    if (!address.isLoopbackAddress) {
                        val ipAddress: String = address.hostAddress
                        val isIPv4 = ipAddress.indexOf(':') < 0
                        if (isIPv4) {
                            return ipAddress
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}


data class LogRequest(
    val token: String = AnalyticsAPI().token,
    val type: String,
    val event: String,
    val event_data: String = "",
    val geo_ip: String = AnalyticsAPI().getDeviceIpAddress(),
    val page_name: String,
    val source: String = "android",
    val user_id: String,
    val product_id: String = ""
)

interface ApiService {
    @POST
    fun addLog(@Url url: String, @Body request: LogRequest): Call<Void>
}