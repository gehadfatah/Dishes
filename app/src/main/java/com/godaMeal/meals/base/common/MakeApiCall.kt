package com.godaMeal.meals.base.common

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

suspend fun <T> makeAPiCall(dispatcher: CoroutineDispatcher, block: suspend () -> T) = flow {
    emit(UiState.Loading(true))
    val result = block()
    emit(UiState.Success(result))
    emit(UiState.Loading(false))
}.flowOn(dispatcher).catch { t ->
    //val errorResponse: String = ErrorHandler.handleError(t)
    Log.i("@@##ERROR", t.message.toString())
    emit(UiState.Error(t.message.toString()))
    emit(UiState.Loading(false))
}