package com.jesuslcorominas.mybooks.data.repository

import com.jesuslcorominas.mybooks.data.source.LocalDatasource
import com.jesuslcorominas.mybooks.data.source.RemoteDatasource
import com.jesuslcorominas.mybooks.domain.Book

class BooksRepository(
    private val localDatasource: LocalDatasource,
    private val remoteDatasource: RemoteDatasource
) {

    suspend fun getAll() = localDatasource.getAll()

    suspend fun findBooks(query: String, region: String) = remoteDatasource.findBooks(query, region)

    suspend fun persistBook(book: Book) = localDatasource.persistBook(book)

    suspend fun findByGoogleId(googleId: String): Book? {
        return localDatasource.findByGoogleId(googleId) ?: remoteDatasource.getBookDetail(googleId)
    }
}