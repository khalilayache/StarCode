package com.khalilayache.starcode.models;

public class StarWarsShip implements java.io.Serializable {
    private static final long serialVersionUID = 8310703454928194839L;

    private String name;
    private String model;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
