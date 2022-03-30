package com.godaMeal.meals.base.common

sealed class UiState<out T>{
    object Empty : UiState<Nothing>()
    data class Loading(val status: Boolean) : UiState<Nothing>()
    data class Success<out R>(val data: R) : UiState<R>()
    data class Error(val error: String) : UiState<Nothing>()
}
