package com.jesuslcorominas.mybooks.model

import com.jesuslcorominas.mybooks.BooksApplication
import com.jesuslcorominas.mybooks.model.database.Book
import com.jesuslcorominas.mybooks.model.framework.RegionRepository
import com.jesuslcorominas.mybooks.model.net.BookItem
import com.jesuslcorominas.mybooks.model.net.GoogleBooksRemoteDatasource
import retrofit2.HttpException
import timber.log.Timber

class BookRepository(application: BooksApplication) {

    // TODO por ahora hardcodeamos los valores.
    //  Lo suyo seria crear filtros que nos permitieran buscar por
    //  libro/magazine y poder paginar los resultados

    private val printType = "books"
    private val projection = "lite"
    private val maxResults = 40

    private val db = application.db

    private val regionRepository =
        RegionRepository(application)

    suspend fun getAll() = db.bookDao().getAll()

    suspend fun findBooks(query: String) =
        try {
            GoogleBooksRemoteDatasource.service
                .listBooks(
                    query,
                    regionRepository.findLastRegion(),
                    printType,
                    projection,
                    maxResults
                )
        } catch (e: HttpException) {
            Timber.e(e, "Error HTTP al hacer la peticion %s", e.message)
            TODO("controlar error HTTP")
        } catch (e: Exception) {
            Timber.e(e, "Error al hacer la peticion %s", e.message)
            TODO("controlar error genérico")
        }

    suspend fun detailBook(googleId: String) =
        try {
            val book = db.bookDao().findByGoogleId(googleId)
            if (book == null) {
                GoogleBooksRemoteDatasource.service.detail(googleId).toBook()
            } else {
                book
            }
        } catch (e: HttpException) {
            Timber.e(e, "Error HTTP al hacer la peticion %s", e.message)
            TODO("controlar error HTTP")
        } catch (e: Exception) {
            Timber.e(e, "Error al hacer la peticion %s", e.message)
            TODO("controlar error genérico")
        }

    suspend fun persistBook(book: Book) {
        if (book.id == 0) {
            db.bookDao().insertBook(book)
        } else {
            db.bookDao().updateBook(book)
        }
    }
}

fun BookItem.toBook(): Book {
    return with(this) {
        Book(
            0,
            id,
            volumeInfo.title,
            volumeInfo.imageLinks.thumbnail,
            volumeInfo.description,
            volumeInfo.infoLink,
            false,
            false
        )
    }
}