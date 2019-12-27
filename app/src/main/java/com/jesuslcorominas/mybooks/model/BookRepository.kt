package com.jesuslcorominas.mybooks.model

import android.app.Activity
import com.antonioleiva.mymovies.model.RegionRepository

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
        GoogleBooks.service
            .listBooks(
                query,
                regionRepository.findLastRegion(),
                printType,
                projection,
                maxResults
            )
            .await()

    suspend fun detailBook(id: String) = GoogleBooks.service.detail(id).await()
}