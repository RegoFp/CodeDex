package com.example.codedex.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class MoveData {

    private int accuracy;
    private int power;
    private int pp;
    private DamageClass damage_class;
    List<FlavorText> flavor_text_entries;
    private ArrayList<Pokemon> learned_by_pokemon;

    public int getAccuracy() {
        return accuracy;
    }

    public int getPower() {
        return power;
    }

    public int getPp() {
        return pp;
    }

    public DamageClass getDamage_class() {
        return damage_class;
    }

    public List<FlavorText> getFlavor_text_entries() {
        return flavor_text_entries;
    }

    public ArrayList<Pokemon> getLearned_by_pokemon() {
        return learned_by_pokemon;
    }


}
