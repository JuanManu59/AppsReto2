package com.example.reto2.modelo;

import java.io.Serializable;

public class User implements Serializable {

    private String Id;
    private String Username;

    public User() {
    }

    public User(String id, String username) {
        Id = id;
        Username = username;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
