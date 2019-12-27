package com.jesuslcorominas.mybooks.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jesuslcorominas.mybooks.R
import com.jesuslcorominas.mybooks.model.BookItem
import com.jesuslcorominas.mybooks.model.BookRepository
import com.jesuslcorominas.mybooks.model.VolumeInfo
import com.jesuslcorominas.mybooks.ui.common.loadUrl
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val BOOK_ID = "DetailActivity:book:id"
    }

    private lateinit var viewModel: DetailViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewModel = ViewModelProviders.of(
            this,
            DetailViewModelFactory(BookRepository(this))
        )[DetailViewModel::class.java]

        viewModel.model.observe(this, Observer(::updateUi))

        viewModel.onCreated(intent.getStringExtra(BOOK_ID))
    }

    private fun updateUi(model: DetailViewModel.UiModel) {
        progress.visibility =
            if (model is DetailViewModel.UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {
            is DetailViewModel.UiModel.Content -> {
                val book: BookItem = model.book

                val volumeInfo: VolumeInfo? = book.volumeInfo

                toolbarDetail.title = volumeInfo?.title

                if (volumeInfo != null && volumeInfo.imageLinks != null && !volumeInfo.imageLinks.thumbnail.isEmpty()) {
                    imageViewCoverPage.loadUrl(volumeInfo.imageLinks.thumbnail)
                } else {
                    imageViewCoverPage.setImageResource(R.drawable.ic_question)
                }

                textViewSummary.text = volumeInfo?.description
                textViewInfo.text = volumeInfo?.infoLink
            }
        }
    }
}