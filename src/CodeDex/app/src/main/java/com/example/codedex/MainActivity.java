package com.example.codedex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;

import com.example.codedex.models.PokemonList;
import com.example.codedex.models.Type;
import com.example.codedex.models.TypesList;
import com.example.codedex.pokeapi.PokemonClient;
import com.example.codedex.models.Pokemon;
import com.example.codedex.models.PokemonData;
import com.google.android.material.navigation.NavigationView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements ListPokemonAdapter.onItemListener, NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    private Retrofit retrofit;
    private String id = "1";

    private RecyclerView recyclerView;
    private ListPokemonAdapter listPokemonAdapter;
    private SearchView searchView;

    private ArrayList<Pokemon> pokemonList = new ArrayList<>();
    private RetrofitInstance retrofitInstance;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    //TODO https://www.youtube.com/watch?v=HwYENW0RyY4

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        retrofitInstance = new RetrofitInstance();
        retrofitInstance.startRetrofit();

        final Animation animation = AnimationUtils.loadAnimation(this,R.anim.bounce);

        NavigationView navigationView = (NavigationView) findViewById(R.id.mainMenu);
        navigationView.setNavigationItemSelectedListener(this);

        Window window = this.getWindow();
        //window.setStatusBarColor(getResources().getColor(R.color.pokeRed));

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.pokeRed));
        }

        searchView = (SearchView) findViewById(R.id.searchBar);
        searchView.setOnQueryTextListener(this);

        //RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.pokeList);


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


        //Get all Pokemon
        alldata();


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {


        }
        return super.onOptionsItemSelected(item);
    }

    private void alldata(){

        ;
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

    //Recibe la posicion en la pokedex, realiza la peticio y envia los resultados a una nueva actividad
    private void Pokemon(String id){
        Intent i = new Intent(this, PokemonView.class);
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

                startActivity(i);




            }

            @Override
            public void onFailure(Call<PokemonData> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"ERROR", Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public void onItemClick(int position) {
        Pokemon pokemon = listPokemonAdapter.getCurrentList().get(position);
        Pokemon(""+pokemon.getId());

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

