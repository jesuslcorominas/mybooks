package com.jesuslcorominas.mybooks.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jesuslcorominas.mybooks.model.BookItem

@BindingAdapter("items")
fun RecyclerView.setItems(books: List<BookItem>?) {
    (adapter as? BooksAdapter)?.let {
        it.items = books ?: emptyList()
    }
}