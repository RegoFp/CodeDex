package com.example.codedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;


import com.example.codedex.adapters.ListPokemonAdapter;
import com.example.codedex.models.FlavorText;
import com.example.codedex.models.MoveData;
import com.example.codedex.models.Pokemon;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Objects;


public class MoveViewActivity extends AppCompatActivity implements ListPokemonAdapter.onItemListener{

    private MoveData moveData;

    private ListPokemonAdapter listPokemonAdapter;
    RetrofitInstance retrofitInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_view);

        //Hides ActionBar
        Objects.requireNonNull(getSupportActionBar()).hide();

        //Start Retrofit
        retrofitInstance = new RetrofitInstance();
        retrofitInstance.startRetrofit();

        //Receive moveData
        moveData = Parcels.unwrap(getIntent().getParcelableExtra("move"));

        //Set texts
        TextView textView1 = findViewById(R.id.moveDataName);
        textView1.setText(moveData.getName().toUpperCase().replace("-"," "));

        TextView textView2 = findViewById(R.id.moveDataAccuracy);
        textView2.setText(String.valueOf(moveData.getAccuracy()));

        TextView textView3 = findViewById(R.id.moveDataPP);
        textView3.setText(String.valueOf(moveData.getPp()));

        TextView textView4 = findViewById(R.id.moveDataPower);
        textView4.setText(String.valueOf(moveData.getPower()));

        TextView textView8 = findViewById(R.id.moveDataEffect);
        String effect = moveData.getEffect_entries().get(0).getEffect();
        textView8.setText(effect.replace("$effect_chance", ""+moveData.getEffect_chance()));

        TextView textView5 = findViewById(R.id.moveDataDescription);
        for (final FlavorText flavorText: moveData.getFlavor_text_entries()){
            if(flavorText.getLanguage().getName().equalsIgnoreCase("en")){
                textView5.setText(flavorText.getFlavor_text().replaceAll("\n"," "));
                break;
            }

        }

        //Set type image
        ImageView imageView1 = findViewById(R.id.moveDataDamageType);
        String type = moveData.getType().getName();
        String uri = "@drawable/type_"+type;  // where myresource (without the extension) is the file
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        imageView1.setImageDrawable(res);

        //Set class image
        ImageView imageView2 = findViewById(R.id.moveDataDamageClass);
        String dmg = moveData.getDamage_class().getName();
        String uri2 = "@drawable/dmg_"+dmg;  // where myresource (without the extension) is the file
        int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
        Drawable res2 = getResources().getDrawable(imageResource2);
        imageView2.setImageDrawable(res2);



        SetColor();

        //Set Up ScrollView
        ScrollView scrollView = findViewById(R.id.scroll);
        //Checks how many lines the effects has, and if it's less than 5 sets it's height as wrap content
        textView8.post(new Runnable() {
            @Override
            public void run() {
                int lineCount = textView8.getLineCount();
                if(lineCount<5){
                    ViewGroup.LayoutParams params = scrollView.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    scrollView.setLayoutParams(params);

                }

            }
        });


        //Set Up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.moveDataPokemon);

        listPokemonAdapter = new ListPokemonAdapter(this,this);
        recyclerView.setAdapter(listPokemonAdapter);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<Pokemon> pokemons = moveData.getLearned_by_pokemon();


        //Removces alternative forms from the list
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            pokemons.removeIf(t -> t.getId()>300);
        }else{

            for(int i = 0; i < pokemons.size(); i++){
                if(pokemons.get(i).getId() > 3000){
                    pokemons.remove(i);
                    i--;
                }
            }


        }


        listPokemonAdapter.addPokemonItem(pokemons);





    }

    private void SetColor() {
        CardView cardView =  findViewById(R.id.moveDataCardview);
        CardView cardView2 = findViewById(R.id.moveDataTopCard);
        CardView cardView3 = findViewById(R.id.moveDataDescriptionCard);
        CardView cardView4 = findViewById(R.id.scrollcard);
        String type = moveData.getType().getName();

        switch (type){
            case "bug":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.Bug));
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.Bug));
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.Bug));
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.Bug));
                break;
            case "dark":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.Dark));
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.Dark));
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.Dark));
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.Dark));
                break;
            case "dragon":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.Dragon));
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.Dragon));
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.Dragon));
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.Dragon));
                break;
            case "electric":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.Electric));
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.Electric));
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.Electric));
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.Electric));
                break;
            case "fairy":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.Fairy));
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.Fairy));
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.Fairy));
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.Fairy));
                break;
            case "fighting":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.Fighting));
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.Fighting));
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.Fighting));
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.Fighting));
                break;
            case "fire":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.fire));
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.fire));
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.fire));
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.fire));
                break;
            case "flying":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.Flying));
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.Flying));
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.Flying));
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.Flying));
                break;
            case "ghost":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.Ghost));
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.Ghost));
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.Ghost));
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.Ghost));
                break;
            case "grass":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.grass));
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.grass));
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.grass));
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.grass));
                break;
            case "ground":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.Ground));
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.Ground));
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.Ground));
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.Ground));
                break;
            case "ice":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.Ice));
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.Ice));
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.Ice));
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.Ice));
                break;
            case "normal":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.Normal));
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.Normal));
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.Normal));
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.Normal));
                break;
            case "poison":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.Poison));
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.Poison));
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.Poison));
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.Poison));
                break;
            case "psychic":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.Psychic));
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.Psychic));
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.Psychic));
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.Psychic));
                break;
            case "rock":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.Rock));
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.Rock));
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.Rock));
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.Rock));
                break;
            case "steel":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.Steel));
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.Steel));
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.Steel));
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.Steel));
                break;
            case "water":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.Water));
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.Water));
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.Water));
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.Water));
                break;
            case "shadow":
                cardView.setCardBackgroundColor(getResources().getColor(R.color.Shadow));
                cardView2.setCardBackgroundColor(getResources().getColor(R.color.Shadow));
                cardView3.setCardBackgroundColor(getResources().getColor(R.color.Shadow));
                cardView4.setCardBackgroundColor(getResources().getColor(R.color.Shadow));
                break;

        }


        //Coge el color de una de las cards
        int color = cardView.getCardBackgroundColor().getDefaultColor();

        //Separa los valores en rgb
        int red = Color.red(color);
        int  green = Color.green(color);
        int  blue = Color.blue(color);

        //Crea un color en base al rbg añadiendole alpha
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            color = Color.argb(150,red,green,blue);
        }



        ConstraintLayout constraintLayout = findViewById(R.id.moveDataBackground);
        constraintLayout.setBackgroundColor(color);


        Window window = this.getWindow();
        window.setStatusBarColor(color);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(color);
        }


        System.out.println( ""+ red);




    }


    @Override
    public void onItemClick(int position) {
        retrofitInstance.getPokemon(getApplicationContext(),String.valueOf(listPokemonAdapter.getCurrentList().get(position).getId()));
    }



}
