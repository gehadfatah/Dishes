package com.godaMeal.meals

import android.app.Application
import com.godaMeal.meals.base.common.ApplicationIntegration
import com.godaMeal.meals.base.resources.di.start
import com.facebook.drawee.backends.pipeline.Fresco

class MealsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ApplicationIntegration.with(this)
        start(this)
        Fresco.initialize(this)

    }
}