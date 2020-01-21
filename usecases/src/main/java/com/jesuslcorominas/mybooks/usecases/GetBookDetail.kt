package com.jesuslcorominas.mybooks.usecases

import com.jesuslcorominas.mybooks.data.repository.BooksRepository

class GetBookDetail(val booksRepository: BooksRepository) {
    suspend fun invoke(googleId: String) = booksRepository.findByGoogleId(googleId)
}