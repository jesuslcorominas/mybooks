package com.jesuslcorominas.mybooks.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jesuslcorominas.mybooks.model.database.Book

@BindingAdapter("items")
fun RecyclerView.setItems(books: List<Book>?) {
    (adapter as? BooksAdapter)?.let {
        it.items = books ?: emptyList()
    }
}