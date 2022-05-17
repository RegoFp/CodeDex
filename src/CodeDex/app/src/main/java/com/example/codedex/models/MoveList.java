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




    public int compareTo(MoveList comparestu) {
        int compareage=((MoveList)comparestu).getVersion_group_details().get(0).getLevel_learned_at();
        /* For Ascending order*/
        return this.getVersion_group_details().get(0).getLevel_learned_at()-compareage;

        /* For Descending order do like this */
        //return compareage-this.studentage;
    }
}


