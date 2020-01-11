package com.jesuslcorominas.mybooks.model

import android.app.Activity
import retrofit2.HttpException
import timber.log.Timber

class BookRepository(activity: Activity) {

    // TODO por ahora hardcodeamos los valores.
    //  Lo suyo seria crear filtros que nos permitieran buscar por
    //  libro/magazine, hacer un metodo de busqueda de detalle y
    //  poder paginar los resultados

    private val printType = "books"
    private val projection = "lite"
    private val maxResults = 40

    private val regionRepository = RegionRepository(activity)

    suspend fun listBooks(query: String) =
        try {
            GoogleBooks.service
                .listBooks(
                    query,
                    regionRepository.findLastRegion(),
                    printType,
                    projection,
                    maxResults
                )
        } catch (e: HttpException) {
            Timber.e(e, "Error HTTP al hacer la peticion %s", e.message)
            ListBooksDto()
        } catch (e: Exception) {
            Timber.e(e, "Error al hacer la peticion %s", e.message)
            ListBooksDto()
        }

    suspend fun detailBook(id: String) =
        try {
            GoogleBooks.service.detail(id)
        } catch (e: HttpException) {
            Timber.e(e, "Error HTTP al hacer la peticion %s", e.message)
            BookItem()
        } catch (e: Exception) {
            Timber.e(e, "Error al hacer la peticion %s", e.message)
            BookItem()
        }
}