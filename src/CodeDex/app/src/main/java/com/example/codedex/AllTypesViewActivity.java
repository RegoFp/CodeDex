package com.example.codedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;

import com.example.codedex.adapters.AllTypesAdapter;
import com.example.codedex.models.Type;
import com.example.codedex.models.TypeResults;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Objects;

public class AllTypesViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AllTypesAdapter allTypesAdapter;
    TypeResults typeResults;
    RetrofitInstance retrofitInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_types_view);


        //Hide topbar and change color of statusBar
        Objects.requireNonNull(getSupportActionBar()).hide();
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.pokeRed));

        //Change color of botton NavigationBar
        getWindow().setNavigationBarColor(getResources().getColor(R.color.pokeRed));

        //Receives the list of types
        Intent intent = getIntent();
        typeResults  = Parcels.unwrap(getIntent().getParcelableExtra("types"));
        ArrayList<Type> typeList = typeResults.getResults();

        //Removes misc types from the list
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            typeList.removeIf(t -> t.getId()>10000);
        }else {

            for (int i = 0; i < typeList.size(); i++) {
                if (typeList.get(i).getId() > 1000) {
                    typeList.remove(i);
                    i--;
                }
            }
        }

        retrofitInstance = new RetrofitInstance();
        retrofitInstance.startRetrofit();

        //RecyclerView setup
        recyclerView = (RecyclerView) findViewById(R.id.AllTypesRecycler);

        allTypesAdapter = new AllTypesAdapter(this, new AllTypesAdapter.onItemListener() {
            @Override
            public void onItemClick(int position) {
                String id = String.valueOf(allTypesAdapter.getCurrentList().get(position).getId());
                retrofitInstance.getType(getApplicationContext(), id);


            }
        });
        recyclerView.setAdapter( allTypesAdapter);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        allTypesAdapter.addItem(typeList);


    }
}