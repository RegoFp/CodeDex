package com.example.codedex;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codedex.adapters.EvolutionChainRootAdapter;
import com.example.codedex.models.AbilityData;
import com.example.codedex.models.ChainLink;
import com.example.codedex.models.EvolutionRoot;
import com.example.codedex.models.FlavorText;
import com.example.codedex.models.PokemonData;
import com.example.codedex.models.SpecieData;
import com.example.codedex.models.Type;
import com.example.codedex.models.TypesList;
import com.example.codedex.pokeapi.PokemonClient;

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


public class PokeViewFragment extends Fragment   {

    PokemonData pokemonData;
    Retrofit retrofit;
    View root;
    ProgressBar progressBar;

    private RecyclerView recyclerView;
    private ArrayList<ChainLink> chainLinks = new ArrayList<>();
    private EvolutionChainRootAdapter evolutionChainRootAdapter;

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


        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_poke_view, null);

        //Start retrofit
        RetrofitInstance retrofitInstance = new RetrofitInstance();
        retrofitInstance.startRetrofit();
        retrofit = retrofitInstance.retrofit;


        //Get SpecieData
        PokemonClient client =  retrofit.create(PokemonClient.class);
        Call<SpecieData> call = client.getSpecieById(pokemonData.getId());
        call.enqueue(new Callback<SpecieData>() {
            @Override
            public void onResponse(Call<SpecieData> call, Response<SpecieData> response) {

                SpecieData specieData = response.body();
                TextView description = (TextView) root.findViewById(R.id.description);

                for (final FlavorText flavorText: specieData.getFlavor_text()){

                    if(flavorText.getLanguage().getName().equalsIgnoreCase("en")){

                        description.setText(flavorText.getFlavor_text().replaceAll("\\\n"," "));
                        break;
                    }


                }

                getEvolutionChain(specieData.getEvolution_chain().getUrl());

            }

            @Override
            public void onFailure(Call<SpecieData> call, Throwable t) {

            }
        });


        //  Show the abilities if the pokemon has some
        if(pokemonData.getAbilities().size()>0){
            TextView ability1 = root.findViewById(R.id.ability1);

            String ability1Name = pokemonData.getAbilities().get(0).getAbility().getName();

            ability1.setText(ability1Name.substring(0, 1).toUpperCase() + ability1Name.substring(1).replace("-"," "));

            ability1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));
                    retrofitInstance.getAbility(getContext(),pokemonData.getAbilities().get(0).getAbility().getName());
                }
            });

            TextView ability2 = root.findViewById(R.id.ability2);
            if(pokemonData.getAbilities().size()>1) {
                String abiliy2Name = pokemonData.getAbilities().get(1).getAbility().getName();
                ability2.setText(abiliy2Name.substring(0, 1).toUpperCase() + abiliy2Name.substring(1).replace("-"," "));
                ability2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));
                        retrofitInstance.getAbility(getContext(),pokemonData.getAbilities().get(1).getAbility().getName());
                    }
                });
            }else{

                ability2.setVisibility(View.GONE);

            }

        }

        //Set height
        TextView height = root.findViewById(R.id.detailHeight);
        Double heightm = Double.valueOf(pokemonData.getHeight())/10;
        height.setText(heightm + " m");

        //Set weight
        TextView weight = root.findViewById(R.id.detailWeight);
        Double weightKg = Double.valueOf(pokemonData.getWeight())/10;
        weight.setText(weightKg+ " Kg");


        progressBar = (ProgressBar) root.findViewById(R.id.evolutionLoading);

        return root;
    }

    private void getEvolutionChain(String url) {
        String[] urlSplice = url.split("/");
        int id = Integer.parseInt(urlSplice[urlSplice.length -1]);


        //Get evolutionChain
        PokemonClient client = retrofit.create(PokemonClient.class);
        Call<EvolutionRoot> call = client.getEvolutionChainByID(id);
        call.enqueue(new Callback<EvolutionRoot>() {
            @Override
            public void onResponse(Call<EvolutionRoot> call, Response<EvolutionRoot> response) {
                EvolutionRoot evolutionRoot = response.body();

                progressBar.setVisibility(View.GONE);

                chainLinks = evolutionRoot.getChain().getEvolves_to();

                //RecyclerView
                recyclerView = (RecyclerView) root.findViewById(R.id.evolutionRecycler);

                Boolean test = false;
                evolutionChainRootAdapter= new EvolutionChainRootAdapter(root.getContext(),EvolutionChainRootAdapter.onItemListener = new EvolutionChainRootAdapter.onItemListener() {
                    @Override
                    public void onItemClick(int position) {
                        //getPokemon(evolutionChainRootAdapter.getCurrentList().get(position).getSpecies().getName());
                    }
                });
                recyclerView.setAdapter(evolutionChainRootAdapter);
                recyclerView.setHasFixedSize(true);

                LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
                recyclerView.setLayoutManager(layoutManager);

                evolutionChainRootAdapter.addOneItem(evolutionRoot.getChain());


                System.out.println(test);
            }


            @Override
            public void onFailure(Call<EvolutionRoot> call, Throwable t) {

            }
        });


    }



}