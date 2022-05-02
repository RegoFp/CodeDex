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
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.codedex.models.PokemonData;

import org.parceler.Parcels;

import java.io.InputStream;
import java.net.URL;

public class PokemonView extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_view);

        Intent intent = getIntent();

        PokemonData pokemonData = Parcels.unwrap(getIntent().getParcelableExtra("pokemon"));

        //Cambiar color topLayer
        ConstraintLayout topLayer = (ConstraintLayout) findViewById(R.id.topLayer);
        topLayer.setBackgroundResource(R.color.grass);

        TextView tv1 = (TextView)findViewById(R.id.pokeName);
        tv1.setText(pokemonData.getName());




        ImageView imageView = (ImageView) findViewById(R.id.pokeImage);

        //arte
        //Glide.with(this).load("https://img.pokemondb.net/artwork/large/"+name+".jpg").into(imageView);

        //sprites
        Glide.with(this).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+pokemonData.getId()+".png").into(imageView);
    }



}
