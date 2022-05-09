package com.example.codedex.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//http://parceler.org/
@org.parceler.Parcel
public class PokemonData{



    private int id;
    private String name;
    private String height;
    private String weight;
    private List<TypesList> types;
    private ArrayList<MoveList> moves;
    private SpeciesList species;
    private List<AbilitiesList> abilities;

    public PokemonData() {

    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public List<TypesList> getTypes() {
        return types;
    }

    public ArrayList<MoveList> getMoves() {
        return moves;
    }

    public SpeciesList getSpecies() {
        return species;
    }

    public List<AbilitiesList> getAbilities() {
        return abilities;
    }
}
