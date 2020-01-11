package com.jesuslcorominas.mybooks.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jesuslcorominas.mybooks.model.BookItem
import com.jesuslcorominas.mybooks.model.BookRepository
import com.jesuslcorominas.mybooks.ui.common.Event
import com.jesuslcorominas.mybooks.ui.common.ScopedViewModel
import kotlinx.coroutines.launch

class MainViewModel(private val booksRepository: BookRepository) : ScopedViewModel() {

    private val _books = MutableLiveData<List<BookItem>>()
    val books: LiveData<List<BookItem>> get() = _books

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _navigateToDetail = MutableLiveData<Event<String>>()
    val navigateToDetail: LiveData<Event<String>> get() = _navigateToDetail

    private val _requestLocationPermission = MutableLiveData<Event<Unit>>()
    val requestLocationPermission: LiveData<Event<Unit>> get() = _requestLocationPermission

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

    fun onBookClicked(book: BookItem) {
        _navigateToDetail.value = Event(book.id)
    }

    fun onSearch(query: String) {
        if (query.length == 0) {
            return;
        }

        launch {
            _loading.value = true
            _books.value = booksRepository.listBooks(query).items
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