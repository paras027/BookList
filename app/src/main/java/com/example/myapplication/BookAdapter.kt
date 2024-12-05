package com.example.myapplication

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookAdapter(
    private var books: MutableList<BookData>,
    private val onFavoriteToggle: (BookData) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    inner class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.bookNameTextView)

        val imagebuton: ImageView = view.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]

        holder.nameTextView.text = book.name
        holder.imagebuton.setImageResource(
            if (book.isFavorite) R.drawable.baseline_check_box_24 else R.drawable.baseline_check_box_outline_blank_24
        )

        holder.imagebuton.setOnClickListener {
            book.isFavorite = !book.isFavorite
            onFavoriteToggle(book)
        }
    }

    override fun getItemCount(): Int = books.size

    fun updateBooks(newBooks: List<BookData>) {
        books = newBooks.toMutableList()
        notifyDataSetChanged()
    }
}
