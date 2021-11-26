package de.malkusch.instanaassignment.infrastructure.linalg;

import org.ejml.simple.SimpleMatrix;

import de.malkusch.instanaassignment.model.linalg.LinearAlgebra;
import de.malkusch.instanaassignment.model.linalg.Matrix;

public final class EjmlLinearAlgebra implements LinearAlgebra {

    @Override
    public Matrix multiply(Matrix a, Matrix b) {
        var ejmlA = (EjmlMatrix) a;
        var ejmlB = (EjmlMatrix) b;
        var result = ejmlA.matrix().mult(ejmlB.matrix());

        return new EjmlMatrix(result);
    }

    @Override
    public Matrix matrix(int rows, int cols) {
        return new EjmlMatrix(new SimpleMatrix(rows, cols));
    }

}
