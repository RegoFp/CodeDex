package com.example.codedex.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Parcel
public class ChainLink {

    public ArrayList<ChainLink> evolves_to;
    public SpeciesList species;

    public ArrayList<ChainLink> getEvolves_to() {
        return evolves_to;
    }

    public SpeciesList getSpecies() {
        return species;
    }
}
