package com.example.codedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.codedex.models.AbilitiesList;
import com.example.codedex.models.Ability;
import com.example.codedex.models.AllAbilities;
import com.example.codedex.models.AllMovesList;
import com.example.codedex.models.Move;
import com.example.codedex.pokeapi.PokemonClient;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllAbilitiesViewActivity extends AppCompatActivity {

    Retrofit retrofit;
    PokemonClient client;

    private RecyclerView recyclerView;
    private AbilitiesAdapter abilitiesAdapter;
    ArrayList<Ability> abilitiesLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_abilities_view);

        recyclerView = (RecyclerView) findViewById(R.id.AllAbilitiesRecycler);

        startRetrofit();

        abilitiesAdapter = new AbilitiesAdapter(this,null);
        recyclerView.setAdapter(abilitiesAdapter);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);


        Call<AllAbilities> call = client.AllAbilities();
        call.enqueue(new Callback<AllAbilities>() {
            @Override
            public void onResponse(Call<AllAbilities> call, Response<AllAbilities> response) {

                abilitiesLists = response.body().getResults();
                abilitiesAdapter.addMoveItem(abilitiesLists);
                //RecyclerView






            }

            @Override
            public void onFailure(Call<AllAbilities> call, Throwable t) {

            }
        });

    }

    private void startRetrofit(){
        //Iniciar Retrofit
        String API_BASE_URL = "https://pokeapi.co/api/v2/";

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit.Builder builder =
                new Retrofit.Builder()
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


        client = retrofit.create(PokemonClient.class);


    }
}