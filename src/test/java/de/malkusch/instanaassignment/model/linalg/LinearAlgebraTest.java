package de.malkusch.instanaassignment.model.linalg;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.malkusch.instanaassignment.model.TestFixture;

public class LinearAlgebraTest {

    private final LinearAlgebra linearAlgebra = TestFixture.LINEAR_ALGEBRA;

    @Test
    public void shouldBuildMatrixWithOffset() {
        var matrix = linearAlgebra.matrix(3, 3);

        matrix.set(1, 1, 11);
        matrix.set(1, 2, 12);
        matrix.set(1, 3, 13);
        matrix.set(2, 1, 21);
        matrix.set(2, 2, 22);
        matrix.set(2, 3, 23);
        matrix.set(3, 1, 31);
        matrix.set(3, 2, 32);
        matrix.set(3, 3, 33);

        assertEquals(11, matrix.element(1, 1));
        assertEquals(12, matrix.element(1, 2));
        assertEquals(13, matrix.element(1, 3));
        assertEquals(21, matrix.element(2, 1));
        assertEquals(22, matrix.element(2, 2));
        assertEquals(23, matrix.element(2, 3));
        assertEquals(31, matrix.element(3, 1));
        assertEquals(32, matrix.element(3, 2));
        assertEquals(33, matrix.element(3, 3));
    }

}
