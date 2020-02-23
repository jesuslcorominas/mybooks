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
class GetBookDetailTest {

    @Mock
    lateinit var booksRepository: BooksRepository

    lateinit var getBookDetail: GetBookDetail

    @Before
    fun setUp() {
        getBookDetail = GetBookDetail(booksRepository)
    }

    @Test
    fun `invoke calls book repository`() {
        runBlocking {

            val book = mockedBook.copy(id = 1)
            whenever(booksRepository.findByGoogleId("uOZhYgEACAAJ")).thenReturn(book)

            val resultOk = getBookDetail.invoke("uOZhYgEACAAJ")
            Assert.assertEquals(book, resultOk)

            val resultKo = getBookDetail.invoke("uOZhYgEACAAK")
            Assert.assertNotEquals(book, resultKo)
        }
    }
}