package com.jesuslcorominas.mybooks.usecases

import com.jesuslcorominas.mybooks.data.repository.BooksRepository
import com.jesuslcorominas.mybooks.domain.Book

interface PersistBook {
    suspend fun persistBook(booksRepository: BooksRepository, book: Book) =
        booksRepository.persistBook(book)
}