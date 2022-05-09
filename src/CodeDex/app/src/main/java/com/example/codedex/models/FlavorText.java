package com.example.codedex.models;

import org.parceler.Parcel;

@Parcel
public class FlavorText {

    String flavor_text;
    Languaje language;

    public String getFlavor_text() {
        return flavor_text;
    }

    public Languaje getLanguage() {
        return language;
    }
}
