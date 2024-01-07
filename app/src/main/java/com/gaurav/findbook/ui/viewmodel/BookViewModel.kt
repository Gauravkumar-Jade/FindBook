package com.gaurav.findbook.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.gaurav.findbook.FindBookApplication
import com.gaurav.findbook.data.BookRepository
import com.gaurav.findbook.ui.BookUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception

class BookViewModel(private val bookRepository: BookRepository):ViewModel() {

    var bookUiState:BookUiState by mutableStateOf(BookUiState.Welcome)
        private set


    fun findBook(query: String){
        viewModelScope.launch {
            bookUiState = BookUiState.Loading
            bookUiState = try{
                val books = bookRepository.findBook(query)
                BookUiState.Success(books.items)
            }catch (e: Exception){
                BookUiState.Error(message = e.message)
            }catch (e:HttpException){
                BookUiState.HttpError(code = e.code(), message = e.message)
            }
        }
    }


    companion object{
        val Factory:ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FindBookApplication)
                val repository = application.container.bookRepository
                BookViewModel(bookRepository = repository)
            }
        }
    }
}