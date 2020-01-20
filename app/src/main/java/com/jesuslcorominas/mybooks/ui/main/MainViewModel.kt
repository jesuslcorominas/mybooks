package com.jesuslcorominas.mybooks.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jesuslcorominas.mybooks.model.BookRepository
import com.jesuslcorominas.mybooks.model.database.Book
import com.jesuslcorominas.mybooks.model.toBook
import com.jesuslcorominas.mybooks.ui.common.Event
import com.jesuslcorominas.mybooks.ui.common.ScopedViewModel
import kotlinx.coroutines.launch

class MainViewModel(private val booksRepository: BookRepository) : ScopedViewModel() {

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> get() = _books

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _navigateToDetail = MutableLiveData<Event<String>>()
    val navigateToDetail: LiveData<Event<String>> get() = _navigateToDetail

    private val _requestLocationPermission = MutableLiveData<Event<Unit>>()
    val requestLocationPermission: LiveData<Event<Unit>> get() = _requestLocationPermission

    private val _hideKeyboard = MutableLiveData<Unit>()
    val hideKeyboard: LiveData<Unit> = _hideKeyboard

    init {
        initScope()
        refresh()
    }

    private fun refresh() {
        _requestLocationPermission.value = Event(Unit)
    }

    private fun clear() {
        _books.value = ArrayList()
    }

    fun onBookClicked(book: Book) {
        _navigateToDetail.value = Event(book.googleId)
    }

    fun onSearch(query: String) {
        _hideKeyboard.value = Unit

        if (query.length == 0) {
            return;
        }

        launch {
            _loading.value = true
            _books.value =
                booksRepository.listBooks(query).items.map { it.toBook() }
            _loading.value = false
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val booksRepository: BookRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MainViewModel(booksRepository) as T
}