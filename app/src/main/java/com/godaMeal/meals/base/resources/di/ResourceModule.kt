package com.godaMeal.meals.base.resources.di

import com.godaMeal.meals.base.resources.AppResources
import com.godaMeal.meals.base.resources.repository.ResourcesRepository
import org.koin.dsl.module

val resourcesModule = module {
    single { AppResources(get()) }
    single { ResourcesRepository(get()) }
}