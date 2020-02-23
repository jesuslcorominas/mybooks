package com.jesuslcorominas.mybooks.data.repository

import com.jesuslcorominas.mybooks.data.source.LocalDatasource
import com.jesuslcorominas.mybooks.data.source.RemoteDatasource
import com.jesuslcorominas.mybooks.testshared.mockedBook
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BooksRepositoryTest {

    @Mock
    lateinit var localDataSource: LocalDatasource

    @Mock
    lateinit var remoteDataSource: RemoteDatasource

    @Mock
    lateinit var regionRepository: RegionRepository

    lateinit var booksRepository: BooksRepository

    @Before
    fun setUp() {
        booksRepository =
            BooksRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun `getAll gets all books from local data source`() {
        runBlocking {

            val localBooks = listOf(mockedBook.copy(1))
            whenever(localDataSource.getAll()).thenReturn(localBooks)

            val result = booksRepository.getAll()

            Assert.assertEquals(localBooks, result)
        }
    }

    @Test
    fun `findBooks finds books from remote data source`() {
        runBlocking {
            val remoteBooks = listOf(mockedBook.copy(1))
            whenever(remoteDataSource.findBooks("El nombre del viento", "ES")).thenReturn(
                remoteBooks
            )

            val resultOk = booksRepository.findBooks("El nombre del viento", "ES")
            val resultKo = booksRepository.findBooks("El temor del hombre sabio", "ES")

            Assert.assertEquals(remoteBooks, resultOk)
            Assert.assertNotEquals(remoteBooks, resultKo)
        }
    }

    @Test
    fun `persistBook persists local data source`() {
        runBlocking {

            val book = mockedBook.copy(id = 1)

            booksRepository.persistBook(book)

            verify(localDataSource).persistBook(book)
        }
    }

    @Test
    fun `findByGoogleId finds book detail from remote datasource`() {
        runBlocking {
            val book = mockedBook.copy(1)
            whenever(remoteDataSource.getBookDetail("uOZhYgEACAAJ")).thenReturn(book)

            val resultOk = booksRepository.findByGoogleId("uOZhYgEACAAJ")
            val resultKo = booksRepository.findByGoogleId("uOZhYgEACAAK")

            Assert.assertEquals(book, resultOk)
            Assert.assertNotEquals(book, resultKo)
        }
    }
}