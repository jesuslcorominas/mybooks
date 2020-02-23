package com.jesuslcorominas.mybooks.usecases

import com.jesuslcorominas.mybooks.data.repository.BooksRepository

class GetStoredBooks(val booksRepository: BooksRepository) {
    suspend fun invoke() = booksRepository.getAll()
}