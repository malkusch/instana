package de.malkusch.instanaassignment.infrastructure.linalg;

import java.util.Arrays;

import org.ejml.simple.SimpleMatrix;

import de.malkusch.instanaassignment.model.linalg.Matrix;

final record EjmlMatrix(SimpleMatrix matrix) implements Matrix {

    @Override
    public int element(int i, int j) {
        // Potential floating point issues
        return (int) matrix.get(i - 1, j - 1);
    }

    @Override
    public void set(int i, int j, int value) {
        // Potential floating point issues
        matrix.set(i - 1, j - 1, value);
    }

    @Override
    public String toString() {
        var buffer = "\n";
        for (int i = 1; i <= matrix.numRows(); i++) {
            for (int j = 1; j <= matrix.numCols(); j++) {
                buffer += element(i, j) + "\t";
            }
            buffer += "\n";
        }
        return buffer;
    }

    // Had to override those as the underlying implementation doesn't give equality,
    // probably because of floats. This is not critical as it's only needed in
    // tests.

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof EjmlMatrix)) {
            return false;
        }
        return Arrays.equals(rollout(), ((EjmlMatrix) other).rollout());
    }

    @Override
    public int hashCode() {
        return rollout().hashCode();
    }

    private int[] rollout() {
        int[] array = new int[matrix.numRows() * matrix.numCols()];
        for (int i = 1; i <= matrix.numRows(); i++) {
            for (int j = 1; j <= matrix.numCols(); j++) {
                array[(i - 1) * matrix.numCols() + (j - 1)] = element(i, j);
            }
        }
        return array;
    }
}
