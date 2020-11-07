package com.dev.numberslight

import android.app.Application
import android.os.StrictMode
import com.dev.numberslight.injection.component.AppComponent
import com.dev.numberslight.injection.component.DaggerAppComponent
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

class NumbersLightApplication : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()

    override fun onCreate() {
        initLeakCanary()
        initTimber()
        setStrictMode()
        super.onCreate()
    }

    private fun initLeakCanary() = if (LeakCanary.isInAnalyzerProcess(this)) {
        null
    } else LeakCanary.install(this)

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .build()
            )
        }
    }
}