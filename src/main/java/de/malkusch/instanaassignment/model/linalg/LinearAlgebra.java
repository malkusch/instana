package de.malkusch.instanaassignment.model.linalg;

public interface LinearAlgebra {

    Matrix multiply(Matrix a, Matrix b);

    default Matrix pow(Matrix matrix, int exponent) {
        if (matrix.dimensions().columns() != matrix.dimensions().rows()) {
            throw new IllegalArgumentException("Matrix size must have identical rows and columns");
        }
        if (exponent == 1) {
            return matrix;
        }
        var result = matrix;
        for (int i = 2; i <= exponent; i++) {
            result = multiply(matrix, result);
        }
        return result;
    }

    Matrix matrix(Dimensions dimensions);

}
