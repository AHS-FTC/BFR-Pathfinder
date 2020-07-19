package edu.ahs.robotics.pathfinder.path.geometry

class Rotation2d {
    private val kEpsilon = 1e-12

    private val cosAngle: Double
    private val sinAngle: Double

    constructor(x: Double = 1.0, y: Double = 0.0) {
        cosAngle = x
        sinAngle = y
    }

    constructor(other: Rotation2d) {
        cosAngle = other.cosAngle
        sinAngle = other.sinAngle
    }

    fun getRadians(): Double {
        return Math.atan2(sinAngle, cosAngle)
    }

    fun getDegrees(): Double {
        return Math.toDegrees(getRadians())
    }

    fun normal(): Rotation2d {
        return Rotation2d(-sinAngle, cosAngle)
    }

    fun inverse(): Rotation2d {
        return Rotation2d(cosAngle, -sinAngle)
    }

    fun cos(): Double {
        return cosAngle
    }

    fun sin(): Double {
        return sinAngle
    }

    fun tan(): Double {
        return if (Math.abs(cosAngle) < kEpsilon) {
            if (sinAngle >= 0.0) {
                Double.POSITIVE_INFINITY
            } else {
                Double.NEGATIVE_INFINITY
            }
        } else sinAngle / cosAngle
    }

    fun rotateBy(other: Rotation2d): Rotation2d {
        return Rotation2d(cosAngle * other.cosAngle - sinAngle * other.sinAngle,
                cosAngle * other.sinAngle + sinAngle * other.cosAngle)
    }


    companion object {
        fun fromRadians(angleRadians: Double): Rotation2d {
            return Rotation2d(Math.cos(angleRadians), Math.sin(angleRadians))
        }

        fun fromDegrees(angleDegrees: Double): Rotation2d {
            return fromRadians(Math.toRadians(angleDegrees))
        }
    }
}
