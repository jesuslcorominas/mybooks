package com.jesuslcorominas.mybooks

import android.app.Application
import androidx.room.Room
import com.jesuslcorominas.mybooks.model.database.BookDatabase

class BooksApplication : Application() {

    lateinit var db: BookDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            BookDatabase::class.java, "book-db"
        ).build()
    }
}