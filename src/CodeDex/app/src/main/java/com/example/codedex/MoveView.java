package com.example.codedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.codedex.models.Effect_entries;
import com.example.codedex.models.FlavorText;
import com.example.codedex.models.MoveData;

import org.parceler.Parcels;

public class MoveView extends AppCompatActivity implements ListPokemonAdapter.onItemListener{

    private MoveData moveData;

    private RecyclerView recyclerView;
    private ListPokemonAdapter listPokemonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_view);

        Intent intent = getIntent();
        moveData = Parcels.unwrap(getIntent().getParcelableExtra("move"));

        TextView textView1 = findViewById(R.id.moveDataName);
        textView1.setText(moveData.getName());

        TextView textView2 = findViewById(R.id.moveDataAccuracy);
        textView2.setText(""+moveData.getAccuracy());

        TextView textView3 = findViewById(R.id.moveDataPP);
        textView3.setText(""+moveData.getPp());

        TextView textView4 = findViewById(R.id.moveDataPower);
        textView4.setText(""+moveData.getPower());

        TextView textView6 = findViewById(R.id.moveDataDamageClass);
        textView6.setText(""+moveData.getDamage_class().getName());

        TextView textView7 = findViewById(R.id.moveDataDamageType);
        textView7.setText(""+moveData.getType().getName());

        TextView textView5 = findViewById(R.id.moveDataDescription);
        for (final FlavorText flavorText: moveData.getFlavor_text_entries()){
            if(flavorText.getLanguage().getName().equalsIgnoreCase("en")){
                textView5.setText(flavorText.getFlavor_text().replaceAll("\\\n"," "));
                break;
            }

        }


        TextView textView8 = findViewById(R.id.moveDataEffect);
        String effect = moveData.getEffect_entries().get(0).getEffect();

        textView8.setText(effect.replace("$effect_chance", ""+moveData.getEffect_chance()));


        recyclerView = (RecyclerView) findViewById(R.id.moveDataPokemon);


        listPokemonAdapter = new ListPokemonAdapter(this,this);
        recyclerView.setAdapter(listPokemonAdapter);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listPokemonAdapter.addPokemonItem(moveData.getLearned_by_pokemon());





    }


    @Override
    public void onItemClick(int position) {

    }
}
