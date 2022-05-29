package com.example.codedex.models;


import org.parceler.Parcel;

@Parcel
public class Type  {


    private String name;
    private String url;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;

    }


    public int getId() {
        String[] urlSplice = url.split("/");
        return Integer.parseInt(urlSplice[urlSplice.length -1]);
    }

}
