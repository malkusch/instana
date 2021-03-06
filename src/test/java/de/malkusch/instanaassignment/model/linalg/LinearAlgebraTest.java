package de.malkusch.instanaassignment.model.linalg;

import static de.malkusch.instanaassignment.model.TestFixture.LINEAR_ALGEBRA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LinearAlgebraTest {

    private final LinearAlgebra linearAlgebra = LINEAR_ALGEBRA;

    @Test
    public void shouldBuildMatrix() {
        var matrix = linearAlgebra.matrix(new Dimensions(3, 2));

        matrix.set(1, 1, 11);
        matrix.set(1, 2, 12);
        matrix.set(2, 1, 21);
        matrix.set(2, 2, 22);
        matrix.set(3, 1, 31);
        matrix.set(3, 2, 32);

        assertEquals(11, matrix.element(1, 1));
        assertEquals(12, matrix.element(1, 2));
        assertEquals(21, matrix.element(2, 1));
        assertEquals(22, matrix.element(2, 2));
        assertEquals(31, matrix.element(3, 1));
        assertEquals(32, matrix.element(3, 2));
    }

    @Test
    public void shouldFailBuildMatrixOutOfDimensions() {
        var matrix = linearAlgebra.matrix(new Dimensions(2, 2));

        assertThrows(IllegalArgumentException.class, () -> matrix.set(3, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> matrix.set(1, 3, 1));
    }

    @Test
    public void testPow1() {
        var matrix = linearAlgebra.matrix(new Dimensions(2, 2));
        matrix.set(1, 1, 1);
        matrix.set(1, 2, 2);
        matrix.set(2, 1, 3);
        matrix.set(2, 2, 4);

        var result = linearAlgebra.pow(matrix, 1);

        assertEquals(matrix, result);
    }

    @Test
    public void testPow2() {
        var matrix = linearAlgebra.matrix(new Dimensions(2, 2));
        matrix.set(1, 1, 1);
        matrix.set(1, 2, 2);
        matrix.set(2, 1, 3);
        matrix.set(2, 2, 4);

        var result = linearAlgebra.pow(matrix, 2);

        var expected = linearAlgebra.matrix(new Dimensions(2, 2));
        expected.set(1, 1, 7);
        expected.set(1, 2, 10);
        expected.set(2, 1, 15);
        expected.set(2, 2, 22);
        assertEquals(expected, result);
    }

    @Test
    public void powShouldFailWhenMatrixIsSquare() {
        var matrix = linearAlgebra.matrix(new Dimensions(3, 2));

        assertThrows(IllegalArgumentException.class, () -> linearAlgebra.pow(matrix, 2));
    }
}
