package com.dega.backbase.model;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by davedega on 25/03/18.
 */
public class Entry {

    private String country;
    private String name;
    private Integer id;
    private Coord coord;

    public Entry(String country, String name) {
        this.country = country;
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    @Override
    public String toString() {
        return name + ", " + country;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entry)) return false;

        Entry entry = (Entry) o;

        if (getCountry() != null ? !getCountry().equals(entry.getCountry()) : entry.getCountry() != null)
            return false;
        return getName() != null ? getName().equals(entry.getName()) : entry.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getCountry() != null ? getCountry().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }
}