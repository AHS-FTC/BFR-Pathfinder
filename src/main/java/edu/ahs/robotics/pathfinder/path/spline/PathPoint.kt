package edu.ahs.robotics.pathfinder.path.spline

import edu.ahs.robotics.pathfinder.path.geometry.Pose2d


data class PathPoint(
        val pose: Pose2d,
        val direction: Pose2d, // unit tangent vector and direction of derivative of heading
        val perpendicular: Pose2d, // principle unit normal vector and direction of second derivative of heading
        val curvature: Double, // 1/radius of curvature
        val distance: Double // arc length param
) {
    fun interpolate(other: PathPoint, position: Double) = PathPoint(
            this.pose.interpolate(other.pose, position),
            this.direction.interpolate(other.direction, position),
            this.perpendicular.interpolate(other.perpendicular, position),
            this.curvature + (other.curvature - this.curvature) * position,
            this.distance + (other.distance - this.distance) * position
    )

    fun anchor(pose: Pose2d) = this.copy(
            pose = this.pose.anchor(pose),
            direction = this.direction.rotatePos(pose.rotation.getDegrees()),
            perpendicular = this.perpendicular.rotatePos(pose.rotation.getDegrees())
    )
}
