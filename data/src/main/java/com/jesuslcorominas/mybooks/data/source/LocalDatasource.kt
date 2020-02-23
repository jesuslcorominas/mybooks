package com.jesuslcorominas.mybooks.data.source

import com.jesuslcorominas.mybooks.domain.Book

interface LocalDatasource {

    suspend fun getAll(): List<Book>

    suspend fun findByGoogleId(googleId: String): Book?

    suspend fun persistBook(book: Book)
}