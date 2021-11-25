package de.malkusch.instanaassignment.model.linalg;

/**
 * A classic matrix as we know from linear algebra. The first element in the
 * first row and first column starts with the index (1,1).
 */
public interface Matrix {

    int element(int i, int j);

    void set(int i, int j, int value);
}
