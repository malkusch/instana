package de.malkusch.instanaassignment.model;

import de.malkusch.instanaassignment.infrastructure.linalg.EjmlLinearAlgebra;
import de.malkusch.instanaassignment.model.Graph.Factory;
import de.malkusch.instanaassignment.model.linalg.LinearAlgebra;

public class TestFixture {

    public static final LinearAlgebra LINEAR_ALGEBRA = new EjmlLinearAlgebra();
    private static final Factory FACTORY = new Factory(LINEAR_ALGEBRA);
    public static final Graph GRAPH = FACTORY.parseCsv("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");

}
