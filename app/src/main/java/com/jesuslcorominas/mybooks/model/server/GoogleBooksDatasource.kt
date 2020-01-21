package com.jesuslcorominas.mybooks.model.server

import com.jesuslcorominas.mybooks.data.source.RemoteDatasource
import com.jesuslcorominas.mybooks.domain.Book
import retrofit2.HttpException
import com.jesuslcorominas.mybooks.model.server.Book as RemoteBook

class GoogleBooksDatasource(private val googleBooks: GoogleBooks) : RemoteDatasource {
    companion object {
        // TODO por ahora hardcodeamos los valores.
        //  Lo suyo seria crear filtros que nos permitieran buscar por
        //  libro/magazine y poder paginar los resultados

        private val printType = "books"
        private val projection = "lite"
        private val maxResults = 40
    }

    override suspend fun findBooks(query: String, region: String): List<Book> {
        try {
            return googleBooks.service.listBooks(
                query, region,
                printType,
                projection,
                maxResults
            ).items.map { it.toBook() }
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getBookDetail(googleId: String): Book? {
        try {
            return googleBooks.service.detail(googleId).toBook()
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }
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