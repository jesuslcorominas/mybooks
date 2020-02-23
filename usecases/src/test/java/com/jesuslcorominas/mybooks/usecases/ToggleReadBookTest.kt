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
class ToggleReadBookTest {

    @Mock
    lateinit var booksRepository: BooksRepository

    lateinit var toggleReadBook: ToggleReadBook

    @Before
    fun setUp() {
        toggleReadBook = ToggleReadBook(booksRepository)
    }

    @Test
    fun `invoke calls books repository`() {
        runBlocking {

            val book = mockedBook.copy(id = 1)

            val result = toggleReadBook.invoke(book)

            verify(booksRepository).persistBook(result)
        }
    }

    @Test
    fun `read book becomes unread`() {
        runBlocking {

            val book = mockedBook.copy(read = true)

            val result = toggleReadBook.invoke(book)

            Assert.assertFalse(result.read)
        }
    }

    @Test
    fun `unread book becomes read`() {
        runBlocking {

            val book = mockedBook.copy(read = false)

            val result = toggleReadBook.invoke(book)

            Assert.assertTrue(result.read)
        }
    }
}