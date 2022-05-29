package com.example.codedex;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;

import com.example.codedex.adapters.AllNaturesAdapter;
import com.example.codedex.models.Nature;
import com.example.codedex.models.NatureResults;
import com.example.codedex.models.Type;
import com.example.codedex.models.TypeData;
import com.example.codedex.pokeapi.PokemonClient;

import org.parceler.Parcels;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AllNaturesActivity extends AppCompatActivity {

    private NatureResults natureResults;
    private RecyclerView recyclerView;
    private AllNaturesAdapter allNaturesAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_natures);

        context = this;
        //Receives the list of types
        Intent intent = getIntent();
        natureResults  = Parcels.unwrap(getIntent().getParcelableExtra("natures"));
        ArrayList<Nature> natureList = natureResults.getResults();

        recyclerView = (RecyclerView) findViewById(R.id.AllNaturesRecycler);


        allNaturesAdapter = new AllNaturesAdapter(this, new AllNaturesAdapter.onItemListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        recyclerView.setAdapter(allNaturesAdapter);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        allNaturesAdapter.addMoveItem(natureList);

    }


}