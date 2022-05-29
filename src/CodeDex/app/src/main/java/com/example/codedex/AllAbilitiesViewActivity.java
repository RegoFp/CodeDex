package com.example.codedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Build;
import android.os.Bundle;


import com.example.codedex.adapters.AbilitiesAdapter;
import com.example.codedex.models.Ability;

import com.example.codedex.models.AllAbilities;
import com.example.codedex.pokeapi.PokemonClient;



import java.util.ArrayList;


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

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

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