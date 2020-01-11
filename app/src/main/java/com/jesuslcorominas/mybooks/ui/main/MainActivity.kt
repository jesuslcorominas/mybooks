package com.jesuslcorominas.mybooks.ui.main

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jesuslcorominas.mybooks.R
import com.jesuslcorominas.mybooks.databinding.ActivityMainBinding
import com.jesuslcorominas.mybooks.model.BookRepository
import com.jesuslcorominas.mybooks.ui.common.EventObserver
import com.jesuslcorominas.mybooks.ui.common.getViewModel
import com.jesuslcorominas.mybooks.ui.common.hideKeyboard
import com.jesuslcorominas.mybooks.ui.common.startActivity
import com.jesuslcorominas.mybooks.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: BooksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModel { MainViewModel(BookRepository(this)) }

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        adapter = BooksAdapter(viewModel::onBookClicked)
        binding.recyclerViewItems.adapter = adapter

        imageButtonSearch.setOnClickListener { onSearch() }

        viewModel.navigateToDetail.observe(this, EventObserver { id ->
            startActivity<DetailActivity> { putExtra(DetailActivity.BOOK_ID, id) }
        })

        editTextSearch.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                onSearch()
                return@OnKeyListener true
            }
            false
        })
    }

    private fun onSearch() {
        viewModel.onSearch(editTextSearch.text.toString())
        hideKeyboard()
    }
}
