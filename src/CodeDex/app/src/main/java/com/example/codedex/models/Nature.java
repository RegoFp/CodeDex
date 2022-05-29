package com.example.codedex.models;

import org.parceler.Parcel;

@Parcel
public class Nature {

    String name;
    String url;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
        String[] urlSplice = url.split("/");
        return Integer.parseInt(urlSplice[urlSplice.length -1]);
    }

}
