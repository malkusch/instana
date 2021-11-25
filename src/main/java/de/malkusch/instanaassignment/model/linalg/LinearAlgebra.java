package de.malkusch.instanaassignment.model.linalg;

public interface LinearAlgebra {

    Matrix multiply(Matrix a, Matrix b);

    default Matrix pow(Matrix matrix, int exponent) {
        if (exponent == 1) {
            return matrix;
        }
        var result = matrix;
        for (int i = 2; i <= exponent; i++) {
            result = multiply(matrix, result);
        }
        return result;
    }

    Matrix matrix(int rows, int cols);

}
