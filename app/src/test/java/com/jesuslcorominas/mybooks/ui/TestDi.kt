package com.jesuslcorominas.mybooks.ui

import com.jesuslcorominas.mybooks.data.repository.PermissionChecker
import com.jesuslcorominas.mybooks.data.source.LocalDatasource
import com.jesuslcorominas.mybooks.data.source.LocationDatasource
import com.jesuslcorominas.mybooks.data.source.RemoteDatasource
import com.jesuslcorominas.mybooks.dataModule
import com.jesuslcorominas.mybooks.domain.Book
import com.jesuslcorominas.mybooks.testshared.mockedBook
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initMockedDi(vararg modules: Module) {
    startKoin {
        modules(listOf(mockedAppModule, dataModule) + modules)
    }
}

private val mockedAppModule = module {
    single<LocalDatasource> { FakeLocalDataSource() }
    single<RemoteDatasource> { FakeRemoteDataSource() }
    single<LocationDatasource> { FakeLocationDataSource() }
    single<PermissionChecker> { FakePermissionChecker() }
    single { Dispatchers.Unconfined }
}

val defaultFakeBooks = listOf(
    mockedBook.copy(id = 1, googleId = "uOZhYgEACAAJ"),
    mockedBook.copy(
        id = 2,
        googleId = "uOZhYgEACAAK",
        favourite = true,
        collected = true,
        read = true
    ),
    mockedBook.copy(id = 3, googleId = "uOZhYgEACAAL"),
    mockedBook.copy(id = 4, googleId = "uOZhYgEACAAM")
)

class FakeLocalDataSource : LocalDatasource {

    var books = defaultFakeBooks

    override suspend fun getAll(): List<Book> = books

    override suspend fun findByGoogleId(googleId: String): Book? =
        books.filter { it.googleId.equals(googleId) }.first()

    override suspend fun persistBook(book: Book) {
        books = books.filterNot { it.googleId == book.googleId } + book
    }
}

class FakeRemoteDataSource : RemoteDatasource {

    var books = defaultFakeBooks

    override suspend fun findBooks(query: String, region: String) = books
    override suspend fun getBookDetail(googleId: String): Book? =
        books.filter { it.googleId.equals(googleId) }.first()
}

class FakeLocationDataSource : LocationDatasource {
    var location = "ES"

    override suspend fun findLastRegion(): String? = location
}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true

    override suspend fun check(permission: PermissionChecker.Permission): Boolean =
        permissionGranted
}