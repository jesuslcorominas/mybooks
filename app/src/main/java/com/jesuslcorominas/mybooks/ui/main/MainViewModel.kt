package com.antonioleiva.mymovies.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.antonioleiva.mymovies.ui.common.Scope
import com.jesuslcorominas.mybooks.model.BookItem
import com.jesuslcorominas.mybooks.model.BookRepository
import kotlinx.coroutines.launch

class MainViewModel(private val booksRepository: BookRepository) : ViewModel(),
    Scope by Scope.Impl() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) clear()
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val books: List<BookItem>) : UiModel()
        class Navigation(val book: BookItem) : UiModel()
    }

    init {
        initScope()
    }

    private fun clear() {
        UiModel.Content(ArrayList())
    }

    fun onBookClicked(book: BookItem) {
        _model.value = UiModel.Navigation(book)
    }

    fun onSearch(query: String) {
        if (query.length == 0) {
            return;
        }

        launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(booksRepository.listBooks(query).items)
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