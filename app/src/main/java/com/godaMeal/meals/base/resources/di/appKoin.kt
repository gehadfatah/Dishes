package com.godaMeal.meals.base.resources.di

import android.app.Application


import com.godaMeal.meals.data.local.di.localModule
import com.godaMeal.meals.data.remote.di.remoteModule
import com.godaMeal.meals.menustags.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


fun start(application: Application) {
    startKoin {
        androidContext(application)
        printLogger()
        modules(
            listOf(
                localModule
                , remoteModule
                , resourcesModule
                , mainModule
            )
        )
    }
}