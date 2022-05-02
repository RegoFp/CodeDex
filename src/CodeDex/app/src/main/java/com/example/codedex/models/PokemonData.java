package com.example.codedex.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

//http://parceler.org/
@org.parceler.Parcel
public class PokemonData{



    private int id;
    private String name;
    private String height;
    private String weight;
    private List<TypesList> types;
    private List<MoveList> moves;


    public PokemonData() {

    }

    protected PokemonData(Parcel in) {
        id = in.readInt();
        name = in.readString();
        height = in.readString();
        weight = in.readString();
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

    public List<MoveList> getMoves() {
        return moves;
    }


}
