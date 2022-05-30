package com.example.codedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Build;
import android.os.Bundle;
import android.view.Window;


import com.example.codedex.adapters.AbilitiesAdapter;
import com.example.codedex.models.Ability;

import com.example.codedex.models.AllAbilities;
import com.example.codedex.pokeapi.PokemonClient;



import java.util.ArrayList;
import java.util.Objects;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AllAbilitiesViewActivity extends AppCompatActivity implements AbilitiesAdapter.onItemListener, SearchView.OnQueryTextListener{


    private AbilitiesAdapter abilitiesAdapter;
    ArrayList<Ability> abilitiesLists;
    private RetrofitInstance retrofitInstance;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_abilities_view);


        //Hide topbar and change color of statusBar
        Objects.requireNonNull(getSupportActionBar()).hide();
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.pokeRed));

        //Change color of botton NavigationBar
        getWindow().setNavigationBarColor(getResources().getColor(R.color.pokeRed));

        //Sets up SearchView
        SearchView searchView = findViewById(R.id.AllAbilitiesSearch);
        searchView.setOnQueryTextListener(this);

        //Start retrofit
        retrofitInstance = new RetrofitInstance();
        retrofitInstance.startRetrofit();
        retrofit = retrofitInstance.retrofit;

        //Change color of navigatioBar
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.pokeRed));
        }
        //Sets up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.AllAbilitiesRecycler);
        abilitiesAdapter = new AbilitiesAdapter(this,this);
        recyclerView.setAdapter(abilitiesAdapter);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Shows all Abilities
        getAllAbilities();


    }

    private void getAllAbilities() {
        PokemonClient client = retrofit.create(PokemonClient.class);
        Call<AllAbilities> call = client.AllAbilities();
        call.enqueue(new Callback<AllAbilities>() {
            @Override
            public void onResponse(Call<AllAbilities> call, Response<AllAbilities> response) {

                abilitiesLists = response.body().getResults();
                abilitiesAdapter.addMoveItem(abilitiesLists);


            }

            @Override
            public void onFailure(Call<AllAbilities> call, Throwable t) {

            }
        });
    }


    @Override
    public void onItemClick(int position) {
        Ability a = abilitiesAdapter.getCurrentList().get(position);
        retrofitInstance.getAbility(this, a.getName());

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        abilitiesAdapter.filtderData(newText);
        return false;
    }
}