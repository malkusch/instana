package de.malkusch.instanaassignment.model.linalg;

public interface LinearAlgebra {

    int sum(Vector vector);

    Matrix multiply(Matrix a, Matrix b);
    
    Matrix pow(Matrix matrix, int exponent);

}
