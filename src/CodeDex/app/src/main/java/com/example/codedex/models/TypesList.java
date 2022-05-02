package com.example.codedex.models;

import android.os.Parcel;
import android.os.Parcelable;

public class TypesList implements Parcelable {

    private String slot;
    private Type type;

    protected TypesList(Parcel in) {
        slot = in.readString();
        type = in.readParcelable(Type.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(slot);
        dest.writeParcelable(type, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TypesList> CREATOR = new Creator<TypesList>() {
        @Override
        public TypesList createFromParcel(Parcel in) {
            return new TypesList(in);
        }

        @Override
        public TypesList[] newArray(int size) {
            return new TypesList[size];
        }
    };

    public String getSlot() {
        return slot;
    }

    public Type getType() {
        return type;
    }
}
