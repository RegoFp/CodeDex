package com.example.codedex.models;

import java.util.List;

public class PokemonData {

    private String name;
    private String height;
    private String weight;
    private List<TypesList> types;

    public List<TypesList> getTypes() {
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
