package com.jesuslcorominas.mybooks.ui.detail

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.jesuslcorominas.mybooks.R
import com.jesuslcorominas.mybooks.ui.common.loadUrl

@BindingAdapter("loading")
fun ProgressBar.setLoading(loading: Boolean?) {
    visibility = loading?.let { if (loading) View.VISIBLE else View.GONE } ?: View.GONE
}

@BindingAdapter("thumbnail")
fun ImageView.loadThumbnail(thumbnail: String?) {
    if (thumbnail != null && !thumbnail.isEmpty()) {
        loadUrl(thumbnail)
    } else {
        setImageResource(R.drawable.ic_question)
    }
}