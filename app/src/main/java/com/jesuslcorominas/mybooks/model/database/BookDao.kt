package com.jesuslcorominas.mybooks.model.database

import androidx.room.*

@Dao
interface BookDao {

    @Query("SELECT * FROM Book WHERE collected || favourite || read")
    suspend fun getAll(): List<Book>

    @Query("SELECT * FROM Book where googleId = :googleId")
    suspend fun findByGoogleId(googleId: String): Book?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBook(book: Book)

    @Update
    suspend fun updateBook(book: Book)
}