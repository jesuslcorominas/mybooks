package com.jesuslcorominas.mybooks.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jesuslcorominas.mybooks.domain.Book
import com.jesuslcorominas.mybooks.ui.FakeLocalDataSource
import com.jesuslcorominas.mybooks.ui.FakeRemoteDataSource
import com.jesuslcorominas.mybooks.ui.initMockedDi
import com.jesuslcorominas.mybooks.usecases.FindBooks
import com.jesuslcorominas.mybooks.usecases.GetStoredBooks
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainIntegrationTest : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Mock
    lateinit var booksObserver: Observer<List<Book>>

    private lateinit var vm: MainViewModel

    private lateinit var localDataSource: FakeLocalDataSource

    private lateinit var remoteDatasource: FakeRemoteDataSource

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)

        val vmModule = module {
            factory { MainViewModel(get(), get(), get()) }
            factory { GetStoredBooks(get()) }
            factory { FindBooks(get(), get()) }
        }

        initMockedDi(vmModule)

        vm = get()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `data is loaded from database when location permisson requested`() {
        vm.books.observeForever(booksObserver)

        vm.onCoarsePermissionRequested()

        runBlocking {
            launch(Dispatchers.Main) {
                verify(booksObserver).onChanged(vm.books.value)
            }
        }
    }

    @Test
    fun `data is loaded from remote datasource when perform search`() {
        vm.books.observeForever(booksObserver)

        vm.onSearch("El nombre del viento")

        runBlocking {
            launch(Dispatchers.Main) {
                verify(booksObserver).onChanged(vm.books.value)
            }
        }
    }
}