package com.example.pokemonpokedexapp.Adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.pokemonpokedexapp.Common.Common
import com.example.pokemonpokedexapp.Interface.IItemClickLisener
import com.example.pokemonpokedexapp.Model.Pokemon
import com.example.pokemonpokedexapp.R

class PokemonTypeListAdapter(internal var context: Context,
                             internal var pokemonList: List<Pokemon>):RecyclerView.Adapter<PokemonTypeListAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.pokemon_type_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context)
            .load(pokemonList[position].img)
            .into(holder.img_pokemon)

        holder.pokemon_id.text = pokemonList[position].id.toString()

        holder.setItemClickListener(object : IItemClickLisener {
            override fun onClick(view: View, position: Int) {
                LocalBroadcastManager.getInstance(context)
                    .sendBroadcast(
                        Intent(Common.KEY_ENABLE_HOME)
                            .putExtra("num",pokemonList[position].num))
            }
        })
    }

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        internal var img_pokemon: ImageView
        internal var pokemon_id: TextView

        internal var itemClickLisener: IItemClickLisener?= null

        fun setItemClickListener(iItemClickLisener: IItemClickLisener)
        {
            this.itemClickLisener = iItemClickLisener
        }

        init {
            img_pokemon = itemView.findViewById(R.id.pokemon_image) as ImageView
            pokemon_id = itemView.findViewById(R.id.pokemon_id) as TextView

            itemView.setOnClickListener{view -> itemClickLisener!!.onClick(view,adapterPosition)}
        }
    }
}