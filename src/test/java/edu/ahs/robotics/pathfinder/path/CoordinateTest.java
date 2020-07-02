package edu.ahs.robotics.pathfinder.path;

import edu.ahs.robotics.pathfinder.path.Coordinate;
import org.junit.Test;
import static org.junit.Assert.*;

public class CoordinateTest {

    @Test
    public void testInchConversionFromTopLeft(){
        Coordinate.initialize(144);

        //the top left corner should always be (-72, 72) in inches
        Coordinate c = Coordinate.newFromPixels(0,0);

        assertEquals(c.getInchX(), -72.0, 0.00001);
        assertEquals(c.getInchY(), 72.0, 0.00001);

    }

    @Test
    public void testInchConversionFromBottomRight(){
        Coordinate.initialize(144);//1 pixel = 1 inch

        Coordinate c = Coordinate.newFromPixels(144,144);

        assertEquals(c.getInchX(), 72, 0.00001);
        assertEquals(c.getInchY(), -72, 0.00001);

    }

    @Test
    public void testInchConversionPointOnField(){
        Coordinate.initialize(144);//1 pixel = 1 inch

        Coordinate c = Coordinate.newFromPixels(75,80);

        assertEquals(c.getInchX(), 3, 0.00001);
        assertEquals(c.getInchY(), -8, 0.00001);

    }

    @Test
    public void testPixelConversionFromTopLeft(){
        Coordinate.initialize(144);

        //the top left corner should always be (-72, 72) in inches
        Coordinate c = Coordinate.newFromInches(-72,72);

        assertEquals(c.getPixelX(), 0, 0.00001);
        assertEquals(c.getPixelY(), 0, 0.00001);

    }

    @Test
    public void testPixelConversionFromBottomRight(){
        Coordinate.initialize(144);//1 pixel = 1 inch

        Coordinate c = Coordinate.newFromInches(72,-72);

        assertEquals(c.getPixelY(), 144, 0.00001);
        assertEquals(c.getPixelY(), 144, 0.00001);

    }

    @Test
    public void testPixelConversionPointOnField(){
        Coordinate.initialize(144);//1 pixel = 1 inch

        Coordinate c = Coordinate.newFromInches(3,-8);

        assertEquals(c.getPixelX(), 75, 0.00001);
        assertEquals(c.getPixelY(), 80, 0.00001);

    }

    @Test
    public void testModululs(){
        System.out.println(6 % (Math.PI));
    }
}