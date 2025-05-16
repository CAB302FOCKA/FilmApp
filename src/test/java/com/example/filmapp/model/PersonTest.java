package com.example.filmapp.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    @Test
    void testPersonConstructorAndGetters() {
        Person person = new Person("FirstName LastName", "Protagonist", "/person.jpg");

        assertEquals("FirstName LastName", person.getName());
        assertEquals("Protagonist", person.getCharacter());
        assertEquals("/person.jpg", person.getProfilePath());
    }
}