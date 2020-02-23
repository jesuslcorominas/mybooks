package com.jesuslcorominas.mybooks

import android.app.Application
import com.facebook.stetho.Stetho

class BooksApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initDI()

        Stetho.initializeWithDefaults(this)
    }
}