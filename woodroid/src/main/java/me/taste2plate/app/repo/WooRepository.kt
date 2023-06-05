package me.taste2plate.app.repo

import com.google.gson.GsonBuilder
import me.taste2plate.app.Constants
import me.taste2plate.app.data.auth.AuthIntercepter
import me.taste2plate.app.utils.TokenRenewInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate

import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

open class WooRepository(var baseUrl: String, var consumerKey: String, var consumerSecret: String) {

    //TODO ('Apply DI or single instance on this')
    var retrofit: Retrofit

    var retrofitWithAuth: Retrofit

    init {

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .setLenient()
            .create()

        val client = getUnsafeOkHttpClient()

        retrofit = Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        retrofitWithAuth = Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }


    fun getUpdatedAuth() {
        val client = getUnsafeOkHttpClient()
        client.interceptors().add(TokenRenewInterceptor(""))
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()

        retrofitWithAuth = Retrofit.Builder()
            .baseUrl(Constants.baseUrl)

            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    fun getUnsafeOkHttpClient(): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
        builder.hostnameVerifier { _, _ -> true }
        builder.addInterceptor(loggingInterceptor)
        builder.readTimeout(30, TimeUnit.SECONDS)
        builder.writeTimeout(30, TimeUnit.SECONDS)
        builder.connectTimeout(15, TimeUnit.SECONDS)
        return builder.build()
    }


}
