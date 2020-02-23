package com.jesuslcorominas.mybooks.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jesuslcorominas.mybooks.domain.Book
import com.jesuslcorominas.mybooks.ui.common.ScopedViewModel
import com.jesuslcorominas.mybooks.usecases.GetBookDetail
import com.jesuslcorominas.mybooks.usecases.ToggleCollectedBook
import com.jesuslcorominas.mybooks.usecases.ToggleFavouriteBook
import com.jesuslcorominas.mybooks.usecases.ToggleReadBook
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch


class DetailViewModel(
    private val googleId: String,
    private val toggleCollectedBook: ToggleCollectedBook,
    private val toggleFavouriteBook: ToggleFavouriteBook,
    private val toggleReadBook: ToggleReadBook,
    private val getBookDetail: GetBookDetail,
    override val uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

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

        getDetail()
    }

    fun getDetail() {
        launch {
            _loading.value = true
            _book.value = getBookDetail.invoke(googleId)
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
                _book.value = toggleFavouriteBook.invoke(it)
                updateUI()
            }
        }
    }

    fun onCollectedClicked() {
        launch {
            book.value?.let {
                _book.value = toggleCollectedBook.invoke(it)
                updateUI()
            }
        }
    }

    fun onReadClicked() {
        launch {
            book.value?.let {
                _book.value = toggleReadBook.invoke(it)
                updateUI()
            }
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}