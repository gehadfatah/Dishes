package com.godaMeal.meals.base.resources.repository


import com.godaMeal.meals.R
import com.godaMeal.meals.base.resources.AppResources


class ResourcesRepository(private val appResources: AppResources) {

    fun getNetworkExceptionMessage(): String =
        appResources.getString(R.string.no_internet_connection)

    fun getSocketTimeoutExceptionMessage(): String =
        appResources.getString(R.string.timeout_error_message)

    fun getGenericUnknownMessage(): String = appResources.getString(R.string.operational_error_message)
}