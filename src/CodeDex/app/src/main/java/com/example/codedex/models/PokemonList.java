package com.example.codedex.models;

import java.util.List;

public class PokemonList {

    private int count;
    private String next;
    private String previous;
    private List<Pokemon> results;

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<Pokemon> getResults() {
        return results;
    }
}
