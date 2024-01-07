package com.gaurav.findbook.data

import com.gaurav.findbook.BuildConfig
import com.gaurav.findbook.network.BookApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val bookRepository: BookRepository
}

class DefaultAppContainer():AppContainer{

    private val baseUrl:String = "https://www.googleapis.com/"

    private fun getClient() = if(BuildConfig.DEBUG){

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }else{
        OkHttpClient
            .Builder()
            .build()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(getClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val bookApiService: BookApiService by lazy {
      retrofit.create(BookApiService::class.java)
    }

    override val bookRepository: BookRepository by lazy{
        NetworkBookRepository(bookApiService)
    }
}