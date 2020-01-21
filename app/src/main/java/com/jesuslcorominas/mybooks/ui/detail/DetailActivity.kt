package com.jesuslcorominas.mybooks.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jesuslcorominas.mybooks.R
import com.jesuslcorominas.mybooks.databinding.ActivityDetailBinding
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailActivity : AppCompatActivity() {

    companion object {
        const val GOOGLE_ID = "DetailActivity:book:google_id"
    }

    private val viewModel: DetailViewModel by currentScope.viewModel(this) {
        parametersOf(intent.getStringExtra(GOOGLE_ID) ?: "")
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
    }
}