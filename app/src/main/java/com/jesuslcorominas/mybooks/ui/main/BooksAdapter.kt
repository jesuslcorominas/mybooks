package com.jesuslcorominas.mybooks.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jesuslcorominas.mybooks.R
import com.jesuslcorominas.mybooks.model.BookItem
import com.jesuslcorominas.mybooks.ui.common.basicDiffUtil
import com.jesuslcorominas.mybooks.ui.common.inflate
import com.jesuslcorominas.mybooks.ui.common.loadUrl
import kotlinx.android.synthetic.main.item_book.view.*

class BooksAdapter(private val listener: (BookItem) -> Unit) :
    RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    var items: List<BookItem> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_book, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = items[position]
        holder.bind(book)
        holder.itemView.setOnClickListener { listener(book) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(book: BookItem) {
            itemView.textViewTitle.text = book.volumeInfo?.title

            if (book.volumeInfo?.imageLinks?.thumbnail != null) {
                itemView.imageViewCoverPage.loadUrl(book.volumeInfo.imageLinks.thumbnail)
            } else {
                itemView.imageViewCoverPage.setImageResource(R.drawable.ic_question)
            }
        }
    }
}