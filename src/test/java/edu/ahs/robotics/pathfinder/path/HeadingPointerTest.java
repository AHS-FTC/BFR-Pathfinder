package edu.ahs.robotics.pathfinder.path;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.Test;

import static org.junit.Assert.*;

public class HeadingPointerTest {

    @Test
    public void testCircleIntersectionSimple() {//todo add another, more complex test for this

        //scenario: https://www.desmos.com/calculator/i1zuf1jl83

        HeadingPointer.MathCircle c1 = new HeadingPointer.MathCircle(0,0,2);
        HeadingPointer.MathCircle c2 = new HeadingPointer.MathCircle(2,0,2);

        Coordinate int1 = Coordinate.newFromPixels(1, -1.732);
        Coordinate int2 = Coordinate.newFromPixels(1, 1.732);

        Coordinate[] actuals = c1.findIntersection(c2);

        Coordinate actual1 = actuals[0];
        Coordinate actual2 = actuals[1];

        assertEquals(int1.getPixelX(), actual2.getPixelX(), 0.01);
        assertEquals(int2.getPixelX(), actual1.getPixelX(), 0.01);

        assertEquals(int1.getPixelY(), actual2.getPixelY(), 0.01);
        assertEquals(int2.getPixelY(), actual1.getPixelY(), 0.01);
    }

    @Test
    public void testCircleIntersectionComplex() {

        //scenario: https://www.desmos.com/calculator/rkktvzcdy8

        HeadingPointer.MathCircle c1 = new HeadingPointer.MathCircle(-2.6,3.1,2.5);
        HeadingPointer.MathCircle c2 = new HeadingPointer.MathCircle(.4,4,1);

        Coordinate int1 = Coordinate.newFromPixels(-.494, 4.448);
        Coordinate int2 = Coordinate.newFromPixels(-.1, 3.134);

        Coordinate[] actuals = c1.findIntersection(c2);

        Coordinate actual1 = actuals[0];
        Coordinate actual2 = actuals[1];

        assertEquals(int1.getPixelX(), actual1.getPixelX(), 0.01);
        assertEquals(int2.getPixelX(), actual2.getPixelX(), 0.01);

        assertEquals(int1.getPixelY(), actual1.getPixelY(), 0.01);
        assertEquals(int2.getPixelY(), actual2.getPixelY(), 0.01);
    }

    @Test
    public void testGetPointerVertexSimple() {
        HeadingPointer headingPointer = new HeadingPointer(1, Coordinate.newFromPixels(0,0), Math.PI);

        Coordinate pointerVertex = headingPointer.getPointerVertex();

        assertEquals(pointerVertex.getPixelX(), -1.0, 0.00001);
        assertEquals(pointerVertex.getPixelY(), 0.0, 0.00001);
    }

    @Test
    public void testGetPointerVertex() {
        HeadingPointer headingPointer = new HeadingPointer(4, Coordinate.newFromPixels(1,1), Math.PI/2);

        Coordinate pointerVertex = headingPointer.getPointerVertex();

        assertEquals(pointerVertex.getPixelX(), 1, 0.00001);
        assertEquals(pointerVertex.getPixelY(), 5, 0.00001);
    }

    @Test
    public void testGetPointerVertex2() {
        HeadingPointer headingPointer = new HeadingPointer(Math.sqrt(2), Coordinate.newFromPixels(1,1), Math.PI/4.0);

        Coordinate pointerVertex = headingPointer.getPointerVertex();

        assertEquals(pointerVertex.getPixelX(), 2, 0.00001);
        assertEquals(pointerVertex.getPixelY(), 2, 0.00001);
    }

    public static class ManualHeadingPointerTest extends Application{
        @Override
        public void start(Stage stage) throws Exception {
            Coordinate c = Coordinate.newFromPixels(100,100);
            HeadingPointer h = new HeadingPointer(30, c, 3*Math.PI/4);
            h.setFill(Color.rgb(220,220,0));

            Group g = new Group();
            g.getChildren().add(h.getGraphics());

            Scene scene = new Scene(g, 500,500);
            stage.setScene(scene);
            stage.show();
        }
    }
}