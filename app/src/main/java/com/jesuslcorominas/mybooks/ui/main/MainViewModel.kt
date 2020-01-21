package com.jesuslcorominas.mybooks.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jesuslcorominas.mybooks.domain.Book
import com.jesuslcorominas.mybooks.ui.common.Event
import com.jesuslcorominas.mybooks.ui.common.ScopedViewModel
import com.jesuslcorominas.mybooks.usecases.FindBooks
import com.jesuslcorominas.mybooks.usecases.GetStoredBooks
import kotlinx.coroutines.launch

class MainViewModel(
    private val getStoredBooks: GetStoredBooks,
    private val findBooks: FindBooks
) : ScopedViewModel() {

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

    fun onCoarsePermissionRequested() {
        launch {
            _loading.value = true
            _books.value = getStoredBooks.invoke()
            _loading.value = false
        }
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
            _books.value = findBooks.invoke(query)
            _loading.value = false
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}