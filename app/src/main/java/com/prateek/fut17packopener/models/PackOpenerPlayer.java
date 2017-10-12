package com.prateek.fut17packopener.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import lombok.Data;

/**
 * Created by prateek on 13/8/17.
 */

@Data
public class PackOpenerPlayer implements Comparable<PackOpenerPlayer> {

    public String id;
    public String name;
    public String image;
    public int clubId;
    public int leagueId;
    public int nationId;
    public String position;
    public int rating;
    public int pace;
    public int shot;
    public int pass;
    public int dribble;
    public int defend;
    public int physical;
    public String color;
    public Nation nation;
    public League league;
    public Club club;

    public PackOpenerPlayer() {

    }

    @Override
    public int compareTo(@NonNull PackOpenerPlayer packOpenerPlayer) {
        return packOpenerPlayer.rating - this.rating;
    }
}
