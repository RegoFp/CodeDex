package com.example.codedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.codedex.models.AbilityData;
import com.example.codedex.models.Effect_entries;
import com.example.codedex.models.FlavorText;

import org.parceler.Parcels;

public class AbilityViewActivity extends AppCompatActivity implements AbilityListPokemonAdapter.onItemListener {

    AbilityData abilityData;
    RecyclerView recyclerView;
    AbilityListPokemonAdapter abilityListPokemonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ability_view);


        getSupportActionBar().hide();


        Intent intent = getIntent();
        abilityData= Parcels.unwrap(getIntent().getParcelableExtra("ability"));

        TextView textView1 = findViewById(R.id.abilityDataName);
        textView1.setText(abilityData.getName().toUpperCase().replace("-"," "));

        TextView textView2 = findViewById(R.id.abilityDataDescription);
        for (final FlavorText flavorText: abilityData.getFlavor_text_entries()){

            if(flavorText.getLanguage().getName().equalsIgnoreCase("en")){

                textView2.setText(flavorText.getFlavor_text().replaceAll("\\\n"," "));
                break;
            }


        }



        TextView textView3 = findViewById(R.id.abilityDataEffect);
        textView3.setText(""+abilityData.getEffect_entries().get(0).getEffect());
        for (final Effect_entries effect_entries: abilityData.getEffect_entries()){

            if(effect_entries.getLanguaje().getName().equalsIgnoreCase("en")){
                textView3.setText(effect_entries.getEffect().replaceAll("\\\n"," "));
                break;
            }
        }

        ScrollView scrollView = (ScrollView) findViewById(R.id.AbilityDataScroll);
        //Checks how many lines the effects has, and if it's less than 5 sets it's height as wrap content
        textView3.post(new Runnable() {
            @Override
            public void run() {
                int lineCount = textView3.getLineCount();
                if(lineCount<5){
                    ViewGroup.LayoutParams params = scrollView.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    scrollView.setLayoutParams(params);

                }

            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.abilityDataPokemon);


        abilityListPokemonAdapter = new AbilityListPokemonAdapter(this,this);
        recyclerView.setAdapter(abilityListPokemonAdapter);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        abilityListPokemonAdapter.addPokemonItem(abilityData.getPokemon());




    }

    @Override
    public void onItemClick(int position) {

    }
}