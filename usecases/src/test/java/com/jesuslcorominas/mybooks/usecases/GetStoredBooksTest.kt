package com.jesuslcorominas.mybooks.usecases

import com.jesuslcorominas.mybooks.data.repository.BooksRepository
import com.jesuslcorominas.mybooks.testshared.mockedBook
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetStoredBooksTest {

    @Mock
    lateinit var booksRepository: BooksRepository

    lateinit var getStoredBooks: GetStoredBooks

    @Before
    fun setUp() {
        getStoredBooks = GetStoredBooks(booksRepository)
    }

    @Test
    fun `invoke calls books repository`() {
        runBlocking {

            val books = listOf(mockedBook.copy(id = 1))
            whenever(booksRepository.getAll()).thenReturn(books)

            val result = getStoredBooks.invoke()

            Assert.assertEquals(books, result)
        }
    }
}