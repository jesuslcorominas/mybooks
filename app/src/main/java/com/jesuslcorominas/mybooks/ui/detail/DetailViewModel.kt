package com.jesuslcorominas.mybooks.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jesuslcorominas.mybooks.model.BookRepository
import com.jesuslcorominas.mybooks.model.database.Book
import com.jesuslcorominas.mybooks.ui.common.ScopedViewModel
import kotlinx.coroutines.launch


class DetailViewModel(private val booksRepository: BookRepository) : ScopedViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _book = MutableLiveData<Book>()
    val book: LiveData<Book> get() = _book

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    private val _thumbnail = MutableLiveData<String>()
    val thumbnail: LiveData<String> get() = _thumbnail

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> get() = _description

    private val _favourite = MutableLiveData<Boolean>()
    val favourite: LiveData<Boolean> get() = _favourite

    private val _collected = MutableLiveData<Boolean>()
    val collected: LiveData<Boolean> get() = _collected

    private val _read = MutableLiveData<Boolean>()
    val read: LiveData<Boolean> get() = _read

    private val _infoLink = MutableLiveData<String>()
    val infoLink: LiveData<String> get() = _infoLink


    init {
        initScope()
    }

    fun onCreated(googleId: String) {
        launch {
            _loading.value = true
            _book.value = booksRepository.detailBook(googleId)
            updateUI()
            _loading.value = false
        }
    }

    private fun updateUI() {
        _book.value?.run {
            _title.value = this.title
            _description.value = this.description
            _infoLink.value = this.infoLink
            _thumbnail.value = this.thumbnail
            _favourite.value = this.favourite
            _collected.value = this.collected
            _read.value = this.read
        }
    }

    fun onFavouriteClicked() {
        launch {
            book.value?.let {
                persistAndUpdateUI(it.copy(favourite = !it.favourite))
            }
        }
    }

    fun onCollectedClicked() {
        launch {
            book.value?.let {
                persistAndUpdateUI(it.copy(collected = !it.collected))
            }
        }
    }

    fun onReadClicked() {
        launch {
            book.value?.let {
                persistAndUpdateUI(it.copy(read = !it.read))
            }
        }
    }

    private suspend fun persistAndUpdateUI(updatedBook: Book) {
        _book.value = updatedBook
        updateUI()
        booksRepository.persistBook(updatedBook)
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