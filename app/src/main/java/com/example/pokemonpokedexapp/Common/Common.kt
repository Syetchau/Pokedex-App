package com.example.pokemonpokedexapp.Common

import android.graphics.Color
import com.example.pokemonpokedexapp.Model.Pokemon

object Common {
    fun findPokemonByNum(num: String?): Pokemon? {
        for(pokemon in Common.pokemonList)
            if (pokemon.num.equals(num))
                return pokemon
        return null
    }
    fun findPokemonByType(type: String): List<Pokemon> {
        var result = ArrayList<Pokemon>()
        for (pokemon in Common.pokemonList)
            if (pokemon.type!!.contains(type))
                result.add(pokemon)
        return result
    }

    fun getColorByType(type: String): Int {
        when (type) {

            "Normal" -> return Color.parseColor("#A8A77A")
            "Dragon" -> return Color.parseColor("#6F35FC")
            "Psychic" -> return Color.parseColor("#F95587")
            "Electric" -> return Color.parseColor("#F7D02C")
            "Ground" -> return Color.parseColor("#E2BF65")
            "Grass" -> return Color.parseColor("#7AC74C")
            "Poison" -> return Color.parseColor("#A33EA1")
            "Steel" -> return Color.parseColor("#B7B7CE")
            "Fairy" -> return Color.parseColor("#D685AD")
            "Fire" -> return Color.parseColor("#EE8130")
            "Fighting" -> return Color.parseColor("#C22E28")
            "Bug" -> return Color.parseColor("#A6B91A")
            "Ghost" -> return Color.parseColor("#735797")
            "Dark" -> return Color.parseColor("#705746")
            "Ice" -> return Color.parseColor("#96D9D6")
            "Water" -> return Color.parseColor("#6390F0")
            "Flying" -> return Color.parseColor("#A98FF3")
            "Rock" -> return Color.parseColor("#B6A136")

            else -> return Color.parseColor("#658FA0")
        }
    }


    var pokemonList:List<Pokemon> = ArrayList()
    val KEY_ENABLE_HOME = "position"
    val KEY_NUM_EVOLUTION = "evolution"
    val KEY_POKEMON_TYPE = "type"

}