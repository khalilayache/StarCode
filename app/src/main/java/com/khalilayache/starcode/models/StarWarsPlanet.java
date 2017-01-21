package com.khalilayache.starcode.models;

public class StarWarsPlanet implements java.io.Serializable {
    private static final long serialVersionUID = 8414044926357957288L;

    private String climate;
    private String url;
    private String population;
    private String diameter;
    private String name;


    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
