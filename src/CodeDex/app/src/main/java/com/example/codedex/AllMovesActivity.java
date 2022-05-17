package com.example.codedex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.codedex.models.AllMovesList;
import com.example.codedex.models.Move;
import com.example.codedex.models.MoveData;
import com.example.codedex.models.MoveList;
import com.example.codedex.pokeapi.PokemonClient;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllMovesActivity extends AppCompatActivity {

    Retrofit retrofit;
    PokemonClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_moves);

        startRetrofit();

        Call<AllMovesList> call = client.Allmoves();
        call.enqueue(new Callback<AllMovesList>() {
            @Override
            public void onResponse(Call<AllMovesList> call, Response<AllMovesList> response) {
                ArrayList<Move> allmove = response.body().getResults();
                allmove.get(0);

                for(Move m : allmove){
                    String name = m.getName();

                    Call<MoveData> call2 = client.getMoveData(name);
                    call2.enqueue(new Callback<MoveData>() {
                        @Override
                        public void onResponse(Call<MoveData> call, Response<MoveData> response) {
                            System.out.println(response.body().getDamage_class());
                        }

                        @Override
                        public void onFailure(Call<MoveData> call, Throwable t) {

                        }
                    });


                }
            }

            @Override
            public void onFailure(Call<AllMovesList> call, Throwable t) {

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