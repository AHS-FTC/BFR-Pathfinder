package edu.ahs.robotics.pathfinder.path;

import org.junit.Test;

import static org.junit.Assert.*;

public class HeadingPointerTest {

    @Test
    public void testCircleIntersectionSimple() {//todo add another, more complex test for this

        //scenario: https://www.desmos.com/calculator/i1zuf1jl83

        HeadingPointer.Circle c1 = new HeadingPointer.Circle(0,0,2);
        HeadingPointer.Circle c2 = new HeadingPointer.Circle(2,0,2);

        Coordinate int1 = Coordinate.newFromPixels(1, -1.732);
        Coordinate int2 = Coordinate.newFromPixels(1, 1.732);

        Coordinate[] actuals = c1.findIntersection(c2);

        Coordinate actual1 = actuals[0];
        Coordinate actual2 = actuals[1];

        assertEquals(int1.getPixelX(), actual1.getPixelX(), 0.01);
        assertEquals(int2.getPixelX(), actual2.getPixelX(), 0.01);

        assertEquals(int1.getPixelY(), actual2.getPixelY(), 0.01);
        assertEquals(int2.getPixelY(), actual1.getPixelY(), 0.01);
    }

    //todo test getPointerVertex()
}