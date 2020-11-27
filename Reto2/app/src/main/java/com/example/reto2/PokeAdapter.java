package com.example.reto2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reto2.modelo.Pokemon;

import java.io.Serializable;
import java.util.ArrayList;

public class PokeAdapter extends RecyclerView.Adapter<PokemonView> {

    private ArrayList<Pokemon>pokemons;

    public PokeAdapter (){
        pokemons = new ArrayList<Pokemon>();
    }

    public void addPokemon(Pokemon poke){
        pokemons.add(poke);
        this.notifyDataSetChanged();
    }

    public void clear(){
        pokemons.clear();
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PokemonView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.pokemon_row,null);
        ConstraintLayout rowroot = (ConstraintLayout) row;
        PokemonView pokemonView = new PokemonView(rowroot);
        return pokemonView;
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonView holder, int position) {
        holder.getNombre().setText(pokemons.get(position).getName());
        Glide.with(holder.getFoto()).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+pokemons.get(position).getForms().get(0).getNumber()+".png").into(holder.getFoto());
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }
}
