package com.coursera.aad.capstoneapp.models;

import java.io.Serializable;

public class Statistic implements Serializable {
    private long id = 0;
    private String continent = "";
    private String country = "";
    private String population = "";
    private Cases cases = new Cases();
    private Deaths deaths = new Deaths();
    private Tests tests = new Tests();
    private String day = "";
    private String time = "";

    public Statistic() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public Cases getCases() {
        return cases;
    }

    public void setCases(Cases cases) {
        this.cases = cases;
    }

    public Deaths getDeaths() {
        return deaths;
    }

    public void setDeaths(Deaths deaths) {
        this.deaths = deaths;
    }

    public Tests getTests() {
        return tests;
    }

    public void setTests(Tests tests) {
        this.tests = tests;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
