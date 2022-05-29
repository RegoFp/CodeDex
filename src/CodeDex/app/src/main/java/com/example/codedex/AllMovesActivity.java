package com.example.codedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import android.os.Bundle;

import android.view.Window;

import com.example.codedex.adapters.AllMovesAdapter;
import com.example.codedex.models.AllMovesList;
import com.example.codedex.models.Move;

import com.example.codedex.pokeapi.PokemonClient;



import java.util.ArrayList;
import java.util.Objects;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AllMovesActivity extends AppCompatActivity implements AllMovesAdapter.onItemListener, SearchView.OnQueryTextListener{


    private RetrofitInstance retrofitInstance;
    private AllMovesAdapter listMovesAdapter;
    ArrayList<Move> moveLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_moves);

        //Start retrofit
        retrofitInstance = new RetrofitInstance();
        retrofitInstance.startRetrofit();


        //Hide topbar and change color of statusBar
        Objects.requireNonNull(getSupportActionBar()).hide();
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.pokeRed));


        //Change color of botton NavigationBar
        getWindow().setNavigationBarColor(getResources().getColor(R.color.pokeRed));

        //Set up Searchview
        SearchView searchView = findViewById(R.id.allMovesSearch);
        searchView.setOnQueryTextListener(this);


        //Set Up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.allMovesRecycler);

        listMovesAdapter = new AllMovesAdapter(this,this);
        recyclerView.setAdapter(listMovesAdapter);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Get allmoves and add them to Recyclerview
        getAllMoves();



    }

    private void getAllMoves() {
        Retrofit retrofit = retrofitInstance.retrofit;
        PokemonClient client = retrofit.create(PokemonClient.class);
        Call<AllMovesList> call = client.Allmoves();
        call.enqueue(new Callback<AllMovesList>() {
            @Override
            public void onResponse(Call<AllMovesList> call, Response<AllMovesList> response) {

                moveLists = response.body().getResults();
                listMovesAdapter.addMoveItem(moveLists);


            }

            @Override
            public void onFailure(Call<AllMovesList> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Move m = listMovesAdapter.getCurrentList().get(position);
        String moveName = m.getName();
        retrofitInstance.getMove(getApplicationContext(),moveName);

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