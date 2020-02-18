package com.jesuslcorominas.mybooks.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jesuslcorominas.mybooks.data.source.LocalDatasource
import com.jesuslcorominas.mybooks.domain.Book
import com.jesuslcorominas.mybooks.ui.FakeLocalDataSource
import com.jesuslcorominas.mybooks.ui.defaultFakeBooks
import com.jesuslcorominas.mybooks.ui.initMockedDi
import com.jesuslcorominas.mybooks.usecases.GetBookDetail
import com.jesuslcorominas.mybooks.usecases.ToggleCollectedBook
import com.jesuslcorominas.mybooks.usecases.ToggleFavouriteBook
import com.jesuslcorominas.mybooks.usecases.ToggleReadBook
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Mock
    lateinit var observer: Observer<Book>

    private lateinit var vm: DetailViewModel
    private lateinit var localDataSource: FakeLocalDataSource

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)

        val vmModule = module {
            factory { (googleId: String) -> DetailViewModel(googleId, get(), get(), get(), get()) }
            factory { GetBookDetail(get()) }
            factory { ToggleFavouriteBook(get()) }
            factory { ToggleReadBook(get()) }
            factory { ToggleCollectedBook(get()) }

        }

        initMockedDi(vmModule)

        localDataSource = get<LocalDatasource>() as FakeLocalDataSource
        localDataSource.books = defaultFakeBooks
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }


    private fun initVmWithNonPersistedBook() {
        vm = get { parametersOf("uOZhYgEACAAJ") }
    }

    private fun initVmWithPersistedBook() {
        vm = get { parametersOf("uOZhYgEACAAK") }
    }

    @Test
    fun `observing LiveData finds the book`() {
        initVmWithNonPersistedBook()

        vm.book.observeForever(observer)

        runBlocking {
            launch(Dispatchers.Main) {
                verify(observer).onChanged(vm.book.value)
            }
        }

    }

    @Test
    fun `favourite is updated in local data source`() {
        initVmWithNonPersistedBook()

        vm.book.observeForever(observer)

        vm.onFavouriteClicked()

        runBlocking {
            launch(Dispatchers.Main) {
                localDataSource.findByGoogleId("uOZhYgEACAAJ")?.favourite?.let {
                    Assert.assertTrue(it)
                }
            }
        }
    }

    @Test
    fun `collected is updated in local data source`() {
        initVmWithNonPersistedBook()

        vm.book.observeForever(observer)

        vm.onCollectedClicked()

        runBlocking {
            launch(Dispatchers.Main) {
                localDataSource.findByGoogleId("uOZhYgEACAAJ")?.collected?.let {
                    Assert.assertTrue(it)
                }
            }
        }
    }

    @Test
    fun `read is updated in local data source`() {
        initVmWithNonPersistedBook()

        vm.book.observeForever(observer)

        vm.onReadClicked()

        runBlocking {
            launch(Dispatchers.Main) {
                localDataSource.findByGoogleId("uOZhYgEACAAJ")?.read?.let {
                    Assert.assertTrue(it)
                }
            }
        }
    }
}