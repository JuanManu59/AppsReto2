package com.example.reto2.modelo;

import java.io.Serializable;

public class Type implements Serializable {
    private String name;

    public Type() {
    }

    public Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
