package com.example.pokedex

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    private lateinit var pokemonNameTextView: TextView
    private lateinit var typeTextView: TextView
    private lateinit var loadPokemonButton: Button

    // Variables to hold the Pokemon's name and type
    private var pokemonName: String = ""
    private var pokemonType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokemonNameTextView = findViewById(R.id.pokemonName)
        typeTextView = findViewById(R.id.type)
        loadPokemonButton = findViewById(R.id.loadPokemonButton)

        // Set initial values for Pokemon name and type
        pokemonNameTextView.text = "Name: $pokemonName"
        typeTextView.text = "Type: $pokemonType"

        loadPokemonButton.setOnClickListener {
            // Simulate fetching Pokemon data and update name and type
            pokemonName = "Pikachu" // Replace with actual Pokemon name
            pokemonType = "Electric" // Replace with actual Pokemon type

            // Update TextViews with the new Pokemon data
            updatePokemonData()
        }
    }

    private fun updatePokemonData() {
        // Update TextViews with the new Pokemon data
        pokemonNameTextView.text = "Name: $pokemonName"
        typeTextView.text = "Type: $pokemonType"
    }
}

