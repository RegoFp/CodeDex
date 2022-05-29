package com.example.codedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codedex.adapters.ListPokemonAdapter;
import com.example.codedex.models.FlavorText;
import com.example.codedex.models.MoveData;
import com.example.codedex.models.Pokemon;
import com.example.codedex.models.PokemonData;
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

public class MoveViewActivity extends AppCompatActivity implements ListPokemonAdapter.onItemListener{

    private MoveData moveData;

    private RecyclerView recyclerView;
    private ListPokemonAdapter listPokemonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_view);

        getSupportActionBar().hide();


        Intent intent = getIntent();
        moveData = Parcels.unwrap(getIntent().getParcelableExtra("move"));

        TextView textView1 = findViewById(R.id.moveDataName);
        textView1.setText(moveData.getName().toUpperCase().replace("-"," "));

        TextView textView2 = findViewById(R.id.moveDataAccuracy);
        textView2.setText(""+moveData.getAccuracy());

        TextView textView3 = findViewById(R.id.moveDataPP);
        textView3.setText(""+moveData.getPp());

        TextView textView4 = findViewById(R.id.moveDataPower);
        textView4.setText(""+moveData.getPower());


        ImageView imageView1 = findViewById(R.id.moveDataDamageType);
        String type = moveData.getType().getName();
        String uri = "@drawable/type_"+type;  // where myresource (without the extension) is the file
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        imageView1.setImageDrawable(res);


        ImageView imageView2 = findViewById(R.id.moveDataDamageClass);
        String dmg = moveData.getDamage_class().getName();
        String uri2 = "@drawable/dmg_"+dmg;  // where myresource (without the extension) is the file
        int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
        Drawable res2 = getResources().getDrawable(imageResource2);
        imageView2.setImageDrawable(res2);

        SetColor();

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


        ScrollView scrollView = (ScrollView) findViewById(R.id.scroll);
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








        recyclerView = (RecyclerView) findViewById(R.id.moveDataPokemon);


        listPokemonAdapter = new ListPokemonAdapter(this,this);
        recyclerView.setAdapter(listPokemonAdapter);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<Pokemon> pokemons = moveData.getLearned_by_pokemon();


        //Remoces alternative forms from the list
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
        CardView cardView = (CardView) findViewById(R.id.moveDataCardview);
        CardView cardView2 = (CardView) findViewById(R.id.moveDataTopCard);
        CardView cardView3 = (CardView) findViewById(R.id.moveDataDescriptionCard);
        CardView cardView4 = (CardView) findViewById(R.id.scrollcard);
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



        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.moveDataBackground);
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

        getPokemon(String.valueOf(listPokemonAdapter.getCurrentList().get(position).getId()));
    }

    private void getPokemon(String id){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl("https://pokeapi.co/api/v2/")
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        Retrofit retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();

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



                i.putExtra("pokemon", wrapped);

                startActivity(i);




            }

            @Override
            public void onFailure(Call<PokemonData> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"ERROR", Toast.LENGTH_LONG).show();
            }
        });

    }

}
