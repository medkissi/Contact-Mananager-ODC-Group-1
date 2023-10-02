package com.medkissi.contactmanagergroupe1.ui

import ContactViewModel
import SearchAdapter
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.medkissi.contactmanagergroupe1.R
import com.medkissi.contactmanagergroupe1.adapters.ContactAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class Research: AppCompatActivity() {

    private lateinit var svSearch: EditText
    private lateinit var rvSearch: RecyclerView
    private lateinit var adapter: SearchAdapter

    val viewModel: ContactViewModel by viewModels()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_research)

        svSearch = findViewById(R.id.ettSearch)
        svSearch.requestFocus()
        rvSearch = findViewById(R.id.rvSearch)

        adapter = SearchAdapter{ selectedContact ->
            // Naviguez vers l'écran de détail de la note avec les données de la note sélectionnée
            val intent = Intent(this, DetailsActivty::class.java)
            intent.putExtra(CONTACT_TO_SAVE, selectedContact)
            startActivity(intent)
        }
        // retourner en arriere
        val btnRoolBack = findViewById<ImageButton>(R.id.baSearch)
            .setOnClickListener {
                finish()
            }

        rvSearch.layoutManager = LinearLayoutManager(this)
        rvSearch.adapter = adapter

        svSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Ne rien faire après la saisie
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim().toLowerCase(Locale.getDefault())
                if (query.isBlank()) {
                    adapter.submitList(emptyList())
                } else {
                    performSearch(query)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Ne rien faire après la saisie
            }
        })
    }

    private fun performSearch(query: String) {
        if (query.isBlank()) {
            // Si la chaîne de recherche est vide, videz la liste des résultats
            adapter.submitList(emptyList())
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val searchResults = viewModel.getAllSearchedContact(query)
                withContext(Dispatchers.Main) {
                    adapter.submitList(searchResults)
                }
            }
        }
    }
}