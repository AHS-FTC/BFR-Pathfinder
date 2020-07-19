package edu.ahs.robotics.pathfinder.path.spline

class CompositePath : GeometricPath {
    private val paths = ArrayList<Pair<Double, GeometricPath>>()

    override var length: Double = 0.0
    fun addPath(path: GeometricPath) {
        this.paths.add(Pair(length, path))
        this.length += path.length
    }

    // anchor path to end
    fun addRelativePath(path: GeometricPath) { // TODO: IMPLEMENT
    }

    override fun getPoint(s: Double): PathPoint {
        if (paths.size == 0) {
            throw IndexOutOfBoundsException()
        }

        var l = 0
        var r = paths.size
        while (l + 1 != r) {
            val m = (l + r) / 2
            if (paths[m].first <= s) {
                l = m
            } else {
                r = m
            }
        }

        val (pathStart, path) = paths[l]

        return path.getPoint(s - pathStart).copy(
                distance = s
        )
    }
}