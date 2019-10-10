package com.example.pokemonpokedexapp.Adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pokemonpokedexapp.Common.Common
import com.example.pokemonpokedexapp.R
import com.robertlevonyan.views.chip.Chip

class PokemonTypeAdapter(internal var context: Context,
                         internal var typeList:List<String>):RecyclerView.Adapter<PokemonTypeAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.chip_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return typeList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.chip.chipText = typeList[position]
        holder.chip.changeBackgroundColor(Common.getColorByType(typeList[position]))
    }

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        internal var chip:Chip

        init {
            chip = itemView.findViewById(R.id.chip) as Chip
            chip.setOnChipClickListener{
                LocalBroadcastManager.getInstance(context)
                    .sendBroadcast(
                        Intent(Common.KEY_POKEMON_TYPE)
                            .putExtra("type",typeList[adapterPosition]))
            }
        }
    }
}