package com.example.reto2.modelo;

import java.io.Serializable;

public class PokemonType implements Serializable {

    private Type type;

    public PokemonType() {
    }

    public PokemonType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
