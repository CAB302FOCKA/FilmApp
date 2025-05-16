package com.example.filmapp.model;

public class Person {
    String name;
    String character;
    String profilePath;

    public Person(String name, String character, String profilePath) {
        this.name = name;
        this.character = character;
        this.profilePath = profilePath;
    }

    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
    }

    public String getProfilePath() {
        return profilePath;
    }
}
