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
    private lateinit var hpTextView: TextView
    private lateinit var loadPokemonButton: Button


    private var pokemonName: String = ""
    private var pokemonType: String = ""
    private var pokemonHP: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokemonNameTextView = findViewById(R.id.pokemonName)
        typeTextView = findViewById(R.id.type)
        hpTextView = findViewById(R.id.hpTextView)
        loadPokemonButton = findViewById(R.id.loadPokemonButton)


        pokemonNameTextView.text = "Name: $pokemonName"
        typeTextView.text = "Type: $pokemonType"
        hpTextView.text = "HP: $pokemonHP"

        loadPokemonButton.setOnClickListener {

            fetchRandomPokemon()
        }
    }

    private fun updatePokemonData(name: String, type: String, hp: Int) {

        pokemonNameTextView.text = "Name: $name"
        typeTextView.text = "Type: $type"
        hpTextView.text = "HP: $hp"
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
                    val hp = response.jsonObject.getJSONArray("stats").getJSONObject(0).getInt("base_stat")

                    // Update the Pokemon data
                    updatePokemonData(name, type, hp)
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

