package com.jesuslcorominas.mybooks.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jesuslcorominas.mybooks.model.BookItem
import com.jesuslcorominas.mybooks.model.BookRepository
import com.jesuslcorominas.mybooks.ui.common.ScopedViewModel
import kotlinx.coroutines.launch


class DetailViewModel(private val booksRepository: BookRepository) : ScopedViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _book = MutableLiveData<BookItem>()
    val book: LiveData<BookItem> get() = _book

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    private val _thumbnail = MutableLiveData<String>()
    val thumbnail: LiveData<String> get() = _thumbnail

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> get() = _description

    private val _infoLink = MutableLiveData<String>()
    val infoLink: LiveData<String> get() = _infoLink

    init {
        initScope()
    }

    fun onCreated(id: String) {
        launch {
            _loading.value = true
            _book.value = booksRepository.detailBook(id)
            updateUI()
            _loading.value = false
        }
    }

    private fun updateUI() {
        _book.value?.run {
            _title.value = volumeInfo?.title
            _description.value = volumeInfo?.description
            _infoLink.value = volumeInfo?.infoLink
            _thumbnail.value = volumeInfo?.imageLinks?.thumbnail
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val booksRepository: BookRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        DetailViewModel(booksRepository) as T
}