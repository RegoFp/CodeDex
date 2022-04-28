package com.example.codedex.models;

import java.util.List;

public class PokemonData {

    private String name;
    private String height;
    private String weight;
    private List<Type> types;

    public List<Type> getTypes() {
        return types;
    }





    public PokemonData() {

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


}
