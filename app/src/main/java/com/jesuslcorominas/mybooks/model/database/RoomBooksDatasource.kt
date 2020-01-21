package com.jesuslcorominas.mybooks.model.database

import com.jesuslcorominas.mybooks.data.source.LocalDatasource
import com.jesuslcorominas.mybooks.domain.Book
import com.jesuslcorominas.mybooks.model.toBook
import com.jesuslcorominas.mybooks.model.toDatabaseBook

class RoomBooksDatasource(private val bookDatabase: BookDatabase) : LocalDatasource {
    override suspend fun getAll(): List<Book> = bookDatabase.bookDao().getAll().map { it.toBook() }

    override suspend fun findByGoogleId(googleId: String): Book? =
        bookDatabase.bookDao().findByGoogleId(googleId)?.toBook()


    override suspend fun persistBook(book: Book) =
        if (book.id == 0) bookDatabase.bookDao().insertBook(book.toDatabaseBook())
        else bookDatabase.bookDao().updateBook(book.toDatabaseBook())

}