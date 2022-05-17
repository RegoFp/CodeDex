package com.example.codedex;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.codedex.models.MoveList;
import com.example.codedex.models.Pokemon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class PokeViewMovesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<MoveList> Movelist = new ArrayList<>();
    private ListMovesAdapter listMovesAdapter;

    public PokeViewMovesFragment(ArrayList<MoveList> moveList) {

        Movelist = moveList;

        //Ordena la lista por nivel en el que se aprende
        Collections.sort(Movelist, new Comparator<MoveList>() {
            @Override public int compare(MoveList x, MoveList y) {
                return x.compareTo(y);
            }
        });
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


        ArrayList<MoveList> filteredMoveList = new ArrayList<>();

        for (MoveList ml : Movelist) {
            if(ml.getVersion_group_details().get(0).getMove_learn_method().getName().equalsIgnoreCase("Level-up")){

                filteredMoveList.add(ml);
            }

        }


        Collections.sort(filteredMoveList, new Comparator<MoveList>() {
            @Override public int compare(MoveList x, MoveList y) {
                return x.compareTo(y);
            }
        });

        listMovesAdapter.clearAllData();
        listMovesAdapter.addMoveItem(filteredMoveList);

        //Configures onclicklisteners to the buttons
        onclickButtons(root);






        return root;
    }

    public void onclickButtons(View root){

        Button buttonAll = (Button) root.findViewById(R.id.filterEgg);
        buttonAll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                ArrayList<MoveList> filteredMoveList = new ArrayList<>();

                for (MoveList ml : Movelist) {
                    if(ml.getVersion_group_details().get(0).getMove_learn_method().getName().equalsIgnoreCase("Egg")){

                        filteredMoveList.add(ml);
                    }

                }


                Collections.sort(filteredMoveList, new Comparator<MoveList>() {
                    @Override public int compare(MoveList x, MoveList y) {
                        return x.compareTo(y);
                    }
                });

                listMovesAdapter.clearAllData();
                listMovesAdapter.addMoveItem(filteredMoveList);
                recyclerView.getLayoutManager().scrollToPosition(0);
                // do something
            }
        });


        //Boton Level
        Button buttonLevel = (Button) root.findViewById(R.id.filterLevel);
        buttonLevel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                ArrayList<MoveList> filteredMoveList = new ArrayList<>();

                for (MoveList ml : Movelist) {
                    if(ml.getVersion_group_details().get(0).getMove_learn_method().getName().equalsIgnoreCase("level-up")){

                        filteredMoveList.add(ml);
                    }

                }


                Collections.sort(filteredMoveList, new Comparator<MoveList>() {
                    @Override public int compare(MoveList x, MoveList y) {
                        return x.compareTo(y);
                    }
                });

                listMovesAdapter.clearAllData();
                listMovesAdapter.addMoveItem(filteredMoveList);
                recyclerView.getLayoutManager().scrollToPosition(0);
                // do something
            }
        });

        Button buttonMachine = (Button) root.findViewById(R.id.filterMachine);
        buttonMachine.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                ArrayList<MoveList> filteredMoveList = new ArrayList<>();

                for (MoveList ml : Movelist) {
                    if(ml.getVersion_group_details().get(0).getMove_learn_method().getName().equalsIgnoreCase("machine")){

                        filteredMoveList.add(ml);
                    }

                }
                listMovesAdapter.clearAllData();
                listMovesAdapter.addMoveItem(filteredMoveList);
                recyclerView.getLayoutManager().scrollToPosition(0);
                // do something
            }
        });

        //Boton tutor
        Button buttonTutor = (Button) root.findViewById(R.id.filterTutor);
        buttonTutor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getContext(),"test", Toast.LENGTH_LONG).show();

                ArrayList<MoveList> filteredMoveList = new ArrayList<>();

                for (MoveList ml : Movelist) {
                    if(ml.getVersion_group_details().get(0).getMove_learn_method().getName().equalsIgnoreCase("tutor")){

                        filteredMoveList.add(ml);
                    }

                }
                listMovesAdapter.clearAllData();
                listMovesAdapter.addMoveItem(filteredMoveList);
                recyclerView.getLayoutManager().scrollToPosition(0);
                // do something
            }
        });

    }

}