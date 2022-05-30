package com.example.codedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ScrollView;
import android.widget.TextView;


import com.example.codedex.adapters.AbilityListPokemonAdapter;
import com.example.codedex.models.AbilityData;
import com.example.codedex.models.AbilityPokemonList;
import com.example.codedex.models.Effect_entries;
import com.example.codedex.models.FlavorText;


import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Objects;


public class AbilityViewActivity extends AppCompatActivity implements AbilityListPokemonAdapter.onItemListener {

    AbilityData abilityData;
    RecyclerView recyclerView;
    AbilityListPokemonAdapter abilityListPokemonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ability_view);

        //Hide actionBar
        Objects.requireNonNull(getSupportActionBar()).hide();

        Window window = this.getWindow();
        window.setStatusBarColor(Color.parseColor("#AAFF6363"));

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setNavigationBarColor(Color.parseColor("#AAFF6363"));
        }


        //Receive abilityData
        abilityData= Parcels.unwrap(getIntent().getParcelableExtra("ability"));

        //Show ability name
        TextView textView1 = findViewById(R.id.abilityDataName);
        textView1.setText(abilityData.getName().toUpperCase().replace("-"," "));

        //Search and show the english Description
        TextView textView2 = findViewById(R.id.abilityDataDescription);
        for (final FlavorText flavorText: abilityData.getFlavor_text_entries()){

            if(flavorText.getLanguage().getName().equalsIgnoreCase("en")){

                textView2.setText(flavorText.getFlavor_text().replaceAll("\n"," "));
                break;
            }


        }

        //Search and show the english Effect
        TextView textView3 = findViewById(R.id.abilityDataEffect);
        textView3.setText(abilityData.getEffect_entries().get(0).getEffect());
        for (final Effect_entries effect_entries: abilityData.getEffect_entries()){

            if(effect_entries.getLanguaje().getName().equalsIgnoreCase("en")){
                textView3.setText(effect_entries.getEffect().replaceAll("\n"," "));
                break;
            }
        }


        //Checks how many lines the effects has, and if it's less than 5 sets it's height as wrap content
        ScrollView scrollView = findViewById(R.id.AbilityDataScroll);
        textView3.post(() -> {
            int lineCount = textView3.getLineCount();
            if(lineCount<5){
                ViewGroup.LayoutParams params = scrollView.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                scrollView.setLayoutParams(params);

            }

        });


        //Set Up RecyclerView
        recyclerView = findViewById(R.id.abilityDataPokemon);
        abilityListPokemonAdapter = new AbilityListPokemonAdapter(this,this);
        recyclerView.setAdapter(abilityListPokemonAdapter);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<AbilityPokemonList> pokemonList = abilityData.getPokemon();

        //Removes alternative forms from the list
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            pokemonList.removeIf(t -> t.getPokemon().getId()>10000);
        }else{

            for(int i = 0; i < pokemonList.size(); i++){
                if(pokemonList.get(i).getPokemon().getId() > 1000){
                    pokemonList.remove(i);
                    i--;
                }
            }


        }

        abilityListPokemonAdapter.addPokemonItem(pokemonList);


    }

    @Override
    public void onItemClick(int position) {
        RetrofitInstance retrofitInstance = new RetrofitInstance();
        retrofitInstance.startRetrofit();

        retrofitInstance.getPokemon(getApplicationContext(), String.valueOf(abilityListPokemonAdapter.getList().get(position).getPokemon().getId()));


    }
}