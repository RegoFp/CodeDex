package com.example.codedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import com.example.codedex.models.AbilitiesList;
import com.example.codedex.models.Ability;
import com.example.codedex.models.AbilityData;
import com.example.codedex.models.AllAbilities;
import com.example.codedex.models.AllMovesList;
import com.example.codedex.models.Move;
import com.example.codedex.models.MoveData;
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

public class AllAbilitiesViewActivity extends AppCompatActivity implements AbilitiesAdapter.onItemListener, SearchView.OnQueryTextListener{

    Retrofit retrofit;
    PokemonClient client;

    private RecyclerView recyclerView;
    private AbilitiesAdapter abilitiesAdapter;
    ArrayList<Ability> abilitiesLists;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_abilities_view);

        searchView = (SearchView) findViewById(R.id.AllAbilitiesSearch);
        searchView.setOnQueryTextListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.AllAbilitiesRecycler);

        startRetrofit();

        abilitiesAdapter = new AbilitiesAdapter(this,this);
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

    @Override
    public void onItemClick(int position) {
        Ability a = abilitiesAdapter.getCurrentList().get(position);
        getAbility(a.getName());

    }

    private void getAbility(String name) {

        Intent i = new Intent(this, AbilityViewActivity.class);


        PokemonClient client = retrofit.create(PokemonClient.class);
        Call<AbilityData> call = client.getAbilityById(name);
        call.enqueue(new Callback<AbilityData>() {
            @Override
            public void onResponse(Call<AbilityData> call, Response<AbilityData> response) {
                AbilityData abilityData = response.body();
                Parcelable wrapped = Parcels.wrap(abilityData);
                i.putExtra("ability", wrapped);
                String test = abilityData.getFlavor_text_entries().get(0).getFlavor_text();
                startActivity(i);
                //getActivity().overridePendingTransition(R.anim.slide_up,R.anim.nothing);
            }

            @Override
            public void onFailure(Call<AbilityData> call, Throwable t) {

            }
        });



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