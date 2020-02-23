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
class ToggleCollectedBookTest {

    @Mock
    lateinit var booksRepository: BooksRepository

    lateinit var toggleCollectedBook: ToggleCollectedBook

    @Before
    fun setUp() {
        toggleCollectedBook = ToggleCollectedBook(booksRepository)
    }

    @Test
    fun `invoke calls books repository`() {
        runBlocking {

            val book = mockedBook.copy(id = 1)

            val result = toggleCollectedBook.invoke(book)

            verify(booksRepository).persistBook(result)
        }
    }

    @Test
    fun `collected book becomes uncollected`() {
        runBlocking {

            val book = mockedBook.copy(collected = true)

            val result = toggleCollectedBook.invoke(book)

            Assert.assertFalse(result.collected)
        }
    }

    @Test
    fun `uncollected book becomes collected`() {
        runBlocking {

            val book = mockedBook.copy(collected = false)

            val result = toggleCollectedBook.invoke(book)

            Assert.assertTrue(result.collected)
        }
    }
}