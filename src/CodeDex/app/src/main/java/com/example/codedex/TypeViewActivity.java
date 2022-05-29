package com.example.codedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.codedex.adapters.ListTypeRelationsAdapter;
import com.example.codedex.models.TypeData;

import org.parceler.Parcels;


public class TypeViewActivity extends AppCompatActivity implements ListTypeRelationsAdapter.onItemListener{

    TypeData typeData;
    RecyclerView recyclerViewDoubleTo;
    RecyclerView recyclerViewHalfTo;
    RecyclerView recyclerViewDoubleFrom;
    RecyclerView recyclerViewHalfFrom;
    RecyclerView recyclerViewNodamageTo;
    RecyclerView recyclerViewNodamageFrom;

    ListTypeRelationsAdapter listTypeRelationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_view);

        getSupportActionBar().hide();

        typeData = Parcels.unwrap(getIntent().getParcelableExtra("type"));

        SetColor();


        String typeName = typeData.getName();
        ImageView imageview = (ImageView) findViewById(R.id.parentType);
        String uri = "@drawable/type_"+typeName;  // where myresource (without the extension) is the file
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        imageview.setImageDrawable(res);

        //DoubleTo
        recyclerViewDoubleTo = findViewById(R.id.TypeDoubleTo);
        listTypeRelationsAdapter = new ListTypeRelationsAdapter(this, this);
        recyclerViewDoubleTo.setAdapter(listTypeRelationsAdapter);
        recyclerViewDoubleTo.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewDoubleTo.setLayoutManager(layoutManager);
        listTypeRelationsAdapter.addItem(typeData.getDamage_relations().getDouble_damage_to());


        //HalfTo
        recyclerViewHalfTo = findViewById(R.id.TypeHalfTo);
        listTypeRelationsAdapter = new ListTypeRelationsAdapter(this, this);
        recyclerViewHalfTo.setAdapter(listTypeRelationsAdapter);
        recyclerViewHalfTo.setHasFixedSize(true);
        LinearLayoutManager layoutManagerHalfTo = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewHalfTo.setLayoutManager(layoutManagerHalfTo);
        listTypeRelationsAdapter.addItem(typeData.getDamage_relations().getHalf_damage_to());

        //DoubleFrom
        recyclerViewDoubleFrom =  findViewById(R.id.TypeDoubleFrom);
        listTypeRelationsAdapter = new ListTypeRelationsAdapter(this, this);
        recyclerViewDoubleFrom.setAdapter(listTypeRelationsAdapter);
        recyclerViewDoubleFrom.setHasFixedSize(true);
        LinearLayoutManager layoutManagerDoubleFrom = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewDoubleFrom.setLayoutManager(layoutManagerDoubleFrom);
        listTypeRelationsAdapter.addItem(typeData.getDamage_relations().getDouble_damage_from());

        //HalfFrom
        recyclerViewHalfFrom = findViewById(R.id.TypeHalfFrom);
        listTypeRelationsAdapter = new ListTypeRelationsAdapter(this, this);
        recyclerViewHalfFrom.setAdapter(listTypeRelationsAdapter);
        recyclerViewHalfFrom.setHasFixedSize(true);
        LinearLayoutManager layoutManagerHalfFrom = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewHalfFrom.setLayoutManager(layoutManagerHalfFrom);
        listTypeRelationsAdapter.addItem(typeData.getDamage_relations().getHalf_damage_from());

        //NoTo
        recyclerViewNodamageTo =  findViewById(R.id.TypeNoTo);
        listTypeRelationsAdapter = new ListTypeRelationsAdapter(this, this);
        recyclerViewNodamageTo.setAdapter(listTypeRelationsAdapter);
        recyclerViewNodamageTo.setHasFixedSize(true);
        LinearLayoutManager layoutManagerNoTo = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewNodamageTo.setLayoutManager(layoutManagerNoTo);
        listTypeRelationsAdapter.addItem(typeData.getDamage_relations().getNo_damage_to());

        if(typeData.getDamage_relations().getNo_damage_to().size() > 0){
            TextView textView1 =  findViewById(R.id.noTypes1);
            textView1.setVisibility(View.GONE);

        }

        //NoFrom
        recyclerViewNodamageFrom = findViewById(R.id.TypeNoFrom);
        listTypeRelationsAdapter = new ListTypeRelationsAdapter(this, this);
        recyclerViewNodamageFrom.setAdapter(listTypeRelationsAdapter);
        recyclerViewNodamageFrom.setHasFixedSize(true);
        LinearLayoutManager layoutManagerNoFrom = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewNodamageFrom.setLayoutManager(layoutManagerNoFrom);
        listTypeRelationsAdapter.addItem(typeData.getDamage_relations().getNo_damage_from());

        if(typeData.getDamage_relations().getNo_damage_from().size() > 0){
            TextView textView2 =  findViewById(R.id.noTypes2);
            textView2.setVisibility(View.GONE);

        }


    }

    private void SetColor() {
        CardView cardView =  findViewById(R.id.cardViewType1);
        CardView cardView2 =  findViewById(R.id.cardViewType2);
        CardView cardView3 =  findViewById(R.id.cardViewType3);
        CardView cardView4 =  findViewById(R.id.cardViewType4);
        CardView cardView5 =  findViewById(R.id.cardViewType5);
        CardView cardView6 =  findViewById(R.id.cardViewType6);
        String type = typeData.getName();

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

        cardView5.setCardBackgroundColor(color);
        cardView6.setCardBackgroundColor(color);

        //Crea un color en base al rbg añadiendole alpha
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            color = Color.argb(150,red,green,blue);
        }



        ScrollView linearLayout= (ScrollView) findViewById(R.id.TypeBackground);
        linearLayout.setBackgroundColor(color);


        Window window = this.getWindow();
        window.setStatusBarColor(color);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(color);
        }


        System.out.println( ""+ red);




    }

    @Override
    public void onItemClick(int position) {

    }




}