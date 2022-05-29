package com.example.codedex;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.example.codedex.models.NatureResults;
import com.example.codedex.models.TypeData;
import com.example.codedex.models.TypeResults;
import com.example.codedex.pokeapi.PokemonClient;

import org.parceler.Parcels;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {


    //TODO return objects, not start avticity here
    public Retrofit retrofit;

    public void startRetrofit() {
        //Iniciar Retrofit
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


}
