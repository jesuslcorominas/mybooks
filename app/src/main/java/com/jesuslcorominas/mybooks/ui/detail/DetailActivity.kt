package com.jesuslcorominas.mybooks.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jesuslcorominas.mybooks.R
import com.jesuslcorominas.mybooks.data.repository.BooksRepository
import com.jesuslcorominas.mybooks.databinding.ActivityDetailBinding
import com.jesuslcorominas.mybooks.model.database.RoomBooksDatasource
import com.jesuslcorominas.mybooks.model.server.GoogleBooks
import com.jesuslcorominas.mybooks.model.server.GoogleBooksDatasource
import com.jesuslcorominas.mybooks.ui.common.app
import com.jesuslcorominas.mybooks.ui.common.getViewModel
import com.jesuslcorominas.mybooks.usecases.GetBookDetail
import com.jesuslcorominas.mybooks.usecases.ToggleCollectedBook
import com.jesuslcorominas.mybooks.usecases.ToggleFavouriteBook
import com.jesuslcorominas.mybooks.usecases.ToggleReadBook

class DetailActivity : AppCompatActivity() {

    companion object {
        const val GOOGLE_ID = "DetailActivity:book:google_id"
    }

    private lateinit var viewModel: DetailViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val googleBooks = GoogleBooks()
        val remoteDatasource = GoogleBooksDatasource(googleBooks)

        val localDatasource = RoomBooksDatasource(app.db)

        val booksRepository = BooksRepository(localDatasource, remoteDatasource)

        val toggleReadBook = ToggleReadBook(booksRepository)
        val toggleFavouriteBook = ToggleFavouriteBook(booksRepository)
        val toggleCollectedBook = ToggleCollectedBook(booksRepository)
        val getBookDetail = GetBookDetail(booksRepository)

        viewModel = getViewModel {
            DetailViewModel(
                toggleCollectedBook,
                toggleFavouriteBook,
                toggleReadBook,
                getBookDetail
            )
        }

        val binding: ActivityDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        viewModel.onCreated(intent.getStringExtra(GOOGLE_ID) ?: "")
    }
}