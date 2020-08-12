package edu.ahs.robotics.pathfinder.util;

import java.text.DecimalFormat;

public class Util {
    private static final DecimalFormat FORMAT = new DecimalFormat("#.##");

    /**
     * Format a radian heading as a string converted to degrees with appropriate formatting.
     */
    public static String formatHeading(double heading){
        double degrees = Math.toDegrees(heading);
        return formatDouble(degrees) + "\u00b0";
    }

    public static String formatDouble(double d){
        return FORMAT.format(d);
    }

    private Util() {}
}
