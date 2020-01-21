package com.jesuslcorominas.mybooks.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jesuslcorominas.mybooks.R
import com.jesuslcorominas.mybooks.databinding.ItemBookBinding
import com.jesuslcorominas.mybooks.domain.Book
import com.jesuslcorominas.mybooks.ui.common.basicDiffUtil
import com.jesuslcorominas.mybooks.ui.common.bindingInflate

class BooksAdapter(private val listener: (Book) -> Unit) :
    RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    var items: List<Book> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.bindingInflate(R.layout.item_book, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = items[position]
        holder.dataBinding.book = book

        holder.itemView.setOnClickListener { listener(book) }
    }

    class ViewHolder(val dataBinding: ItemBookBinding) : RecyclerView.ViewHolder(dataBinding.root)
}