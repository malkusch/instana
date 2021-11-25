package de.malkusch.instanaassignment.model.linalg;

public interface LinearAlgebra {

    Matrix multiply(Matrix a, Matrix b);

    Matrix pow(Matrix matrix, int exponent);

    Matrix matrix(int rows, int cols);
    
}
