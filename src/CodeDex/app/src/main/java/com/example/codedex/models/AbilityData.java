package com.example.codedex.models;

import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class AbilityData {

    String name;

    ArrayList<Effect_entries> effect_entries;

    ArrayList<FlavorText> flavor_text_entries;

    private ArrayList<AbilityPokemonList> pokemon;


    public String getName() {
        return name;
    }

    public ArrayList<Effect_entries> getEffect_entries() {
        return effect_entries;
    }

    public ArrayList<FlavorText> getFlavor_text_entries() {
        return flavor_text_entries;
    }

    public ArrayList<AbilityPokemonList> getPokemon() {
        return pokemon;
    }
}
