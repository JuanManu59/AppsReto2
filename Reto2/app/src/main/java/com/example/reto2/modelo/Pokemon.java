package com.example.reto2.modelo;

import java.io.Serializable;
import java.util.List;

public class Pokemon implements Serializable {

    private String name;
    private List<PokemonStat> stats;
    private List<PokemonType> types;
    private List<PokemonForm> forms;

    public Pokemon() {
    }

    public Pokemon(String name, List<PokemonStat> stats, List<PokemonType> types, List<PokemonForm> forms) {
        this.name = name;
        this.stats = stats;
        this.types = types;
        this.forms = forms;
    }

    public List<PokemonForm> getForms() {
        return forms;
    }

    public void setForms(List<PokemonForm> forms) {
        this.forms = forms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PokemonStat> getStats() {
        return stats;
    }

    public void setStats(List<PokemonStat> stats) {
        this.stats = stats;
    }

    public List<PokemonType> getTypes() {
        return types;
    }

    public void setTypes(List<PokemonType> types) {
        this.types = types;
    }
}
