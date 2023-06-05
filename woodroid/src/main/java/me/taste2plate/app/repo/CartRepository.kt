package me.taste2plate.app.repo

import android.content.Context
import me.taste2plate.app.Constants
import me.taste2plate.app.data.api.CartAPI
import me.taste2plate.app.data.cookie.AddCookiesInterceptor
import me.taste2plate.app.data.cookie.ReceivedCookiesInterceptor
import me.taste2plate.app.models.LineItem
import me.taste2plate.app.models.filters.CartFilter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


class CartRepository(internal var baseUrl: String, consumerKey: String, consumerSecret: String) {

    internal var apiService: CartAPI
    internal var retrofit: Retrofit

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = getUnsafeOkHttpClient()

        retrofit = Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        apiService = retrofit.create(CartAPI::class.java)
    }


    fun getUnsafeOkHttpClient(): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY


        val x509TrustManager = object: X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }

        val trustManagers = arrayOf<TrustManager>(x509TrustManager)

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustManagers, null)

        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslContext.socketFactory, x509TrustManager)
        builder.hostnameVerifier { _, _ -> true }
        builder.addInterceptor(loggingInterceptor)
        builder.readTimeout(30, TimeUnit.SECONDS)
        builder.writeTimeout(30, TimeUnit.SECONDS)
        builder.connectTimeout(15, TimeUnit.SECONDS)
        return builder.build()
    }

    fun  turnOnCookies(context: Context) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(AddCookiesInterceptor(context))
            .addInterceptor(ReceivedCookiesInterceptor(context))
            .addInterceptor(loggingInterceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        apiService = retrofit.create(CartAPI::class.java)
    }

    fun clear(): Call<String> {
        return apiService.clear()
    }


    fun count(id: Int): Call<Int> {
        return apiService.count()
    }

    fun cart(): Call<Map<String, LineItem>> {
        return apiService.list()
    }

    fun addToCart(lineItem: LineItem): Call<Map<String, LineItem>> {
        return apiService.addToCart(lineItem)
    }

    fun delete(cardId: String): Call<String> {
        val cartFilter = CartFilter(cardId)
        return apiService.delete(cartFilter)
    }

    fun restore(cardId: String): Call<String> {
        val cartFilter = CartFilter(cardId)
        return apiService.restore(cartFilter)
    }

    fun update(cardId: String, quantity: Int): Call<String> {
        val cartFilter = CartFilter(cardId)
        cartFilter.quantity = quantity

        return apiService.update(cartFilter)
    }


}
