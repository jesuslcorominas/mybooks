package com.jesuslcorominas.mybooks.ui.common

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.jesuslcorominas.mybooks.R


@BindingAdapter("visible")
fun ProgressBar.setLoading(visible: Boolean?) {
    visibility = visible?.let { if (visible) View.VISIBLE else View.GONE } ?: View.GONE
}

@BindingAdapter("thumbnail")
fun ImageView.loadThumbnail(thumbnail: String?) {
    if (thumbnail != null && !thumbnail.isEmpty()) {
        loadUrl(thumbnail)
    } else {
        setImageResource(R.drawable.ic_question)
    }
}