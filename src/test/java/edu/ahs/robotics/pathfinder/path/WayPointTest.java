package edu.ahs.robotics.pathfinder.path;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WayPointTest {
    @Before
    public void setUp() throws Exception {
        Coordinate.initialize(144);
    }

    @Test
    public void testNullScoot() {
        WayPoint w = new WayPoint(Coordinate.newFromInches(0,0));
        WayPoint w2 = new WayPoint(Coordinate.newFromInches(0,0));

        assertEquals(w, w2);
    }

    @Test
    public void testScoot() {
        WayPoint w = new WayPoint(Coordinate.newFromInches(1,1));
        WayPoint w2 = new WayPoint(Coordinate.newFromInches(6,-9));

        w.scoot(5,-10);

        assertEquals(w, w2);
    }
}