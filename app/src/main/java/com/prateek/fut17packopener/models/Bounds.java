package com.prateek.fut17packopener.models;

/**
 * Created by Prateek on 8/9/17.
 */

public class Bounds {
    private int start;
    private int end;

    public Bounds(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bounds)) return false;

        Bounds bounds = (Bounds) o;

        if (getStart() != bounds.getStart()) return false;
        return getEnd() == bounds.getEnd();

    }

    @Override
    public int hashCode() {
        int result = getStart();
        result = 31 * result + getEnd();
        return result;
    }
}
