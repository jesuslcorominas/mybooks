package com.jesuslcorominas.mybooks.model.net

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object GoogleBooksRemoteDatasource {
    private val okHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(this).build()
    }

    val service: GoogleBooksService = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/books/v1/")
        .client(okHttpClient)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .run {
            create<GoogleBooksService>(
                GoogleBooksService::class.java)
        }
}

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
    suspend fun detail(@Path("id") id: String): BookItem
}