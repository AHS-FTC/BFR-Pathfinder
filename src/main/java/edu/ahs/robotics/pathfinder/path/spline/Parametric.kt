package edu.ahs.robotics.pathfinder.path.spline

import edu.ahs.robotics.pathfinder.path.geometry.Pose2d

interface Parametric {
    fun getPose(s: Double): Pose2d
    fun getDirection(s: Double): Pose2d
    fun getPerpendicular(s: Double): Pose2d
    fun getCurvature(s: Double): Double
}