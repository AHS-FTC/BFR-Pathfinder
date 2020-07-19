package edu.ahs.robotics.pathfinder.path.geometry

class Translation2d {
    private val x: Double
    private val y: Double

    constructor() {
        this.x = 0.0
        this.y = 0.0
    }

    constructor(x: Double, y: Double) {
        this.x = x
        this.y = y
    }

    constructor(other: Translation2d) {
        x = other.x
        y = other.y
    }

    constructor(start: Translation2d, end: Translation2d) {
        x = end.x - start.x
        y = end.y - start.y
    }

    fun translateBy(other: Translation2d): Translation2d {
        return Translation2d(x + other.x, y + other.y)
    }

    fun rotateBy(rotation: Rotation2d): Translation2d {
        return Translation2d(x * rotation.cos() - y * rotation.sin(),
                x * rotation.sin() + y * rotation.cos())
    }

    fun inverse() = Translation2d(-this.x, -this.y)
    fun add(v: Translation2d) = Translation2d(this.x + v.x, this.y + v.y)
    fun subtract(v: Translation2d) = this.add(v.inverse())
    fun multiply(c: Double) = Translation2d(x * c, y * c)
    fun divide(c: Double) = Translation2d(x / c, y / c)
    operator fun unaryMinus() = this.inverse()
    operator fun plus(v: Translation2d) = this.add(v)
    operator fun minus(v: Translation2d) = this.subtract(v)
    operator fun times(c: Double) = this.multiply(c)
    operator fun div(c: Double) = this.divide(c)

    fun norm() = Math.hypot(this.x, this.y)
    fun unit() = this / this.norm()

    fun rotate(angle: Double): Translation2d {
        // CW is +angle, so negate the angle passed into the transformation
        // because the transformation assumes CCW is +angle
        val sinTheta = Math.sin(Math.toRadians(-angle))
        val cosTheta = Math.cos(Math.toRadians(-angle))

        return Translation2d(
                cosTheta * x - sinTheta * y,
                sinTheta * x + cosTheta * y
        )
    }

    fun distanceTo(translation2d: Translation2d): Double {
        return Math.hypot(x-translation2d.x, y-translation2d.y)
    }

    fun x(): Double {
        return x
    }

    fun y(): Double {
        return y
    }

    companion object {
        fun zero(): Translation2d = Translation2d(0.0, 0.0)
    }
}