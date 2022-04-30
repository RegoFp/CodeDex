package com.example.codedex.pokeapi;

import com.example.codedex.models.Pokemon;
import com.example.codedex.models.PokemonData;
import com.example.codedex.models.PokemonList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonClient {

    //Lista de todos los pokemons
    @GET("pokemon?limit=100000&offset=0")
    Call<PokemonList> pokemons(

    );

    //Pokemon por su nº en la pokedex
    @GET("pokemon/{id}")
    Call<PokemonData> getPokemonById(@Path("id") String id);

}
