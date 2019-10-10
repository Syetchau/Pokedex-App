package com.example.pokemonpokedexapp.Model

class Pokemon {

    var id:Int =0
    var num:String? = null
    var name:String? = null
    var img:String? = null
    var type:List<String>? = null
    var height:String? = null
    var weight:String? = null
    var candy:String? = null
    var candyCount:Int =0
    var egg:String? = null
    var spawnChance:Double? = 0.toDouble()
    var avgSpawns: Double? = 0.toDouble()
    var spawnTime: String? = null
    var multipliers:List<Double>? = null
    var weaknesses: List<String>? = null
    var next_evolution: List<Evolution>? = null
    var prev_evolution: List<Evolution>? = null
}