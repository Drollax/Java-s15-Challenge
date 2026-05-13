package com.library.user;

public abstract class Person {
    private String name;

    public Person(String name){
        this.name = name;
    }

    public abstract void whoYouAre();


    public String getName() {
        return name;
    }
}
