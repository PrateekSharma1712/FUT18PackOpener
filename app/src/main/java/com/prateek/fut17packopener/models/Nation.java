package com.prateek.fut17packopener.models;

/**
 * Created by Prateek on 7/14/17.
 */

public class Nation {

    public String image;
    public String abbrName;
    public int id;
    public String name;

    @Override
    public String toString() {
        return "[name : " + name + "]";
    }
}
