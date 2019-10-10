package com.example.pokemonpokedexapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.FragmentManager
import android.support.v4.content.LocalBroadcastManager
import android.view.MenuItem
import com.example.pokemonpokedexapp.Common.Common
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //create broadcast handle
    private val showDetail = object:BroadcastReceiver(){
        override fun onReceive(p0: Context?, intent: Intent?) {
            if(intent!!.action!!.toString() == Common.KEY_ENABLE_HOME)
            {
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                supportActionBar!!.setDisplayShowHomeEnabled(true)

                //replace fragment
                val detailFragment = PokemonDetail.getInstance()
                val num = intent.getStringExtra("num")
                val bundle = Bundle()
                var return_top_btn: FloatingActionButton
                bundle.putString("num",num)
                detailFragment.arguments = bundle

                val fragmentTranscation = supportFragmentManager.beginTransaction()
                fragmentTranscation.replace(R.id.pokemon_list_fragment,detailFragment)
                fragmentTranscation.addToBackStack("detail")
                fragmentTranscation.commit()
                return_top_btn = findViewById<FloatingActionButton>(R.id.return_top_btn)
                return_top_btn.hide()

                //set pokemon name for toolbar
                val pokemon = Common.findPokemonByNum(num)
                toolbar.title = pokemon!!.name
            }
        }
    }

    private val showEvolution = object:BroadcastReceiver(){
        override fun onReceive(p0: Context?, intent: Intent?) {
            if(intent!!.action!!.toString() == Common.KEY_NUM_EVOLUTION )
            {
                //replace fragment
                val detailFragment = PokemonDetail.getInstance()
                val bundle = Bundle()
                val num = intent.getStringExtra("num")
                var return_top_btn: FloatingActionButton
                bundle.putString("num",num)
                detailFragment.arguments = bundle

                val fragmentTranscation = supportFragmentManager.beginTransaction()
                fragmentTranscation.remove(detailFragment)
                fragmentTranscation.replace(R.id.pokemon_list_fragment,detailFragment)
                fragmentTranscation.addToBackStack("detail")
                fragmentTranscation.commit()
                return_top_btn = findViewById<FloatingActionButton>(R.id.return_top_btn)
                return_top_btn.hide()

                //set pokemon name for toolbar
                val pokemon = Common.findPokemonByNum(num)
                toolbar.title = pokemon!!.name
            }
        }
    }

    private val showType = object:BroadcastReceiver(){
        override fun onReceive(p0: Context?, intent: Intent?) {
            if(intent!!.action!!.toString() == Common.KEY_POKEMON_TYPE)
            {
                //replace fragment
                val pokemonType = PokemonType.getInstance()
                val type = intent.getStringExtra("type")
                val bundle = Bundle()
                var return_top_btn: FloatingActionButton
                bundle.putString("type",type)
                pokemonType.arguments = bundle

                supportFragmentManager.popBackStack(0,FragmentManager.POP_BACK_STACK_INCLUSIVE)

                val fragmentTranscation = supportFragmentManager.beginTransaction()
                fragmentTranscation.replace(R.id.pokemon_list_fragment,pokemonType)
                fragmentTranscation.addToBackStack("type")
                fragmentTranscation.commit()
                return_top_btn = findViewById<FloatingActionButton>(R.id.return_top_btn)
                return_top_btn.hide()

                toolbar.title = type + " Type"
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.setTitle(R.string.pokedex_gen_1)
        setSupportActionBar(toolbar)

        //register broadcast
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(showDetail, IntentFilter(Common.KEY_ENABLE_HOME))

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(showEvolution, IntentFilter(Common.KEY_NUM_EVOLUTION))

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(showType, IntentFilter(Common.KEY_POKEMON_TYPE))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId)
        {
            android.R.id.home -> {
                toolbar.title = "Pokemon List"

                //clear all fragment in stack with "detail" name
                supportFragmentManager.popBackStack("detail",FragmentManager.POP_BACK_STACK_INCLUSIVE)
                supportFragmentManager.popBackStack("type",FragmentManager.POP_BACK_STACK_INCLUSIVE)

                //replace fragment
                val pokemonList = PokemonList.getInstance()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.remove(pokemonList)
                fragmentTransaction.replace(R.id.pokemon_list_fragment, pokemonList)
                fragmentTransaction.commit()

                supportActionBar!!.setDisplayShowHomeEnabled(false)
                supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            }
        }
        return true
    }
}
