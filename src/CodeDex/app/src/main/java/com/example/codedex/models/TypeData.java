package com.example.codedex.models;

import org.parceler.Parcel;

@Parcel
public class TypeData {

    String name;
    private DamageRelations damage_relations;


    public String getName() {
        return name;
    }

    public DamageRelations getDamage_relations() {
        return damage_relations;
    }
}
