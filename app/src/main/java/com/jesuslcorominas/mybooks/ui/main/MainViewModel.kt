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
            if (_model.value == null) refresh("ring")
            return _model
        }

    // TODO crear un tipo para realizar la busqueda. Ahora mismo se esta
    //  hardcodeando el termindo de busqueda
    sealed class UiModel {
        object Loading : UiModel()
        class Content(val books: List<BookItem>) : UiModel()
        class Navigation(val book: BookItem) : UiModel()
    }

    init {
        initScope()
    }

    private fun refresh(query: String) {
        launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(booksRepository.listBooks(query).items)
        }
    }

    fun onBookClicked(book: BookItem) {
        _model.value = UiModel.Navigation(book)
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