package edu.ahs.robotics.pathfinder.path.spline

interface GeometricPath {
    val length: Double
    fun getPoint(dist: Double): PathPoint

    val end get() = this.getPoint(length)

    fun add(path: GeometricPath): GeometricPath = CompositePath().apply { addPath(path) }
}