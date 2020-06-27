package edu.ahs.robotics.pathfinder;

import edu.ahs.robotics.pathfinder.util.Coordinate;
import org.junit.Assert;
import org.junit.Test;

public class CoordinateTest {

    @Test
    public void testInchConversionFromTopLeft(){
        Coordinate.initialize(144);

        //the top left corner should always be (-72, 72) in inches
        Coordinate c = Coordinate.newFromPixels(0,0);

        Assert.assertEquals(c.getInchX(), -72.0, 0.00001);
        Assert.assertEquals(c.getInchY(), 72.0, 0.00001);

    }

    @Test
    public void testInchConversionFromBottomRight(){
        Coordinate.initialize(144);//1 pixel = 1 inch

        Coordinate c = Coordinate.newFromPixels(144,144);

        Assert.assertEquals(c.getInchX(), 72, 0.00001);
        Assert.assertEquals(c.getInchY(), -72, 0.00001);

    }

    @Test
    public void testInchConversionPointOnField(){
        Coordinate.initialize(144);//1 pixel = 1 inch

        Coordinate c = Coordinate.newFromPixels(75,80);

        Assert.assertEquals(c.getInchX(), 3, 0.00001);
        Assert.assertEquals(c.getInchY(), -8, 0.00001);

    }

    @Test
    public void testPixelConversionFromTopLeft(){
        Coordinate.initialize(144);

        //the top left corner should always be (-72, 72) in inches
        Coordinate c = Coordinate.newFromInches(-72,72);

        Assert.assertEquals(c.getPixelX(), 0, 0.00001);
        Assert.assertEquals(c.getPixelY(), 0, 0.00001);

    }

    @Test
    public void testPixelConversionFromBottomRight(){
        Coordinate.initialize(144);//1 pixel = 1 inch

        Coordinate c = Coordinate.newFromInches(72,-72);

        Assert.assertEquals(c.getPixelY(), 144, 0.00001);
        Assert.assertEquals(c.getPixelY(), 144, 0.00001);

    }

    @Test
    public void testPixelConversionPointOnField(){
        Coordinate.initialize(144);//1 pixel = 1 inch

        Coordinate c = Coordinate.newFromInches(3,-8);

        Assert.assertEquals(c.getPixelX(), 75, 0.00001);
        Assert.assertEquals(c.getPixelY(), 80, 0.00001);

    }
}