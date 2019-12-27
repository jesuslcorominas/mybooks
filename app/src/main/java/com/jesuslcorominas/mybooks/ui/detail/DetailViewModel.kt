package com.jesuslcorominas.mybooks.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.antonioleiva.mymovies.ui.common.Scope
import com.jesuslcorominas.mybooks.model.BookItem
import com.jesuslcorominas.mybooks.model.BookRepository
import kotlinx.coroutines.launch


class DetailViewModel(private val booksRepository: BookRepository) :
    ViewModel(),
    Scope by Scope.Impl() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val book: BookItem) : UiModel()
    }

    init {
        initScope()
    }

    fun onCreated(id: String) {
        launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(booksRepository.detailBook(id))
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