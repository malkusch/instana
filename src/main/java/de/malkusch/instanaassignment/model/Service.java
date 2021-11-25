package de.malkusch.instanaassignment.model;

public record Service(String name) {

    public Service(char name) {
        this(String.valueOf(name));
    }

}
