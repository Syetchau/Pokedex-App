package com.example.pokemonpokedexapp

import android.annotation.SuppressLint
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
import com.example.pokemonpokedexapp.Adapter.PokemonListAdapter
import com.example.pokemonpokedexapp.Common.Common
import com.example.pokemonpokedexapp.Common.ItemOffsetDecoration
import com.example.pokemonpokedexapp.Model.Pokemon
import com.example.pokemonpokedexapp.Retrofit.IPokemonList
import com.example.pokemonpokedexapp.Retrofit.RetrofitClient
import com.mancj.materialsearchbar.MaterialSearchBar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_pokemon_list.*
import android.support.v7.widget.LinearLayoutManager




class PokemonList : Fragment() {

    internal var compositeDisposable = CompositeDisposable()
    internal var iPokemonList:IPokemonList
    internal lateinit var recyclerView: RecyclerView
    internal lateinit var adapter:PokemonListAdapter
    internal lateinit var searchAdapter:PokemonListAdapter
    internal var lastSuggest:MutableList<String> = ArrayList()
    internal lateinit var search_bar: MaterialSearchBar
    internal lateinit var return_top_btn: FloatingActionButton

    companion object{
        internal var instance:PokemonList?= null

        fun getInstance():PokemonList{
            if(instance == null)
                instance = PokemonList()
            return instance!!
        }
    }


    init {
        val retrofit = RetrofitClient.instance
        iPokemonList = retrofit.create(IPokemonList::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val itemView = inflater.inflate(R.layout.fragment_pokemon_list, container, false)

        recyclerView = itemView.findViewById(R.id.pokemon_recycler_view) as RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(activity,1)
        val itemDecoration = ItemOffsetDecoration(activity!!,R.dimen.spacing)
        recyclerView.addItemDecoration(itemDecoration)

        //return top btn
        return_top_btn = itemView.findViewById(R.id.return_top_btn) as FloatingActionButton
        return_top_btn.setOnClickListener{
            recyclerView.smoothScrollToPosition(0)
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    return_top_btn.show()
                } else {
                    return_top_btn.hide()
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })

        // Setup the searchBar
        search_bar = itemView.findViewById(R.id.search_bar) as MaterialSearchBar
        search_bar.setHint("Enter Pokemon Name")
        search_bar.setCardViewElevation(10)
        search_bar.addTextChangeListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                return_top_btn.hide()

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                return_top_btn.hide()

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                return_top_btn.hide()
                val suggest = ArrayList<String>()
                for(search in lastSuggest)
                    if(search.toLowerCase().contains(search_bar.text.toLowerCase()))
                        suggest.add(search)
                search_bar.lastSuggestions = suggest

            }
        })

        search_bar.setOnSearchActionListener(object :MaterialSearchBar.OnSearchActionListener{
            override fun onButtonClicked(buttonCode: Int) {
                return_top_btn.hide()

            }

            override fun onSearchStateChanged(enabled: Boolean) {
                return_top_btn.hide()
                if(!enabled)
                    pokemon_recycler_view.adapter = adapter
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                return_top_btn.hide()
                startSearch(text.toString())
            }
        })

        fetchData()

        return itemView
    }

    private fun fetchData() {
        compositeDisposable.add(iPokemonList.listPokemon
                                             .subscribeOn(Schedulers.io())
                                             .observeOn(AndroidSchedulers.mainThread())
                                             .subscribe{ pokedex -> Common.pokemonList = pokedex.pokemon!!
                                             adapter = PokemonListAdapter(activity!!,Common.pokemonList)
                                             recyclerView.adapter = adapter

                                             lastSuggest.clear()
                                             for(pokemon in Common.pokemonList)
                                                 lastSuggest.add(pokemon.name!!)
                                             search_bar.visibility = View.VISIBLE
                                             search_bar.lastSuggestions = lastSuggest
            }
        )
    }

    private fun startSearch(text: String) {
        return_top_btn.hide()
        if (Common.pokemonList.size >0) {
            val result = ArrayList<Pokemon>()
            for (pokemon in Common.pokemonList)
                if (pokemon.name!!.toLowerCase().contains(text.toLowerCase()))
                    result.add(pokemon)
            searchAdapter = PokemonListAdapter(activity!!,result)
            pokemon_recycler_view.adapter = searchAdapter
        }
    }
}
