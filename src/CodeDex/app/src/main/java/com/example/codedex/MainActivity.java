package com.example.codedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.codedex.models.PokemonList;
import com.example.codedex.models.Type;
import com.example.codedex.models.TypesList;
import com.example.codedex.pokeapi.PokemonClient;
import com.example.codedex.models.Pokemon;
import com.example.codedex.models.PokemonData;

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

public class MainActivity extends AppCompatActivity implements ListPokemonAdapter.onItemListener {

    private Retrofit retrofit;
    private String id = "1";

    private RecyclerView recyclerView;
    private ListPokemonAdapter listPokemonAdapter;
    private SearchView searchView;

    private ArrayList<Pokemon> pokemonList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Animation animation = AnimationUtils.loadAnimation(this,R.anim.bounce);


        //RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.pokeList);


        listPokemonAdapter = new ListPokemonAdapter(this,this);
        recyclerView.setAdapter(listPokemonAdapter);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



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

    private void alldata(){

        ;
        PokemonClient client =  retrofit.create(PokemonClient.class);
        Call<PokemonList> call = client.pokemons();
        call.enqueue(new Callback<PokemonList>() {
            @Override
            public void onResponse(Call<PokemonList> call, Response<PokemonList> response) {
            System.out.println(response.body().getResults().get(1).getName());

            pokemonList = response.body().getResults();

            listPokemonAdapter.addPokemonItem(pokemonList);

            for (int i=0;i< pokemonList.size();i++){
                System.out.println(pokemonList.get(i).getName());
            }

            }

            @Override
            public void onFailure(Call<PokemonList> call, Throwable t) {

            }
        });

        int items = recyclerView.getAdapter().getItemCount();
        Toast.makeText(this,"hila"+items,Toast.LENGTH_SHORT).show();

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

                //Toast.makeText(getApplicationContext(),bulbasur.getName(), Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),type.getName(), Toast.LENGTH_LONG).show();


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
        Pokemon pokemon = pokemonList.get(position);
        Pokemon(""+pokemon.getId());

    }
}

