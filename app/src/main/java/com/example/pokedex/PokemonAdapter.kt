import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.Pokemon

class PokemonAdapter(private val pokemonList: MutableList<Pokemon>) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.pokemon_name)
        val typeTextView: TextView = itemView.findViewById(R.id.pokemon_type)
        val hpTextView: TextView = itemView.findViewById(R.id.pokemon_hp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_item_layout, parent, false)
        return PokemonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val currentPokemon = pokemonList[position]
        holder.nameTextView.text = currentPokemon.name
        holder.typeTextView.text = currentPokemon.type
        holder.hpTextView.text = currentPokemon.hp.toString()
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    fun addPokemon(pokemon: Pokemon) {
        pokemonList.add(pokemon)
        notifyDataSetChanged()
    }
}
