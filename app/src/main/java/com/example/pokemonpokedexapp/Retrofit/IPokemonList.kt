package com.example.pokemonpokedexapp.Retrofit

import com.example.pokemonpokedexapp.Model.Pokedex
import io.reactivex.Observable
import retrofit2.http.GET

interface IPokemonList {
    @get:GET("pokedex.json")
    val listPokemon:Observable<Pokedex>
}