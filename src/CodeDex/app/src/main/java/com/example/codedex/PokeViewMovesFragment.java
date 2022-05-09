package com.example.codedex;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.codedex.models.MoveList;
import com.example.codedex.models.Pokemon;

import java.util.ArrayList;


public class PokeViewMovesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<MoveList> Movelist = new ArrayList<>();
    private ListMovesAdapter listMovesAdapter;

    public PokeViewMovesFragment(ArrayList<MoveList> moveList) {
        this.Movelist = moveList;
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_poke_view_moves, null);

        //RecyclerView
        recyclerView = (RecyclerView) root.findViewById(R.id.movesList);


        listMovesAdapter = new ListMovesAdapter(root.getContext());
        recyclerView.setAdapter(listMovesAdapter);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);

        listMovesAdapter.addMoveItem(Movelist);

        

        return root;
    }
}