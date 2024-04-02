import android.os.Bundle
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.codepath.asynchttpclient.Headers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PokemonAdapter(mutableListOf())
        recyclerView.adapter = adapter

        // Fetch data for each Pokémon ID
        for (pokemonId in 1..898) {
            fetchPokemonById(pokemonId)
        }
    }

    private fun fetchPokemonById(pokemonId: Int) {
        val pokemonUrl = "https://pokeapi.co/api/v2/pokemon/$pokemonId"

        val client = AsyncHttpClient()
        client.get(pokemonUrl, object: JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: List<Headers>?, response: JsonHttpResponseHandler.JSON?) {
                try {
                    if (response is JSONObject) {
                        val name = response.getString("name").capitalize()
                        val typesArray = response.getJSONArray("types")
                        val type = typesArray.getJSONObject(0).getJSONObject("type").getString("name").capitalize()
                        val hp = response.getJSONArray("stats").getJSONObject(0).getInt("base_stat")

                        val pokemon = Pokemon(name, type, hp)
                        updateRecyclerView(pokemon)
                    } else {
                        Log.e("MainActivity", "Unexpected response format: $response")
                    }
                } catch (e: JSONException) {
                    Log.e("MainActivity", "Error parsing JSON: ${e.message}")
                }
            }

            override fun onFailure(statusCode: Int, headers: List<Headers>?, response: String?, throwable: Throwable?) {
                Log.e("MainActivity", "Failed to fetch Pokémon data for ID $pokemonId: ${throwable?.message}")
            }


        })
    }

    private fun updateRecyclerView(pokemon: Pokemon) {
        GlobalScope.launch(Dispatchers.Main) {
            adapter.addPokemon(pokemon)
        }
    }
}

class Pokemon(name: String, type: String, hp: Int) {

}
