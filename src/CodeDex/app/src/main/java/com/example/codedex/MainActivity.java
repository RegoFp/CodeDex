package com.example.codedex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import androidx.appcompat.widget.SearchView;

import com.example.codedex.adapters.ListPokemonAdapter;
import com.example.codedex.models.PokemonList;
import com.example.codedex.pokeapi.PokemonClient;
import com.example.codedex.models.Pokemon;
import com.google.android.material.navigation.NavigationView;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity implements ListPokemonAdapter.onItemListener, NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {



    private RecyclerView recyclerView;
    private ListPokemonAdapter listPokemonAdapter;

    private ArrayList<Pokemon> pokemonList = new ArrayList<>();
    private RetrofitInstance retrofitInstance;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Start Retrofit
        retrofitInstance = new RetrofitInstance();
        retrofitInstance.startRetrofit();


        //Set Up NavigationView
        NavigationView navigationView = findViewById(R.id.mainMenu);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));



        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.pokeRed));
        }

        //Set Up SearchView
        SearchView searchView = findViewById(R.id.searchBar);
        searchView.setOnQueryTextListener(this);

        //RecyclerView
        recyclerView = findViewById(R.id.pokeList);
        listPokemonAdapter = new ListPokemonAdapter(this,this);
        recyclerView.setAdapter(listPokemonAdapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //Start Retrofit
        RetrofitInstance retrofitInstance = new RetrofitInstance();
        retrofitInstance.startRetrofit();


        //Show all pokemon
        alldata();



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {


        }
        return super.onOptionsItemSelected(item);
    }

    private void alldata(){
        Retrofit retrofit = retrofitInstance.retrofit;
        PokemonClient client =  retrofit.create(PokemonClient.class);
        Call<PokemonList> call = client.pokemons();
        call.enqueue(new Callback<PokemonList>() {
            @Override
            public void onResponse(Call<PokemonList> call, Response<PokemonList> response) {
            System.out.println(response.body().getResults().get(1).getName());

            pokemonList = response.body().getResults();

                //Removes alternative forms from the list
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    pokemonList.removeIf(t -> t.getId()>10000);
                }else{

                    for(int i = 0; i < pokemonList.size(); i++){
                        if(pokemonList.get(i).getId() > 1000){
                            pokemonList.remove(i);
                            i--;
                        }
                    }


                }

            listPokemonAdapter.addPokemonItem(pokemonList);


            }

            @Override
            public void onFailure(Call<PokemonList> call, Throwable t) {

            }
        });

        int items = recyclerView.getAdapter().getItemCount();

    }


    @Override
    public void onItemClick(int position) {
        Pokemon pokemon = listPokemonAdapter.getCurrentList().get(position);
        retrofitInstance.getPokemon(getApplicationContext(),""+pokemon.getId());

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_abilities:
                Intent e = new Intent(this, AllAbilitiesViewActivity.class);
                startActivity(e);
                break;

            case R.id.menu_moves: {
                Intent i = new Intent(this, AllMovesActivity.class);
                startActivity(i);
                break;
            }

            case R.id.menu_types: {
                retrofitInstance.getAllType(this);
                break;
            }

            case R.id.menu_Natures: {
                retrofitInstance.getAllNatures(this);
                break;
            }
        }
        //close navigation drawer
        return true;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listPokemonAdapter.filtderData(newText);
        return false;
    }
}

