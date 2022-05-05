package com.example.codedex;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.codedex.models.PokemonData;
import com.example.codedex.models.SpecieData;
import com.example.codedex.pokeapi.PokemonClient;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PokeViewFragment extends Fragment {

    PokemonData pokemonData;
    Retrofit retrofit;

    public PokeViewFragment(PokemonData pokemonData) {
        this.pokemonData = pokemonData;

        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Get Extra Data

        //Start retrofit
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


        //Get data
        //Get specieData
        View root = inflater.inflate(R.layout.fragment_poke_view, null);

        PokemonClient client =  retrofit.create(PokemonClient.class);
        Call<SpecieData> call = client.getSpecieById(pokemonData.getId());
        call.enqueue(new Callback<SpecieData>() {
            @Override
            public void onResponse(Call<SpecieData> call, Response<SpecieData> response) {
                SpecieData specieData = response.body();

                //TODO make sure its in english with a foor loop
                TextView description = (TextView) root.findViewById(R.id.description);
                String desc = specieData.getFlavor_text().get(0).getFlavor_text();
                description.setText(specieData.getFlavor_text().get(0).getFlavor_text().replaceAll("\\\n", " "));
            }

            @Override
            public void onFailure(Call<SpecieData> call, Throwable t) {

            }
        });

        // Inflate the layout for this fragment

        //TODO disable when not enough abilities
        if(pokemonData.getAbilities().size()>0){
            TextView ability1 = root.findViewById(R.id.ability1);
            ability1.setText(pokemonData.getAbilities().get(0).getAbility().getName());

            if(pokemonData.getAbilities().size()>1) {
                TextView ability2 = root.findViewById(R.id.ability2);
                ability2.setText(pokemonData.getAbilities().get(1).getAbility().getName());
            }

        }else{

        }

        TextView height = root.findViewById(R.id.detailHeight);
        height.setText(pokemonData.getHeight());

        TextView weight = root.findViewById(R.id.detailWeight);
        weight.setText(pokemonData.getWeight());


        return root;
    }
}