package de.malkusch.instanaassignment.infrastructure.linalg;

import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;

import de.malkusch.instanaassignment.model.linalg.LinearAlgebra;
import de.malkusch.instanaassignment.model.linalg.Matrix;

public final class JblasLinearAlgebra implements LinearAlgebra {

    @Override
    public Matrix multiply(Matrix a, Matrix b) {
        var jblasA = (JblasMatrix) a;
        var jblasB = (JblasMatrix) b;
        var result = jblasA.matrix().mmul(jblasB.matrix());
        return new JblasMatrix(result);
    }

    @Override
    public Matrix pow(Matrix matrix, int exponent) {
        var jblasMatrix = (JblasMatrix) matrix;
        var result = MatrixFunctions.pow(jblasMatrix.matrix(), exponent);
        return new JblasMatrix(result);
    }

    @Override
    public Matrix matrix(int rows, int cols) {
        return new JblasMatrix(new DoubleMatrix(rows, cols));
    }

}
