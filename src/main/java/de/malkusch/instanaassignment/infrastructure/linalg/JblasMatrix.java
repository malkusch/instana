package de.malkusch.instanaassignment.infrastructure.linalg;

import org.jblas.DoubleMatrix;

import de.malkusch.instanaassignment.model.linalg.Matrix;

final record JblasMatrix(DoubleMatrix matrix) implements Matrix {

    @Override
    public int element(int i, int j) {
        // Potential floating point issues
        return (int) matrix.get(i - 1, j - 1);
    }

    @Override
    public void set(int i, int j, int value) {
        // Potential floating point issues
        matrix.put(i - 1, j - 1, value);
    }

    @Override
    public String toString() {
        var buffer = "";
        for (int i = 1; i <= matrix.rows; i++) {
            for (int j = 1; j <= matrix.columns; j++) {
                buffer += element(i, j) + "\t";
            }
            buffer += "\n";
        }
        return buffer;
    }
}
