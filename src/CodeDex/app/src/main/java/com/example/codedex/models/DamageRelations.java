package com.example.codedex.models;

import com.example.codedex.models.damageRelations.DoubleDamageFrom;
import com.example.codedex.models.damageRelations.DoubleDamageTo;
import com.example.codedex.models.damageRelations.HalfDamageFrom;
import com.example.codedex.models.damageRelations.HalfDamageTo;
import com.example.codedex.models.damageRelations.NoDamageFrom;
import com.example.codedex.models.damageRelations.NoDamageTo;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class DamageRelations {

    List<DoubleDamageFrom> double_damage_from;
    List<DoubleDamageTo> double_damage_to;
    List<HalfDamageFrom> half_damage_from;
    List<HalfDamageTo> half_damage_to;
    List<NoDamageFrom> no_damage_from;
    List<NoDamageTo> no_damage_to;


}
