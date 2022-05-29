package com.example.codedex.pokeapi;


import com.example.codedex.models.AbilityData;
import com.example.codedex.models.AllAbilities;
import com.example.codedex.models.AllMovesList;
import com.example.codedex.models.EvolutionRoot;
import com.example.codedex.models.MoveData;
import com.example.codedex.models.NatureData;
import com.example.codedex.models.NatureResults;
import com.example.codedex.models.PokemonData;
import com.example.codedex.models.PokemonList;
import com.example.codedex.models.SpecieData;
import com.example.codedex.models.TypeData;
import com.example.codedex.models.TypeResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonClient {

    //Lista de todos los pokemons
    @GET("pokemon?limit=10000&offset=0")
    Call<PokemonList> pokemons();

    //Pokemon por su nº en la pokedex
    @GET("pokemon/{id}")
    Call<PokemonData> getPokemonById(@Path("id") String id);

    //Peticion
    @GET("pokemon-species/{id}")
    //El objeto que se recibe y a la derecha variable que enviamoes
    Call<SpecieData> getSpecieById(@Path("id") Integer id);

    @GET("move?limit=100000&offset=0")
    Call<AllMovesList> Allmoves();

    @GET("ability?limit=100000&offset=0")
    Call<AllAbilities> AllAbilities();


    //Pokemon por su nº en la pokedex
    @GET("ability/{id}")
    Call<AbilityData> getAbilityById(@Path("id") String id);

    //Pokemon por su nº en la pokedex
    @GET("evolution-chain/{id}")
    Call<EvolutionRoot> getEvolutionChainByID(@Path("id") Integer id);


    @GET("move/{name}")
    Call<MoveData> getMoveData(@Path("name") String name);


    @GET("type/{id}")
    Call<TypeData> getType(@Path("id") String id);

    //Receives all types
    @GET("type/")
    Call<TypeResults> getAllTypes();

    //Receives all Natures
    @GET("nature/")
    Call<NatureResults> getAllNatures();

    @GET("nature/{id}")
    Call<NatureData> getNature(@Path("id") String id);
}
