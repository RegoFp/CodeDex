package com.example.codedex;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.widget.Toast;

import com.example.codedex.models.AbilityData;
import com.example.codedex.models.MoveData;
import com.example.codedex.models.NatureResults;
import com.example.codedex.models.PokemonData;
import com.example.codedex.models.Type;
import com.example.codedex.models.TypeData;
import com.example.codedex.models.TypeResults;
import com.example.codedex.models.TypesList;
import com.example.codedex.pokeapi.PokemonClient;

import org.parceler.Parcels;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {


    public Retrofit retrofit;

    /**
     * Starts an instance of Retrofit to be used in the methods in this same class
     */
    public void startRetrofit() {
        String API_BASE_URL = "https://pokeapi.co/api/v2/";

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        retrofit2.Retrofit.Builder builder =
                new retrofit2.Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();


    }

    /**
     * Requests a TypeData object and starts TypeViewActivity
     * @param context   the context from the activity this method is called from
     * @param id the id assigned to the type in the API
     */
    public void getType(Context context, String id){
        Intent i = new Intent(context, TypeViewActivity.class);
        PokemonClient client =  retrofit.create(PokemonClient.class);

        Call<TypeData> call = client.getType(id);
        call.enqueue(new Callback<TypeData>() {
            @Override
            public void onResponse(Call<TypeData> call, Response<TypeData> response) {
                TypeData typeData = response.body();
                Parcelable wrapped = Parcels.wrap(typeData);
                i.putExtra("type", wrapped);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }

            @Override
            public void onFailure(Call<TypeData> call, Throwable t) {

            }
        });



    }

    /**
     * Requests a TypeResults object and starts AllTypesViewActivity
     * @param context  the context from the activity this method is called from
     */
    public void getAllType(Context context){
        Intent i = new Intent(context, AllTypesViewActivity.class);
        PokemonClient client =  retrofit.create(PokemonClient.class);
        Call<TypeResults> call = client.getAllTypes();
        call.enqueue(new Callback<TypeResults>() {
            @Override
            public void onResponse(Call<TypeResults> call, Response<TypeResults> response) {
                TypeResults typeResults = response.body();
                Parcelable wrapped = Parcels.wrap(typeResults);
                i.putExtra("types", wrapped);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }

            @Override
            public void onFailure(Call<TypeResults> call, Throwable t) {
            }
        });
    }

    /**
     * Requests a TypeResults object and starts AllNaturesActivity
     * @param context  the context from the activity this method is called from
     */
    public void getAllNatures(Context context){
        Intent i = new Intent(context, AllNaturesActivity.class);
        PokemonClient client =  retrofit.create(PokemonClient.class);
        Call<NatureResults> call = client.getAllNatures();
        call.enqueue(new Callback<NatureResults>() {
            @Override
            public void onResponse(Call<NatureResults> call, Response<NatureResults> response) {
                NatureResults natureResults = response.body();
                Parcelable wrapped = Parcels.wrap(natureResults);
                i.putExtra("natures", wrapped);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }

            @Override
            public void onFailure(Call<NatureResults> call, Throwable t) {
            }
        });
    }

    /**
     * Requests a PokemonData object and starts PokemonView
     * @param context the context from the activity this method is called from
     * @param id the id assigned to the Pokemon in the API
     */
    public void getPokemon(Context context, String id){
        Intent i = new Intent(context, PokemonView.class);
        PokemonClient client =  retrofit.create(PokemonClient.class);
        Call<PokemonData> call = client.getPokemonById(id);
        call.enqueue(new Callback<PokemonData>() {
            @Override
            public void onResponse(Call<PokemonData> call, Response<PokemonData> response) {
                PokemonData pokemonData = response.body();
                Parcelable wrapped = Parcels.wrap(pokemonData);
                PokemonData pokemonDataWrapped = Parcels.unwrap(wrapped);


                List<TypesList> typesList = pokemonData.getTypes();
                Type type = typesList.get(0).getType();
                i.putExtra("pokemon", wrapped);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);




            }

            @Override
            public void onFailure(Call<PokemonData> call, Throwable t) {
                Toast.makeText(context,"ERROR", Toast.LENGTH_LONG).show();
            }
        });

    }

    /**
     * Requests a AbilityData object and starts AbilityViewActivity
     * @param context the context from the activity this method is called from
     * @param name the name assigned to the ability in the API
     */
    public void getAbility(Context context, String name) {

        Intent i = new Intent(context, AbilityViewActivity.class);


        PokemonClient client = retrofit.create(PokemonClient.class);
        Call<AbilityData> call = client.getAbilityById(name);
        call.enqueue(new Callback<AbilityData>() {
            @Override
            public void onResponse(Call<AbilityData> call, Response<AbilityData> response) {
                AbilityData abilityData = response.body();
                Parcelable wrapped = Parcels.wrap(abilityData);
                i.putExtra("ability", wrapped);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                String test = abilityData.getFlavor_text_entries().get(0).getFlavor_text();
                context.startActivity(i);

            }

            @Override
            public void onFailure(Call<AbilityData> call, Throwable t) {

            }
        });
    }

    /**
     * Requests a MoveData object and starts MoveViewActivity
     * @param context the context from the activity this method is called from
     * @param name the name assigned to the move in the API
     */
    public void getMove(Context context, String name) {

        Intent i = new Intent(context, MoveViewActivity.class);


        PokemonClient client = retrofit.create(PokemonClient.class);
        Call<MoveData> call = client.getMoveData(name);
        call.enqueue(new Callback<MoveData>() {
            @Override
            public void onResponse(Call<MoveData> call, Response<MoveData> response) {
                MoveData moveData = response.body();
                Parcelable wrapped = Parcels.wrap(moveData);
                i.putExtra("move", wrapped);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                //getActivity().overridePendingTransition(R.anim.slide_up,R.anim.nothing);
            }

            @Override
            public void onFailure(Call<MoveData> call, Throwable t) {

            }
        });



    }


}
