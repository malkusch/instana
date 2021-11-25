package de.malkusch.instanaassignment.infrastructure.linalg;

import org.jblas.DoubleMatrix;

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
        if (exponent == 1) {
            return matrix;
        }
        var result = matrix;
        for (int i = 2; i <= exponent; i++) {
            result = multiply(result, matrix);
        }
        return result;
    }

    @Override
    public Matrix matrix(int rows, int cols) {
        return new JblasMatrix(new DoubleMatrix(rows, cols));
    }

}
