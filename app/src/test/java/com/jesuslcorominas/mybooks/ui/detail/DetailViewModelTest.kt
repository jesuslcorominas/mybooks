package com.jesuslcorominas.mybooks.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jesuslcorominas.mybooks.domain.Book
import com.jesuslcorominas.mybooks.testshared.mockedBook
import com.jesuslcorominas.mybooks.usecases.GetBookDetail
import com.jesuslcorominas.mybooks.usecases.ToggleCollectedBook
import com.jesuslcorominas.mybooks.usecases.ToggleFavouriteBook
import com.jesuslcorominas.mybooks.usecases.ToggleReadBook
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
class DetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getBookDetail: GetBookDetail

    @Mock
    lateinit var toggleFavouriteBook: ToggleFavouriteBook

    @Mock
    lateinit var toggleCollectedBook: ToggleCollectedBook

    @Mock
    lateinit var toggleReadBook: ToggleReadBook

    @Mock
    lateinit var observerBook: Observer<Book>

    private lateinit var vm: DetailViewModel

    @Before
    fun setUp() {
        vm = DetailViewModel(
            "uOZhYgEACAAJ",
            toggleCollectedBook,
            toggleFavouriteBook,
            toggleReadBook,
            getBookDetail,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun `observing LiveData finds the book`() {
        runBlocking {
            val book = mockedBook.copy(id = 1)
            whenever(getBookDetail.invoke("uOZhYgEACAAJ")).thenReturn(book)

            vm.book.observeForever(observerBook)

            vm.getDetail()

            verify(observerBook).onChanged(vm.book.value)
        }
    }

    @Test
    fun `when favorite clicked, the toggleFavouriteBook use case is invoked`() {
        runBlocking {
            val book = mockedBook.copy(id = 1)
            whenever(getBookDetail.invoke("uOZhYgEACAAJ")).thenReturn(book)
            whenever(toggleFavouriteBook.invoke(book)).thenReturn(book.copy(favourite = !book.favourite))

            vm.book.observeForever(observerBook)

            vm.getDetail()

            vm.onFavouriteClicked()

            verify(toggleFavouriteBook).invoke(book)
        }
    }

    @Test
    fun `when read clicked, the toggleReadBook use case is invoked`() {
        runBlocking {
            val book = mockedBook.copy(id = 1)
            whenever(getBookDetail.invoke("uOZhYgEACAAJ")).thenReturn(book)
            whenever(toggleReadBook.invoke(book)).thenReturn(book.copy(read = !book.read))
            vm.book.observeForever(observerBook)

            vm.getDetail()

            vm.onReadClicked()

            verify(toggleReadBook).invoke(book)
        }
    }

    @Test
    fun `when collected clicked, the toggleCollectedBook use case is invoked`() {
        runBlocking {
            val book = mockedBook.copy(id = 1)
            whenever(getBookDetail.invoke("uOZhYgEACAAJ")).thenReturn(book)
            whenever(toggleCollectedBook.invoke(book)).thenReturn(book.copy(collected = !book.collected))

            vm.book.observeForever(observerBook)

            vm.getDetail()

            vm.onCollectedClicked()

            verify(toggleCollectedBook).invoke(book)
        }
    }
}