package com.example.codedex;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.codedex.models.PokemonData;

import java.io.InputStream;
import java.net.URL;

public class PokemonView extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_view);

        Intent intent = getIntent();
        PokemonData pokemonData = (PokemonData) intent.getSerializableExtra("pokemon");


        int id = intent.getIntExtra("id",0);
        String name = intent.getStringExtra("name");
        String weight = intent.getStringExtra("weight");
        String height = intent.getStringExtra("height");
        String type = intent.getStringExtra("type1");
        String type2 = intent.getStringExtra("type2");

        //Cambiar color topLayer
        ConstraintLayout topLayer = (ConstraintLayout) findViewById(R.id.topLayer);
        topLayer.setBackgroundResource(R.color.grass);

        TextView tv1 = (TextView)findViewById(R.id.pokeName);
        tv1.setText(name);

        TextView tv5 = (TextView)findViewById(R.id.id);
        tv5.setText("#"+id);

        TextView tv2 = (TextView)findViewById(R.id.pokeWeight);
        tv2.setText(weight);


        TextView tv3 = (TextView)findViewById(R.id.pokeHeight);
        tv3.setText(height);


        TextView tv4 = (TextView)findViewById(R.id.pokeType);
        tv4.setText(type);

        if(type2!=null) {
            TextView tv6 = (TextView) findViewById(R.id.pokeType2);
            tv6.setText(type2);
        }



        ImageView imageView = (ImageView) findViewById(R.id.pokeImage);

        Glide.with(this).load("https://img.pokemondb.net/artwork/large/"+name+".jpg").into(imageView);

    }



}
