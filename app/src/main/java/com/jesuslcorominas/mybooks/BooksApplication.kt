package com.jesuslcorominas.mybooks

import android.app.Application

class BooksApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initDI()
    }
}