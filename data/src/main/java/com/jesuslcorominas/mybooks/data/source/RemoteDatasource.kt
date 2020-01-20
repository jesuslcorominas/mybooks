package com.jesuslcorominas.mybooks.data.source

import com.jesuslcorominas.mybooks.domain.Book


interface RemoteDatasource {

    suspend fun findBooks(query: String, region: String): List<Book>

    suspend fun getBookDetail(googleId: String): Book?
}