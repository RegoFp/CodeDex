package com.example.codedex.models;






import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class MoveData {

    private String name;
    private int accuracy;
    private int power;
    private int pp;
    private DamageClass damage_class;
    private Type type;
    private int effect_chance;
    List<FlavorText> flavor_text_entries;
    private ArrayList<Pokemon> learned_by_pokemon;
    private MoveMeta meta;
    private List<Effect_entries> effect_entries;


    public String getName() {
        return name;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getPower() {
        return power;
    }

    public int getPp() {
        return pp;
    }

    public int getEffect_chance() {
        return effect_chance;
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

    public MoveMeta getMeta() {
        return meta;
    }

    public Type getType() {
        return type;
    }

    public List<Effect_entries> getEffect_entries() {
        return effect_entries;
    }
}
