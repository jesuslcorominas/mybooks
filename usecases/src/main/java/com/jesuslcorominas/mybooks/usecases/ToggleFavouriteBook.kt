package com.jesuslcorominas.mybooks.usecases

import com.jesuslcorominas.mybooks.data.repository.BooksRepository
import com.jesuslcorominas.mybooks.domain.Book

class ToggleFavouriteBook(val booksRepository: BooksRepository) : PersistBook {
    suspend fun invoke(book: Book): Book = with(book) {
        copy(favourite = !favourite).also { persistBook(booksRepository, it) }
    }
}