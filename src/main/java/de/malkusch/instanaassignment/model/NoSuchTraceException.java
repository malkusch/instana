package de.malkusch.instanaassignment.model;

public class NoSuchTraceException extends RuntimeException {
    private static final long serialVersionUID = -6032786145895849095L;

    public NoSuchTraceException(String message) {
        super(message);
    }

}
