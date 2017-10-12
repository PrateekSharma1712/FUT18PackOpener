package com.prateek.fut17packopener.models;

/**
 * Created by prateek on 9/9/17.
 */

public enum FilterTypes {

    NATION("nation"),
    LEAGUE("league"),
    CARD("card");

    String filterName;

    FilterTypes(String filter) {
        this.filterName = filter;
    }

    public String getFilterName() {
        return filterName;
    }
}
