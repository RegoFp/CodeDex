package com.example.codedex;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import com.example.codedex.adapters.AllNaturesAdapter;
import com.example.codedex.models.Nature;
import com.example.codedex.models.NatureResults;

import org.parceler.Parcels;

import java.util.ArrayList;


public class AllNaturesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_natures);

        //Receives the list of types
        NatureResults natureResults = Parcels.unwrap(getIntent().getParcelableExtra("natures"));
        ArrayList<Nature> natureList = natureResults.getResults();


        //Set Ups RecyclerView
        RecyclerView recyclerView = findViewById(R.id.AllNaturesRecycler);
        AllNaturesAdapter allNaturesAdapter = new AllNaturesAdapter(getApplicationContext(), position -> {

        });

        recyclerView.setAdapter(allNaturesAdapter);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        allNaturesAdapter.addMoveItem(natureList);

    }


}