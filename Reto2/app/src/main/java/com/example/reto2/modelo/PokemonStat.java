package com.example.reto2.modelo;

import java.io.Serializable;

public class PokemonStat implements Serializable {
    private String base_stat;

    public PokemonStat() {
    }

    public PokemonStat(String base_stat) {
        this.base_stat = base_stat;
    }

    public String getBase_stat() {
        return base_stat;
    }

    public void setBase_stat(String base_stat) {
        this.base_stat = base_stat;
    }
}
