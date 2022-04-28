package com.example.codedex.pokeapi;

import com.example.codedex.models.Pokemon;
import com.example.codedex.models.PokemonData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonClient {

    //Request
    @GET("pokemon/")
    Call<List<Pokemon>> pokemons(

    );

    @GET("pokemon/{id}")
    Call<PokemonData> getPokemonById(@Path("id") String id);

}
