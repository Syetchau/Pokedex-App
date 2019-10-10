package com.example.pokemonpokedexapp


import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.pokemonpokedexapp.Adapter.PokemonListAdapter
import com.example.pokemonpokedexapp.Adapter.PokemonTypeListAdapter
import com.example.pokemonpokedexapp.Common.Common
import com.example.pokemonpokedexapp.Common.ItemOffsetDecoration
import com.example.pokemonpokedexapp.Model.Pokemon
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.fragment_pokemon_list.*

/**
 * A simple [Fragment] subclass.
 */
class PokemonType : Fragment() {

    internal lateinit var recyclerView: RecyclerView
    internal lateinit var adapter: PokemonTypeListAdapter
    internal lateinit var searchAdapter: PokemonTypeListAdapter
    internal var lastSuggest:MutableList<String> = ArrayList()
    internal lateinit var search_bar: MaterialSearchBar
    internal lateinit var typeList:List<Pokemon>

    companion object{
        internal var instance:PokemonType?= null

        fun getInstance():PokemonType{
            if(instance == null)
                instance = PokemonType()
            return instance!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val itemView = inflater.inflate(R.layout.fragment_pokemon_type, container, false)

        recyclerView = itemView.findViewById(R.id.pokemon_recycler_view) as RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(activity,2)
        val itemDecoration = ItemOffsetDecoration(activity!!,R.dimen.spacing)
        recyclerView.addItemDecoration(itemDecoration)

        search_bar = itemView.findViewById(R.id.search_bar) as MaterialSearchBar
        search_bar.setHint("Enter Pokemon Name")
        search_bar.setCardViewElevation(10)
        search_bar.addTextChangeListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val suggest = ArrayList<String>()
                for(search in lastSuggest)
                    if(search.toLowerCase().contains(search_bar.text.toLowerCase()))
                        suggest.add(search)
                search_bar.lastSuggestions = suggest

            }
        })

        search_bar.setOnSearchActionListener(object :MaterialSearchBar.OnSearchActionListener{
            override fun onButtonClicked(buttonCode: Int) {

            }

            override fun onSearchStateChanged(enabled: Boolean) {
                if(!enabled)
                    pokemon_recycler_view.adapter = adapter

            }

            override fun onSearchConfirmed(text: CharSequence?) {
                startSearch(text.toString())
            }
        })

        if(arguments != null)
        {
            val type = arguments!!.getString("type")
            if (type != null)
            {
                typeList = Common.findPokemonByType(type)
                adapter = PokemonTypeListAdapter(activity!!,typeList)
                recyclerView.adapter = adapter

                loadSuggest()
            }
        }

        return itemView
    }

    private fun loadSuggest() {
        lastSuggest.clear()
        if (typeList.size >0)
            for (pokemon in typeList)
                lastSuggest.add(pokemon.name!!)
        search_bar.lastSuggestions = lastSuggest
    }

    private fun startSearch(text: String) {
        if (typeList.size >0) {
            val result = ArrayList<Pokemon>()
            for (pokemon in typeList )
                if (pokemon.name!!.toLowerCase().contains(text.toLowerCase()))
                    result.add(pokemon)
            searchAdapter = PokemonTypeListAdapter(activity!!,result)
            pokemon_recycler_view.adapter = searchAdapter
        }
    }
}
