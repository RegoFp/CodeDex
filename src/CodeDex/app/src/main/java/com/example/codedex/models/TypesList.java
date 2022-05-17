package com.example.codedex.models;

import android.os.Parcelable;

import org.parceler.Parcel;

@Parcel
public class TypesList  {

    private String slot;
    private Type type;


    public String getSlot() {
        return slot;
    }

    public Type getType() {
        return type;
    }
}
