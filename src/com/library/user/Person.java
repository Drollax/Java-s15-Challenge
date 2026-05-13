package com.library.user;

import java.util.Objects;

public abstract class Person {
    private Long personId;
    private String name;

    public Person(Long personId, String name) {
        this.personId = personId;
        this.name = name;
    }

    public abstract void whoYouAre();


    public String getName() {
        return name;
    }

    public Long getPersonId() {
        return personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(personId, person.personId); // İsime göre kontrol
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId);
    }
}
