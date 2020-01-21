package com.jesuslcorominas.mybooks.model.server

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoogleBooksService {

    @GET("volumes")
    suspend fun listBooks(
        @Query("q") query: String,
        @Query("langRestrict") langRestrict: String,
        @Query("printType") printType: String,
        @Query("projection") projection: String,
        @Query("maxResults") maxResults: Int

    ): ListBooksDto

    @GET("volumes/{id}")
    suspend fun detail(@Path("id") id: String): Book
}