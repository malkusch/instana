package de.malkusch.instanaassignment.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class CountTracesWithinLatencyServiceTest {

    private final CountTracesWithinLatencyService countService = new CountTracesWithinLatencyService();

    @Test
    public void testCount_10() {
        var start = new Service("C");
        var end = new Service("C");
        var max = new Latency(Duration.ofMillis(30));

        var count = countService.count(TestFixture.GRAPH, max, start, end);

        assertEquals(7, count);
    }

}
