package com.jesuslcorominas.mybooks.usecases

import com.jesuslcorominas.mybooks.data.repository.BooksRepository
import com.jesuslcorominas.mybooks.domain.Book

class ToggleCollectedBook(val booksRepository: BooksRepository) : PersistBook {
    suspend fun invoke(book: Book): Book = with(book) {
        copy(collected = !collected).also { persistBook(booksRepository, it) }
    }
}