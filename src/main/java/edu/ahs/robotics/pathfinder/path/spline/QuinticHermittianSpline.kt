package edu.ahs.robotics.pathfinder.path.spline

import edu.ahs.robotics.pathfinder.path.geometry.Pose2d
import edu.ahs.robotics.pathfinder.path.geometry.Rotation2d
import edu.ahs.robotics.pathfinder.path.geometry.Translation2d

class QuinticHermittianSpline(type: Type, var startPose: Pose2d, var endPose: Pose2d) : Parametric {
    /**
     * These spline parameters are the same as Pathfinder and TrajectoryLib.
     * q(t) = at^5 + bt^4 + ct^3 + dt^2 + et
     */
    private var a: Double = 0.toDouble()
    private var b: Double = 0.toDouble()
    private var c: Double = 0.toDouble()
    private var d: Double = 0.toDouble()
    private var e: Double = 0.toDouble()

    private val xOffset: Double
    private val yOffset: Double
    private val headingOffset: Double
    private val knotDistance: Double
    var length: Double = 0.toDouble()
    private val headingFlipped: Boolean

    enum class Type {
        CUBIC_HERMITIAN,
        QUINTIC_HERMITIAN
    }

    init {
        startPose = startPose.copy(rotation = Rotation2d.fromDegrees(90.0 - startPose.rotation.getDegrees()))
        endPose = endPose.copy(rotation = Rotation2d.fromDegrees(90.0 - endPose.rotation.getDegrees()))

        xOffset = startPose.translation.x()
        yOffset = startPose.translation.y()

        knotDistance = (endPose.translation - startPose.translation).norm()
        headingOffset = Math.toDegrees(Math.atan2(endPose.translation.y() - startPose.translation.y(),
                endPose.translation.x() - startPose.translation.x()))

        val a0Delta = Math.tan(Math.toRadians(adjustAngle(startPose.rotation.getDegrees() - headingOffset)))
        val a1Delta = Math.tan(Math.toRadians(adjustAngle(endPose.rotation.getDegrees() - headingOffset)))

        if (type == Type.CUBIC_HERMITIAN) {
            a = 0.0
            b = 0.0
            c = (a0Delta + a1Delta) / (knotDistance * knotDistance)
            d = -(2 * a0Delta + a1Delta) / knotDistance
            e = a0Delta
        } else {
            a = -(3 * (a0Delta + a1Delta)) / (knotDistance * knotDistance * knotDistance * knotDistance)
            b = (8 * a0Delta + 7 * a1Delta) / (knotDistance * knotDistance * knotDistance)
            c = -(6 * a0Delta + 4 * a1Delta) / (knotDistance * knotDistance)
            d = 0.0
            e = a0Delta
        }

        computeLength()


        headingFlipped = Math.abs(adjustAngle((90 - startPose.rotation.getDegrees()) -
                start().rotation.getDegrees())) > 90
    }

    private fun valueAt(percentage: Double): Double {
        val x = knotDistance * percentage
        return (a * x + b) * (x * x * x * x) + c * (x * x * x) + d * (x * x) + e * x
    }

    private fun derivativeAt(percentage: Double): Double {
        val x = knotDistance * percentage
        return (5.0 * a * x + 4 * b) * (x * x * x) + (3.0 * c * x + 2 * d) * x + e
    }

    private fun secondDerivativeAt(percentage: Double): Double {
        val x = knotDistance * percentage
        return (20.0 * a * x + 12 * b) * (x * x) + 6.0 * c * x + 2 * d
    }

    private fun thirdDerivativeAt(percentage: Double): Double {
        val x = knotDistance * percentage
        return (60.0 * a * x + 24 * b) * x + 6 * c
    }

    private fun computeLength() {
        length = 0.0
        var lastIntegrand = Math.sqrt(1 + derivativeAt(0.0) * derivativeAt(0.0)) / ARC_LENGTH_SAMPLES
        for (i in 1..ARC_LENGTH_SAMPLES) {
            val percentage = i.toDouble() / ARC_LENGTH_SAMPLES
            val dydx = derivativeAt(percentage)
            val integrand = Math.sqrt(1 + dydx * dydx) / ARC_LENGTH_SAMPLES
            length += (integrand + lastIntegrand) / 2
            lastIntegrand = integrand
        }
        length *= knotDistance
    }

    fun length(): Double {
        return length
    }

    fun start(): Pose2d {
        return getPose(0.0)
    }

    fun end(): Pose2d {
        return getPose(length)
    }

    override fun getPose(displacement: Double): Pose2d {
        val percentage = displacement / length

        val derivative = derivativeAt(percentage)

        val x = knotDistance * percentage
        val y = valueAt(percentage)
        var heading = Math.toDegrees(Math.atan(derivative)) + headingOffset
        if (headingFlipped) {
            heading += 180
        }
        heading = adjustAngle(heading)

        return Pose2d(
                Translation2d(
                        x * Math.cos(Math.toRadians(headingOffset)) - y * Math.sin(Math.toRadians(headingOffset)) + xOffset,
                        x * Math.sin(Math.toRadians(headingOffset)) + y * Math.cos(Math.toRadians(headingOffset)) + yOffset
                ),
                Rotation2d.fromDegrees(90 - heading)
        )
    }

    fun getDerivative(displacement: Double): Pose2d {
        val percentage = displacement / length

        val derivative = derivativeAt(percentage)
        val secondDerivative = secondDerivativeAt(percentage)

        val xDeriv = knotDistance / length
        val yDeriv = derivative * knotDistance / length
        var omega = secondDerivative / (1 + derivative * derivative)
        omega *= knotDistance / length

        return Pose2d(
                Translation2d(
                        xDeriv * Math.cos(Math.toRadians(headingOffset)) - yDeriv * Math.sin(Math.toRadians(headingOffset)),
                        xDeriv * Math.sin(Math.toRadians(headingOffset)) + yDeriv * Math.cos(Math.toRadians(headingOffset))
                ),
                Rotation2d.fromDegrees(90 - omega)
        )
    }

    fun getSecondDerivative(displacement: Double): Pose2d {
        val percentage = displacement / length

        val derivative = derivativeAt(percentage)
        val secondDerivative = secondDerivativeAt(percentage)
        val thirdDerivative = thirdDerivativeAt(percentage)

        val xSecondDeriv = 0.0
        val ySecondDeriv = secondDerivative * knotDistance * knotDistance / (length * length)
        var alpha = (1 + derivative * derivative) * thirdDerivative - secondDerivative * 2.0 * derivative * secondDerivative
        alpha /= (1 + derivative * derivative) * (1 + derivative * derivative)
        alpha *= knotDistance * knotDistance / (length * length)

        return Pose2d(
                Translation2d(
                        xSecondDeriv * Math.cos(Math.toRadians(headingOffset)) - ySecondDeriv * Math.sin(Math.toRadians(headingOffset)),
                        xSecondDeriv * Math.sin(Math.toRadians(headingOffset)) + ySecondDeriv * Math.cos(Math.toRadians(headingOffset))
                ),
                Rotation2d.fromDegrees(90 - alpha)
        )
    }

    fun knotDistance(): Double {
        return knotDistance
    }

    fun xOffset(): Double {
        return xOffset
    }

    fun yOffset(): Double {
        return yOffset
    }

    fun headingOffset(): Double {
        return headingOffset
    }

    fun a(): Double {
        return a
    }

    fun b(): Double {
        return b
    }

    fun c(): Double {
        return c
    }

    fun d(): Double {
        return d
    }

    fun e(): Double {
        return e
    }

    override fun getDirection(s: Double): Pose2d = this.getDerivative(s).unit()

    override fun getPerpendicular(s: Double): Pose2d = this.getSecondDerivative(s).unit()

    override fun getCurvature(s: Double): Double = this.getSecondDerivative(s).translation.norm()

    companion object {
        var ARC_LENGTH_SAMPLES = 100000


        fun adjustAngle(angle: Double): Double {
            var angle = angle
            angle %= 360.0
            while (angle > 180) {
                angle -= 360.0
            }
            while (angle <= -180) {
                angle += 360.0
            }
            return angle
        }
    }
}
