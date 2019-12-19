package com.jesuslcorominas.mybooks.ui.main

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.antonioleiva.mymovies.ui.main.MainViewModel
import com.antonioleiva.mymovies.ui.main.MainViewModelFactory
import com.jesuslcorominas.mybooks.R
import com.jesuslcorominas.mybooks.model.BookItem
import com.jesuslcorominas.mybooks.model.BookRepository
import com.jesuslcorominas.mybooks.ui.common.hideKeyboard
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: BooksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(
            this,
            MainViewModelFactory(BookRepository(this))
        )[MainViewModel::class.java]

        imageButtonSearch.setOnClickListener { onSearch() }

        editTextSearch.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                onSearch()
                return@OnKeyListener true
            }
            false
        })

        adapter = BooksAdapter(viewModel::onBookClicked)
        recyclerViewItems.adapter = adapter
        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: MainViewModel.UiModel) {
        progress.visibility =
            if (model is MainViewModel.UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {
            is MainViewModel.UiModel.Content -> updateBooks(model.books)
            is MainViewModel.UiModel.Navigation -> TODO("Implementar navegacion a detalle")
        }
    }

    private fun updateBooks(books: List<BookItem>) {
        adapter.items = books

        hideKeyboard()
    }

    private fun onSearch() {
        viewModel.onSearch(editTextSearch.text.toString())
        hideKeyboard()
    }
}
