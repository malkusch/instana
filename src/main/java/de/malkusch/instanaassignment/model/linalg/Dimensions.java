package de.malkusch.instanaassignment.model.linalg;

public record Dimensions(int rows, int columns) {

    public Dimensions(int rows, int columns) {
        if (rows <= 0) {
            throw new IllegalArgumentException("rows must be positive");
        }
        this.rows = rows;

        if (columns <= 0) {
            throw new IllegalArgumentException("columns must be positive");
        }
        this.columns = columns;
    }

}
