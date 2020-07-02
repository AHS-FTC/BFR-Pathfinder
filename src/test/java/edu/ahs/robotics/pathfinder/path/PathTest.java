package edu.ahs.robotics.pathfinder.path;

import org.junit.Test;

import static org.junit.Assert.*;

public class PathTest {

    @Test
    public void testCalculateBestAngleSimple() {
        double rawHeading = 2*Math.PI + .1;
        double oldHeading = 0;

        double bestHeading = Path.calculateBestAngle(rawHeading, oldHeading);

        assertEquals(bestHeading, .1,0.0001);
    }

    @Test
    public void testCalculateBestAngle2(){
        double rawHeading = 3 * Math.PI;
        double oldHeading = Math.PI / 2.0;

        double bestHeading = Path.calculateBestAngle(rawHeading, oldHeading);

        assertEquals(bestHeading, Math.PI,0.0001);
    }

    @Test
    public void testCalculateBestAngle3(){
        double rawHeading = 3 * Math.PI;
        double oldHeading = (5 * Math.PI) / 2.0;

        double bestHeading = Path.calculateBestAngle(rawHeading, oldHeading);

        assertEquals(bestHeading, 3 * Math.PI,0.0001);
    }

    @Test
    public void testCalculateBestAngle4(){
        double rawHeading = Math.PI - .2;
        double oldHeading = (-Math.PI) + .2;

        double bestHeading = Path.calculateBestAngle(rawHeading, oldHeading);

        assertEquals((-Math.PI) - .2, bestHeading,0.0001);
    }
}