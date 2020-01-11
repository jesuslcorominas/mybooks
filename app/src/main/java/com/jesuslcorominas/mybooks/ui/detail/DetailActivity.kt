package com.jesuslcorominas.mybooks.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jesuslcorominas.mybooks.R
import com.jesuslcorominas.mybooks.databinding.ActivityDetailBinding
import com.jesuslcorominas.mybooks.model.BookRepository
import com.jesuslcorominas.mybooks.ui.common.getViewModel

class DetailActivity : AppCompatActivity() {

    companion object {
        const val BOOK_ID = "DetailActivity:book:id"
    }

    private lateinit var viewModel: DetailViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModel { DetailViewModel(BookRepository(this)) }

        val binding: ActivityDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        viewModel.onCreated(intent.getStringExtra(BOOK_ID))
    }
}