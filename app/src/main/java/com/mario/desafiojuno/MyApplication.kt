package com.mario.desafiojuno

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.mario.desafiojuno.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * My application
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(appModules)
        }

        Fresco.initialize(this)
    }
}