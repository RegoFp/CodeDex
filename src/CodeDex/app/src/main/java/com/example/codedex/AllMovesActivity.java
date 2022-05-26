package com.example.codedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;

import com.example.codedex.models.AllMovesList;
import com.example.codedex.models.Move;
import com.example.codedex.models.MoveData;
import com.example.codedex.models.MoveList;
import com.example.codedex.pokeapi.PokemonClient;

import org.parceler.Parcels;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllMovesActivity extends AppCompatActivity implements AllMovesAdapter.onItemListener, SearchView.OnQueryTextListener{

    Retrofit retrofit;
    PokemonClient client;

    private RecyclerView recyclerView;
    private AllMovesAdapter listMovesAdapter;
    ArrayList<Move> moveLists;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_moves);

        startRetrofit();

        getSupportActionBar().hide();
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.pokeRed));


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.pokeRed));
        }

        searchView = (SearchView) findViewById(R.id.allMovesSearch);
        searchView.setOnQueryTextListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.allMovesRecycler);



        listMovesAdapter = new AllMovesAdapter(this,this);
        recyclerView.setAdapter(listMovesAdapter);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        Call<AllMovesList> call = client.Allmoves();
        call.enqueue(new Callback<AllMovesList>() {
            @Override
            public void onResponse(Call<AllMovesList> call, Response<AllMovesList> response) {

                moveLists = response.body().getResults();
                listMovesAdapter.addMoveItem(moveLists);
                //RecyclerView


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

    @Override
    public void onItemClick(int position) {
        Move m = listMovesAdapter.getCurrentList().get(position);
        String moveName = m.getName();
        getMove(moveName);

    }

    public void getMove(String name) {

        Intent i = new Intent(this, MoveViewActivity.class);


        PokemonClient client = retrofit.create(PokemonClient.class);
        Call<MoveData> call = client.getMoveData(name);
        call.enqueue(new Callback<MoveData>() {
            @Override
            public void onResponse(Call<MoveData> call, Response<MoveData> response) {
                MoveData moveData = response.body();
                Parcelable wrapped = Parcels.wrap(moveData);
                i.putExtra("move", wrapped);

                startActivity(i);
                //getActivity().overridePendingTransition(R.anim.slide_up,R.anim.nothing);
            }

            @Override
            public void onFailure(Call<MoveData> call, Throwable t) {

            }
        });



    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listMovesAdapter.filtderData(newText);
        return false;
    }
}