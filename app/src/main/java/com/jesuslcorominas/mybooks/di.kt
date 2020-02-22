package com.jesuslcorominas.mybooks

import android.app.Application
import com.jesuslcorominas.mybooks.data.repository.BooksRepository
import com.jesuslcorominas.mybooks.data.repository.PermissionChecker
import com.jesuslcorominas.mybooks.data.repository.RegionRepository
import com.jesuslcorominas.mybooks.data.source.LocalDatasource
import com.jesuslcorominas.mybooks.data.source.LocationDatasource
import com.jesuslcorominas.mybooks.data.source.RemoteDatasource
import com.jesuslcorominas.mybooks.model.database.BookDatabase
import com.jesuslcorominas.mybooks.model.database.RoomBooksDatasource
import com.jesuslcorominas.mybooks.model.location.AndroidPermissionChecker
import com.jesuslcorominas.mybooks.model.location.PlayServicesLocationDatasource
import com.jesuslcorominas.mybooks.model.server.GoogleBooks
import com.jesuslcorominas.mybooks.model.server.GoogleBooksDatasource
import com.jesuslcorominas.mybooks.ui.detail.DetailActivity
import com.jesuslcorominas.mybooks.ui.detail.DetailViewModel
import com.jesuslcorominas.mybooks.ui.main.MainActivity
import com.jesuslcorominas.mybooks.ui.main.MainViewModel
import com.jesuslcorominas.mybooks.usecases.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {

    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

private val appModule = module {
    single { BookDatabase.build(get()) }
    factory<LocalDatasource> { RoomBooksDatasource(get()) }
    single(named("baseUrl")) { "https://www.googleapis.com/books/v1/" }
    factory { GoogleBooks(get(named("baseUrl"))) }
    factory<RemoteDatasource> { GoogleBooksDatasource(get()) }
    factory<PermissionChecker> { AndroidPermissionChecker(get()) }
    factory<LocationDatasource> { PlayServicesLocationDatasource(get()) }

}

val dataModule = module {
    factory { RegionRepository(get(), get()) }
    factory { BooksRepository(get(), get()) }
}

private val scopesModule = module {
    scope(named<MainActivity>()) {
        viewModel { MainViewModel(get(), get()) }
        scoped { GetStoredBooks(get()) }
        scoped { FindBooks(get(), get()) }
    }

    scope(named<DetailActivity>()) {
        viewModel { (googleId: String) -> DetailViewModel(googleId, get(), get(), get(), get()) }
        scoped { ToggleCollectedBook(get()) }
        scoped { ToggleFavouriteBook(get()) }
        scoped { ToggleReadBook(get()) }
        scoped { GetBookDetail(get()) }
    }
}

