package edu.ahs.robotics.pathfinder.path.spline

import edu.ahs.robotics.pathfinder.path.geometry.Pose2d


class SplinePath(val type: QuinticHermittianSpline.Type, val startPose: Pose2d, val endPose: Pose2d) : GeometricPath {
    val parametric = QuinticHermittianSpline(type, startPose, endPose)
    val parametricPath = ParametricPath(parametric, parametric.length)

    override val length = parametricPath.length

    override fun getPoint(dist: Double) = parametricPath.getPoint(dist)

    companion object {
        fun composite(waypoints: ArrayList<Pose2d>): GeometricPath {
            val compositePath = CompositePath()
            (0..(waypoints.size - 2)).forEach {
                val segment = SplinePath(
                        QuinticHermittianSpline.Type.QUINTIC_HERMITIAN,
                        waypoints[it],
                        waypoints[it + 1]
                )
                if (segment.length > 1e8) throw Exception("why dis path so long")
                compositePath.addPath(segment)
            }
            return compositePath
        }
    }
}
