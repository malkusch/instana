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
}
