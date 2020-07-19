package edu.ahs.robotics.pathfinder.path.spline

import edu.ahs.robotics.pathfinder.path.geometry.Pose2d
import edu.ahs.robotics.pathfinder.path.geometry.Rotation2d
import edu.ahs.robotics.pathfinder.path.geometry.Translation2d

class QuinticHermittianSplineTest {
    object Test {
        @JvmStatic
        fun main(args: Array<String>) {
            val waypoints = ArrayList<Pose2d>()
            waypoints.add(Pose2d(Translation2d(0.0, 0.0), Rotation2d.fromDegrees(0.0)))
            waypoints.add(Pose2d(Translation2d(30.0, 30.0), Rotation2d.fromDegrees(90.0)))

            val path = SplinePath.composite(waypoints)

            printPath(path)
        }

        private fun printPath(path: GeometricPath) {
            val labels = listOf(
                    "s",
                    "pos_x",
                    "pos_y",
                    "heading",
                    "direction"
            )
            System.out.println(labels.joinToString(","))


            (0..(path.length).toInt()).forEach {
                val point = path.getPoint(it.toDouble())
                val direction = point.direction
                val values = listOf(
                        point.pose.translation.x(),
                        point.pose.translation.y(),
                        point.pose.rotation.getDegrees()
                )

                System.out.println(values.map { it.toString() }.joinToString(", "))
            }
        }
    }
}