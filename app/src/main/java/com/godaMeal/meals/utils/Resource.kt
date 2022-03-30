package com.godaMeal.meals.utils

data class Resource<out T> (
        val status: Status,
        val data: T?,
        val error: Throwable?
)