package com.gauravnadar.notes.util

import android.app.Application
import timber.log.Timber

class LoggerApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}