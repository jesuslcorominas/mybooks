package com.jesuslcorominas.mybooks.usecases

import com.jesuslcorominas.mybooks.data.repository.BooksRepository
import com.jesuslcorominas.mybooks.data.repository.RegionRepository
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
class FindBooksTest {

    @Mock
    lateinit var booksRepository: BooksRepository

    @Mock
    lateinit var regionRepository: RegionRepository

    lateinit var findBooks: FindBooks


    @Before
    fun setUp() {
        findBooks = FindBooks(booksRepository, regionRepository)
    }

    @Test
    fun `invoke calls books and region repositories`() {
        runBlocking {

            val books = listOf(mockedBook.copy(id = 1))
            whenever(regionRepository.findLastRegion()).thenReturn("ES")

            val region = regionRepository.findLastRegion()
            whenever(booksRepository.findBooks("El nombre del viento", "ES")).thenReturn(books)

            val resultOk = findBooks.invoke("El nombre del viento")
            Assert.assertEquals(books, resultOk)

            whenever(regionRepository.findLastRegion()).thenReturn("EN")

            val resultKo = findBooks.invoke("El nombre del viento")
            Assert.assertNotEquals(books, resultKo)
        }

    }
}