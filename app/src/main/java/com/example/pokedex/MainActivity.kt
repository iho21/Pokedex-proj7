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


    private var pokemonName: String = ""
    private var pokemonType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokemonNameTextView = findViewById(R.id.pokemonName)
        typeTextView = findViewById(R.id.type)
        loadPokemonButton = findViewById(R.id.loadPokemonButton)


        pokemonNameTextView.text = "Name: $pokemonName"
        typeTextView.text = "Type: $pokemonType"

        loadPokemonButton.setOnClickListener {

            fetchRandomPokemon()
        }
    }

    private fun updatePokemonData() {
        // Update TextViews with the new Pokemon data
        pokemonNameTextView.text = "Name: $pokemonName"
        typeTextView.text = "Type: $pokemonType"
    }

    private fun fetchRandomPokemon() {
        val randomPokemonId = (1..898).random()
        val randomPokemonUrl = "https://pokeapi.co/api/v2/pokemon/$randomPokemonId"

        val client = AsyncHttpClient()
        client.get(randomPokemonUrl, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, response: JSON) {
                try {

                    val name = response.jsonObject.getString("name").capitalize()
                    val types = response.jsonObject.getJSONArray("types")
                    val type = types.getJSONObject(0).getJSONObject("type").getString("name").capitalize()


                    pokemonName = name
                    pokemonType = type
                    updatePokemonData()
                } catch (e: JSONException) {
                    Log.e("MainActivity", "Error parsing JSON: ${e.message}")
                }
            }

            override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
                Log.e("MainActivity", "Failed to fetch Pokémon data: ${throwable?.message}")
            }
        })
    }

}

