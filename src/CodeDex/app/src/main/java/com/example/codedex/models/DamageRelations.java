package com.example.codedex.models;



import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class DamageRelations {

    ArrayList<Type> double_damage_from;
    ArrayList<Type> double_damage_to;
    ArrayList<Type> half_damage_from;
    ArrayList<Type> half_damage_to;
    ArrayList<Type> no_damage_from;
    ArrayList<Type> no_damage_to;

    public ArrayList<Type> getDouble_damage_from() {
        return double_damage_from;
    }

    public ArrayList<Type> getDouble_damage_to() {
        return double_damage_to;
    }

    public ArrayList<Type> getHalf_damage_from() {
        return half_damage_from;
    }

    public ArrayList<Type> getHalf_damage_to() {
        return half_damage_to;
    }

    public ArrayList<Type> getNo_damage_from() {
        return no_damage_from;
    }

    public ArrayList<Type> getNo_damage_to() {
        return no_damage_to;
    }
}
