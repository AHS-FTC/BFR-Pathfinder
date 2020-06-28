package edu.ahs.robotics.pathfinder.path;

/**
 * A class representing a coordinate on the field. Seamlessly handles the conversions between real life inches and graphical pixels.
 * @author Alex Appleby, team 16896
 */
public class Coordinate {
    private Double pixelX, pixelY;
    private Double inchX, inchY;

    private static Coordinate centerCoordinate;
    private static boolean initialized = false;
    private static Double scaleFactor; //the ratio of pixels to inches. Nonprimitive to guarantee initialization

    /**
     * Inaccessible from outside of class. Use Point.newFromInches() or Point.newFromPixels() instead.
     */
    private Coordinate(){
    }

    /**
     * Acts as a constructor. Creates a point given inch coordinates. Origin is in the center of the field.
     * @param xInches Inches from the center of the field along the X axis, where right is increasing.
     * @param yInches Inches from the center of the field along the Y axis, where up is increasing.
     * @return A new point populated with the given information.
     */
    public static Coordinate newFromInches(double xInches, double yInches){
        Coordinate p = new Coordinate();
        p.inchX = xInches;
        p.inchY = yInches;
        return p;
    }

    /**
     * Acts as a constructor. Creates a point given pixel coordinates. Origin is the JavaFX default top left corner of the window.
     * @param xPixels Pixels from the top right corner of the field along the X axis, where right is increasing.
     * @param yPixels Pixels from the top right corner of the field along the Y axis, where down is increasing.
     * @return A new point populated with the given information.
     */
    public static Coordinate newFromPixels(double xPixels, double yPixels){
        Coordinate p = new Coordinate();
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

    @Override
    public boolean equals(Object obj) { //todo consider making equals method run unit conversions
        if(obj == this){
            return true;
        }

        if(!(obj instanceof Coordinate)){
            return false;
        }

        Coordinate c = (Coordinate)obj;

        boolean inchesAreEqual = false;
        boolean pixelsAreEqual = false;

        if (inchX != null && inchY != null){ //don't run this check if inches aren't defined
            if(inchX == c.inchX && inchY == c.inchY){
                inchesAreEqual = true;
            }
        }
        if (pixelX != null && pixelY != null){ //don't run this check if inches aren't defined
            if(pixelX == c.pixelX && pixelY == c.pixelY){
                pixelsAreEqual = true;
            }
        }

        if(inchesAreEqual || pixelsAreEqual){
            return true;
        } else return false;

    }

    /**
     * Initializes the coordinate system with knowledge of field size.
     * @param fieldSize the size of one side of the (square) field in pixels.
     */
    public static void initialize(double fieldSize){
        if(!initialized){
            initialized = true;
            scaleFactor = fieldSize / 144.0; //the FTC field is 144 inches across
            centerCoordinate = newFromPixels(fieldSize / 2.0,fieldSize / 2.0);
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
