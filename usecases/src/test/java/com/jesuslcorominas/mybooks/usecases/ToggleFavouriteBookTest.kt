package com.jesuslcorominas.mybooks.usecases

import com.jesuslcorominas.mybooks.data.repository.BooksRepository
import com.jesuslcorominas.mybooks.testshared.mockedBook
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ToggleFavouriteBookTest {

    @Mock
    lateinit var booksRepository: BooksRepository

    lateinit var toggleFavouriteBook: ToggleFavouriteBook

    @Before
    fun setUp() {
        toggleFavouriteBook = ToggleFavouriteBook(booksRepository)
    }

    @Test
    fun `invoke calls books repository`() {
        runBlocking {

            val book = mockedBook.copy(id = 1)

            val result = toggleFavouriteBook.invoke(book)

            verify(booksRepository).persistBook(result)
        }
    }

    @Test
    fun `favourite book becomes unfavourite`() {
        runBlocking {

            val book = mockedBook.copy(favourite = true)

            val result = toggleFavouriteBook.invoke(book)

            Assert.assertFalse(result.favourite)
        }
    }

    @Test
    fun `unfavourite book becomes favourite`() {
        runBlocking {

            val book = mockedBook.copy(favourite = false)

            val result = toggleFavouriteBook.invoke(book)

            Assert.assertTrue(result.favourite)
        }
    }
}