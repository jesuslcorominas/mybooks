package com.jesuslcorominas.mybooks.ui.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.jesuslcorominas.mybooks.R
import com.jesuslcorominas.mybooks.databinding.ActivityMainBinding
import com.jesuslcorominas.mybooks.model.BookRepository
import com.jesuslcorominas.mybooks.model.framework.PermissionRequester
import com.jesuslcorominas.mybooks.ui.common.*
import com.jesuslcorominas.mybooks.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // TODO habria que hacer una vista de "sin libros almacenados"
    //  y con un livedata que se mostrase u ocultase. Tambien una vista de error

    // TODO dar opción de volver a mostrar los libros almacenados, un botón
    //  limpiar o algo así

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: BooksAdapter

    private val coarsePermissionRequester = PermissionRequester(this, ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModel { MainViewModel(BookRepository(app)) }

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        adapter = BooksAdapter(viewModel::onBookClicked)
        binding.recyclerViewItems.adapter = adapter

        imageButtonSearch.setOnClickListener { onSearch() }

        viewModel.navigateToDetail.observe(this, EventObserver { googleId ->
            startActivity<DetailActivity> { putExtra(DetailActivity.GOOGLE_ID, googleId) }
        })

        editTextSearch.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                onSearch()
                return@OnKeyListener true
            }
            false
        })

        viewModel.requestLocationPermission.observe(this, EventObserver {
            coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        })

        viewModel.hideKeyboard.observe(this, Observer { hideKeyboard() })
    }

    private fun onSearch() {
        viewModel.onSearch(editTextSearch.text.toString())
    }
}
