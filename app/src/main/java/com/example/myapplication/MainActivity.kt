package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bookAdapter: BookAdapter
    private val books = mutableListOf<BookData>(
        BookData("Harry"),
        BookData("Rahul"),
        BookData("Doordarshan"),
        BookData("Simba"),
        BookData("Japan"),
        BookData ("Paras"),
        BookData ("Bro"),
        BookData ("Arjun"),
        BookData ("DSA"),
        BookData ("CP"),
        BookData ("Redux"),
        BookData ("Kotlin")

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        bookAdapter = BookAdapter(books) { book ->
            saveFavorites()
            sortBooks()
        }
        recyclerView.adapter = bookAdapter

        loadFavorites()
        sortBooks()
    }

    private fun sortBooks() {
        books.sortWith(compareByDescending<BookData> { it.isFavorite }.thenBy { it.name })
        bookAdapter.updateBooks(books)
    }

    private fun saveFavorites() {
        val sharedPreferences = getSharedPreferences("book_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val favoriteSet = books.filter { it.isFavorite }.map { it.name }.toSet()
        editor.putStringSet("favorites", favoriteSet)
        editor.apply()
    }

    private fun loadFavorites() {
        val sharedPreferences = getSharedPreferences("book_prefs", Context.MODE_PRIVATE)
        val favoriteSet = sharedPreferences.getStringSet("favorites", emptySet())
        books.forEach { it.isFavorite = it.name in favoriteSet.orEmpty() }
    }
}
