package com.example.codedex.models;

import org.parceler.Parcel;

@Parcel
public class VersionGroupDetails {
    private int level_learned_at;
    private MoveLearnMethod move_learn_method;


    public int getLevel_learned_at() {
        return level_learned_at;
    }

    public MoveLearnMethod getMove_learn_method() {
        return move_learn_method;
    }
}
