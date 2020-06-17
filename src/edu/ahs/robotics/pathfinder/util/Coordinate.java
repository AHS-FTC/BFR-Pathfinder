package edu.ahs.robotics.pathfinder.util;

/**
 * A class representing a coordinate on the field. Seamlessly handles the conversions between real life inches and graphical pixels.
 * Also holds heading data.
 * @author Alex Appleby, team 16896
 */
public class Coordinate { //todo consider removing heading and creating an encapsulating Position class
    private Double pixelX, pixelY;
    private Double inchX, inchY;
    private double heading; // in radians

    private static Coordinate centerCoordinate;
    private static boolean initialized = false;
    private static Double scaleFactor; //the ratio of pixels to inches. Nonprimitive to guarantee initialization

    /**
     * Inaccessible from outside of class. Use Point.newFromInches() or Point.newFromPixels() instead.
     * @param heading Heading in radians, where 0 degrees is along the positive X axis (right) and counterclockwise increases.
     */
    private Coordinate(double heading){
        this.heading = heading;
    }

    /**
     * Acts as a constructor. Creates a point given inch coordinates. Origin is in the center of the field.
     * @param xInches Inches from the center of the field along the X axis, where right is increasing.
     * @param yInches Inches from the center of the field along the Y axis, where up is increasing.
     * @param heading Heading in radians, where 0 degrees is along the positive X axis (right) and counterclockwise increases.
     * @return A new point populated with the given information.
     */
    public static Coordinate newFromInches(double xInches, double yInches, double heading){
        Coordinate p = new Coordinate(heading);
        p.inchX = xInches;
        p.inchY = yInches;
        return p;
    }

    /**
     * Acts as a constructor. Creates a point given pixel coordinates. Origin is the JavaFX default top left corner of the window.
     * @param xPixels Pixels from the top right corner of the field along the X axis, where right is increasing.
     * @param yPixels Pixels from the top right corner of the field along the Y axis, where down is increasing.
     * @param heading Heading in degrees, where 0 degrees is along the positive X axis (right) and counterclockwise increases.
     * @return A new point populated with the given information.
     */
    public static Coordinate newFromPixels(double xPixels, double yPixels, double heading){
        Coordinate p = new Coordinate(heading);
        p.pixelX = xPixels;
        p.pixelY = yPixels;
        return p;
    }

    public double getInchX(){
        if(inchX == null)
            calculateInches();
        return inchX;
    }

    public double getInchY(){
        if(inchY == null)
            calculateInches();
        return inchY;
    }

    public double getPixelX(){
        if(pixelX == null)
            calculatePixels();
        return pixelX;
    }

    public double getPixelY(){
        if(pixelY == null)
            calculatePixels();
        return pixelY;
    }

    public double getHeading(){
        return heading;
    }

    public void setHeading(double heading){
        this.heading = heading;
    }

    /**
     * Finds inch coordinates given pixel coordinates
     */
    private void calculateInches(){
        //find how far in inches this coordinate is from the pixel origin on the x axis
        double xInchesFromPixelOrigin = convertPixelsToInches(pixelX);

        //find how far in inches this coordinate is from the pixel origin on the y axis.
        double yInchesFromPixelOrigin = -convertPixelsToInches(pixelY); //negative accounts for inches y axis and pixel y axis being in different directions.

        //We can calculate an 'offset' on either axis to move a coordinate from one origin to another.
        //the x or y distance from the pixel origin to the inches origin is equal to the pixel location of the inches origin (center point of field)
        double xOriginOffset = convertPixelsToInches(centerCoordinate.pixelX);
        double yOriginOffset = -convertPixelsToInches(centerCoordinate.pixelY); //negative for same reason as above

        //subtracting these offsets yields inch coordinates
        inchX = xInchesFromPixelOrigin - xOriginOffset;
        inchY = yInchesFromPixelOrigin - yOriginOffset;
    }

    /**
     * Finds pixel coordinates given inch coordinates
     */
    private void calculatePixels(){
        //find how far in pixels this coordinate is from the inch origin on the x axis
        double xPixelsFromInchOrigin = convertInchesToPixels(inchX);

        //find how far in pixels this coordinate is from the inch origin on the y axis
        double yPixelsFromInchOrigin = -convertInchesToPixels(inchY); //negative accounts for inches y axis and pixel y axis being in different directions.

        //We can calculate an 'offset' on either axis to move a coordinate from one origin to another.
        //the x or y distance from the inch origin to the pixel origin is equal to the pixel location of the inches origin (center point of field)
        double xOriginOffset = centerCoordinate.pixelX;
        double yOriginOffset = centerCoordinate.pixelY;

        //subtracting these offsets yields inch coordinates
        pixelX = xPixelsFromInchOrigin + xOriginOffset;
        pixelY = yPixelsFromInchOrigin + yOriginOffset;
    }

    /**
     * Finds the angle from this coordinate to another.
     * @return The angle in radians (standard conventions).
     */
    public double angleTo(Coordinate coord){

        //use inches here to avoid axis ambiguity and such other bugs
        double dx = coord.getInchX() - getInchX();
        double dy = coord.getInchY() - getInchY();

        return Math.atan2(dy,dx);
    }


    /**
     * Initializes the coordinate system with knowledge of field size.
     * @param fieldSize the size of one side of the (square) field in pixels.
     */
    public static void initialize(double fieldSize){
        if(!initialized){
            initialized = true;
            scaleFactor = fieldSize / 144.0; //the FTC field is 144 inches across
            centerCoordinate = newFromPixels(fieldSize / 2.0,fieldSize / 2.0, 0);
        }
    }

    /**
     * Converts a scalar distance in inches to pixels using the Scale Factor. Not for a tensor coordinate point.
     */
    public static double convertInchesToPixels(double inches){
        return inches * scaleFactor;
    }

    /**
     * Converts a scalar distance in pixels to inches using the Scale Factor. Not for a tensor coordinate point.
     */
    public static double convertPixelsToInches(double pixels){
        return pixels * (1.0 / scaleFactor);
    }

}
