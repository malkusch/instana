package de.malkusch.instanaassignment.model;

public record Service(String name) {

    public Service(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Must not be blank");
        }
        this.name = name;
    }

    public Service(char name) {
        this(String.valueOf(name));
    }

}
