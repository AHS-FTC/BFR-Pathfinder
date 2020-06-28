package edu.ahs.robotics.pathfinder.path;

import javafx.collections.ObservableList;
import javafx.scene.shape.Polygon;

/**
 * A graphical triangle that
 */
public class HeadingPointer extends Polygon {

    private double heading;
    private Circle boundingCircle;
    private ObservableList<Double> points = getPoints();


    public HeadingPointer(double radius, Coordinate coordinate, double heading) {
        super();
        this.heading = heading;

        boundingCircle = new Circle(coordinate.getPixelX(), coordinate.getInchX(), radius);
        Circle c;
    }

    private void calculateVertices(){
        points.clear();

        Coordinate[] vertices = new Coordinate[3];

        vertices[0] = getPointerVertex();




    }

    /**
     * Gets the vertex that acts as the 'tip' of the triangle, pointing in the direction of the heading
     */
    private Coordinate getPointerVertex(){

        //just basic trig
        double dx = boundingCircle.radius * Math.cos(heading);
        double dy = boundingCircle.radius * Math.sin(heading);

        //add relative to center
        double x = boundingCircle.x + dx;
        double y = boundingCircle.y + dy;

        return Coordinate.newFromPixels(x,y);
    }


    /*protected for testing*/ static class Circle{
        /*protected*/ double x, y, radius;

        /*protected*/ Circle(double x, double y, double radius) {
            this.x = x;
            this.y = y;
            this.radius = radius;
        }

        /**
         * Uses the formula for circle intersection here:
         * http://paulbourke.net/geometry/circlesphere/
         *
         * Based on the C example:
         * http://paulbourke.net/geometry/circlesphere/tvoght.c
         */
        /*protected*/ Coordinate[] findIntersection(Circle other){

            //determine the straight line distance between the centers
            double dx = other.x - x;
            double dy = other.y - y;

            double d = Math.hypot(dx, dy);

            //in a more thorough implementation, one might check for solvability here. we don't care.

            //a is the distance from the center of this circle to the 'midpoint' between the two points of intersection
            double a = ((radius*radius) - (other.radius*other.radius) + (d*d)/(2*d));

            //the 'midpoint' x and y
            double mpX = x + (dx * (a/d));
            double mpY = y + (dy * (a/d));

            //h is the distance from the center of the other circle to the 'midpoint' between the two points of intersection
            double h = Math.sqrt((radius*radius) - (a*a));

            //determine the offsets of the intersection points from the previous 'midpoint'
            double rx = -dy * (h/d);
            double ry = dx * (h/d);

            //get final x and y values
            double x1 = mpX + rx;
            double x2 = mpX - rx;

            double y1 = mpY + ry;
            double y2 = mpY - ry;

            Coordinate c1 = Coordinate.newFromPixels(x1, y1);
            Coordinate c2 = Coordinate.newFromPixels(x2, y2);

            return new Coordinate[] {c1, c2};
        }
    }
}
