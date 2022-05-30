package com.example.codedex;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;


import com.example.codedex.adapters.AllNaturesAdapter;
import com.example.codedex.models.Nature;
import com.example.codedex.models.NatureResults;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Objects;


public class AllNaturesActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    AllNaturesAdapter allNaturesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_natures);


        //Hide topbar and change color of statusBar
        Objects.requireNonNull(getSupportActionBar()).hide();
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.pokeRed));

        //Change color of botton NavigationBar
        window.setNavigationBarColor(getResources().getColor(R.color.pokeRed));

        //Receives the list of types
        NatureResults natureResults = Parcels.unwrap(getIntent().getParcelableExtra("natures"));
        ArrayList<Nature> natureList = natureResults.getResults();

        //Set up Searchview
        SearchView searchView = findViewById(R.id.allNaturesSearch);
        searchView.setOnQueryTextListener(this);


        //Set Ups RecyclerView
        RecyclerView recyclerView = findViewById(R.id.AllNaturesRecycler);
        allNaturesAdapter = new AllNaturesAdapter(getApplicationContext(), position -> {

        });

        recyclerView.setAdapter(allNaturesAdapter);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        allNaturesAdapter.addMoveItem(natureList);

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        allNaturesAdapter.filtderData(newText);
        return false;
    }
}