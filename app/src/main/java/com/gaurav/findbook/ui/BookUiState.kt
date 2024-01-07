package com.gaurav.findbook.ui

import com.gaurav.findbook.model.Item

sealed interface BookUiState {
    data class Success(val bookList: List<Item>): BookUiState
    object Welcome: BookUiState
    object Loading:BookUiState
    data class HttpError(val code:Int?, val message: String?): BookUiState
    data class Error(val message: String?): BookUiState
}