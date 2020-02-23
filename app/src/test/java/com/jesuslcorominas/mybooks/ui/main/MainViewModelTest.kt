package com.jesuslcorominas.mybooks.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jesuslcorominas.mybooks.domain.Book
import com.jesuslcorominas.mybooks.testshared.mockedBook
import com.jesuslcorominas.mybooks.ui.common.Event
import com.jesuslcorominas.mybooks.usecases.FindBooks
import com.jesuslcorominas.mybooks.usecases.GetStoredBooks
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getStoredBooks: GetStoredBooks

    @Mock
    lateinit var findBooks: FindBooks

    @Mock
    lateinit var observerBooks: Observer<List<Book>>

    @Mock
    lateinit var observerRequestLocationPermission: Observer<Event<Unit>>

    private lateinit var vm: MainViewModel

    @Before
    fun setUp() {
        vm = MainViewModel(
            getStoredBooks,
            findBooks,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun `refresh UI launches location permission request`() {
        vm.requestLocationPermission.observeForever(observerRequestLocationPermission)

        vm.refresh()

        verify(observerRequestLocationPermission).onChanged(vm.requestLocationPermission.value)
    }

    @Test
    fun `after requesting the permission gets stored books`() {
        runBlocking {

            val books = listOf(mockedBook.copy(id = 1))
            whenever(getStoredBooks.invoke()).thenReturn(books)
            vm.books.observeForever(observerBooks)

            vm.onCoarsePermissionRequested()

            verify(observerBooks).onChanged(vm.books.value)
        }
    }

    @Test
    fun `on search books finds remote books`() {
        runBlocking {
            val books = listOf(mockedBook.copy(id = 1))
            whenever(findBooks.invoke("el nombre del viento")).thenReturn(books)

            vm.books.observeForever(observerBooks)

            vm.onSearch("el nombre del viento")

            verify(observerBooks).onChanged(vm.books.value)
        }
    }
}