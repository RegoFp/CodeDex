package com.example.codedex.models;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class MoveList {

    private Move move;
    private List<VersionGroupDetails> version_group_details;

    public Move getMove() {
        return move;
    }

    public List<VersionGroupDetails> getVersion_group_details() {
        return version_group_details;
    }
}
