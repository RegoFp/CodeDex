package com.example.codedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.codedex.models.AbilityData;

import org.parceler.Parcels;

public class AbilityViewActivity extends AppCompatActivity {

    AbilityData abilityData;
    RecyclerView recyclerView;
    ListPokemonAdapter listPokemonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ability_view);


        getSupportActionBar().hide();


        Intent intent = getIntent();
        abilityData= Parcels.unwrap(getIntent().getParcelableExtra("ability"));

        TextView textView1 = findViewById(R.id.abilityDataName);
        textView1.setText(abilityData.getName());

        TextView textView2 = findViewById(R.id.abilityDataDescription);
        textView2.setText(""+abilityData.getFlavor_text_entries().get(0).getFlavor_text());


        TextView textView3 = findViewById(R.id.abilityDataEffect);
        textView3.setText(""+abilityData.getEffect_entries().get(0).getEffect());
/*

        recyclerView = (RecyclerView) findViewById(R.id.abilityDataPokemon);


        listPokemonAdapter = new ListPokemonAdapter(this,null);
        recyclerView.setAdapter(listPokemonAdapter);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listPokemonAdapter.addPokemonItem(abilityData.getPokemon());

*/


    }
}