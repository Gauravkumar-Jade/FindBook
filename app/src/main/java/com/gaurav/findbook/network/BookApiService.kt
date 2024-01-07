package com.gaurav.findbook.network

import com.gaurav.findbook.model.Books
import retrofit2.http.GET
import retrofit2.http.Query


interface BookApiService {

    @GET("books/v1/volumes?")
    suspend fun getBook(@Query("q") query:String):Books
}