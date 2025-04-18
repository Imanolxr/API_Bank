package com.SaintPatrick.Banco.Saint.Patrick.domain.model;

public class User {
    private String name;
    private String lastName;

    public User(String name, String lastName) {
        if (name == null || !name.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("El nombre solo debe contener letras y espacios");
        }
        if (lastName == null || !lastName.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("El apellido solo debe contener letras y espacios");
        }

        this.name = name;
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
