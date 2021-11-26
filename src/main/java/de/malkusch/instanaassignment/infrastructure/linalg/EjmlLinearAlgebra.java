package de.malkusch.instanaassignment.infrastructure.linalg;

import org.ejml.simple.SimpleMatrix;

import de.malkusch.instanaassignment.model.linalg.Dimensions;
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
    public Matrix matrix(Dimensions dimensions) {
        return new EjmlMatrix(new SimpleMatrix(dimensions.rows(), dimensions.columns()));
    }

}
