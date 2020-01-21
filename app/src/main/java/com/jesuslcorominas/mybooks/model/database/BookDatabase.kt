package com.jesuslcorominas.mybooks.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Book::class], version = 1)
abstract class BookDatabase : RoomDatabase() {

    companion object {
        fun build(context: Context) = Room.databaseBuilder(
            context,
            BookDatabase::class.java,
            "book-db"
        ).build()
    }

    abstract fun bookDao(): BookDao
}