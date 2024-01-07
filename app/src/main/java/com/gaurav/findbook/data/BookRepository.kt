package com.gaurav.findbook.data

import com.gaurav.findbook.model.Books
import com.gaurav.findbook.network.BookApiService

interface BookRepository {

    suspend fun findBook(query:String): Books
}

class NetworkBookRepository(private val apiService: BookApiService):BookRepository{
    override suspend fun findBook(query: String): Books {
        return apiService.getBook(query)
    }

}