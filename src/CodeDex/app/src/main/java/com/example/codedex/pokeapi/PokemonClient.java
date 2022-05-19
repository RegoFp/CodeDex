package com.example.codedex.pokeapi;

import com.example.codedex.models.AllMovesList;
import com.example.codedex.models.MoveData;
import com.example.codedex.models.MoveList;
import com.example.codedex.models.Pokemon;
import com.example.codedex.models.PokemonData;
import com.example.codedex.models.PokemonList;
import com.example.codedex.models.SpecieData;

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

    //Peticion
    @GET("pokemon-species/{id}")
    //El objeto que se recibe y a la derecha variable que enviamoes
    Call<SpecieData> getSpecieById(@Path("id") Integer id);

    @GET("move?limit=100000&offset=0")
    Call<AllMovesList> Allmoves(

    );

    @GET("move/{name}")
    Call<MoveData> getMoveData(@Path("name") String name);

}
