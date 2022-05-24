package com.example.codedex.models;

import org.parceler.Parcel;

@Parcel
public class AbilityPokemonList {

    private Boolean is_hidden;
    private Pokemon pokemon;
    private String slot;

    public Boolean getIs_hidden() {
        return is_hidden;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public String getSlot() {
        return slot;
    }
}
