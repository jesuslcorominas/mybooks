package com.jesuslcorominas.mybooks.model

import com.jesuslcorominas.mybooks.BooksApplication
import com.jesuslcorominas.mybooks.data.repository.RegionRepository
import com.jesuslcorominas.mybooks.domain.Book
import com.jesuslcorominas.mybooks.model.framework.AndroidPermissionChecker
import com.jesuslcorominas.mybooks.model.framework.PlayServicesLocationDatasource
import com.jesuslcorominas.mybooks.model.net.GoogleBooksRemoteDatasource
import retrofit2.HttpException
import timber.log.Timber
import com.jesuslcorominas.mybooks.data.source.server.model.Book as RemoteBook
import com.jesuslcorominas.mybooks.model.database.Book as DatabaseBook

class BookRepository(application: BooksApplication) {

    // TODO por ahora hardcodeamos los valores.
    //  Lo suyo seria crear filtros que nos permitieran buscar por
    //  libro/magazine y poder paginar los resultados

    private val printType = "books"
    private val projection = "lite"
    private val maxResults = 40

    private val db = application.db

    private val regionRepository =
        RegionRepository(
            PlayServicesLocationDatasource(application),
            AndroidPermissionChecker(application)
        )

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

    suspend fun persistBook(book: Book) =
        if (book.id == 0) db.bookDao().insertBook(book.toDatabaseBook())
        else db.bookDao().updateBook(book.toDatabaseBook())
}

fun RemoteBook.toBook(): Book {
    return with(this) {
        Book(
            0,
            id,
            volumeInfo.title,
            volumeInfo.imageLinks.thumbnail,
            volumeInfo.description,
            volumeInfo.infoLink,
            false,
            false,
            false
        )
    }
}

fun Book.toDatabaseBook(): DatabaseBook {
    return with(this) {
        DatabaseBook(
            id,
            googleId,
            title,
            thumbnail,
            description,
            infoLink,
            favourite,
            collected,
            read
        )
    }
}

fun DatabaseBook.toBook() {
    return with(this) {
        Book(
            id,
            googleId,
            title,
            thumbnail,
            description,
            infoLink,
            favourite,
            collected,
            read
        )
    }
}
