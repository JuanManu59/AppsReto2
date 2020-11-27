package com.example.reto2.modelo;

import java.io.Serializable;

public class PokemonForm implements Serializable {

    private String url;
    private int number;

    public String getUrl() {
        return url;
    }

    public PokemonForm() {
    }

    public int getNumber() {
        String [] urlPartes = url.split("/");
        return Integer.parseInt(urlPartes[urlPartes.length-1]);
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
