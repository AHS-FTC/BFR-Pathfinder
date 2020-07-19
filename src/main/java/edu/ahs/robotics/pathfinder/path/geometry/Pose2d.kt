package edu.ahs.robotics.pathfinder.path.geometry

import edu.ahs.robotics.pathfinder.path.spline.QuinticHermittianSpline.Companion.adjustAngle

data class Pose2d(val translation: Translation2d, val rotation: Rotation2d) {

    fun fromTranslation(translation: Translation2d): Pose2d {
        return Pose2d(translation, Rotation2d())
    }

    fun fromRotation(rotation: Rotation2d): Pose2d {
        return Pose2d(Translation2d(), rotation)
    }

    fun mirror(): Pose2d {
        return Pose2d(Translation2d(translation.x(), -translation.y()), rotation.inverse())
    }

    fun transformBy(other: Pose2d): Pose2d {
        return Pose2d(translation.translateBy(other.translation.rotateBy(rotation)),
                rotation.rotateBy(other.rotation))
    }

    fun inverse(): Pose2d {
        val rotation_inverted = rotation.inverse()
        return Pose2d(translation.inverse().rotateBy(rotation_inverted), rotation_inverted)
    }

    fun normal(): Pose2d {
        return Pose2d(translation, rotation.normal())
    }

    fun negate() = Pose2d(-this.translation, this.rotation.inverse())
    fun add(p: Pose2d) = Pose2d(this.translation + p.translation,
            Rotation2d.fromDegrees(adjustAngle(this.rotation.getDegrees() + p.rotation.getDegrees())))
    fun add(v: Translation2d) = Pose2d(this.translation + v, this.rotation)
    fun subtract(p: Pose2d) = this.add(p.negate())
    fun multiply(c: Double) = Pose2d(this.translation * c,
            Rotation2d.fromDegrees(rotation.getDegrees() * c))
    fun divide(c: Double) = Pose2d(this.translation / c,
            Rotation2d.fromDegrees(rotation.getDegrees() / c))
    operator fun unaryMinus() = this.negate()
    operator fun plus(p: Pose2d) = this.add(p)
    operator fun plus(v: Translation2d) = this.add(v)
    operator fun minus(p: Pose2d) = this.subtract(p)
    operator fun times(c: Double) = this.multiply(c)
    operator fun div(c: Double) = this.divide(c)

    fun unit() = Pose2d(
            translation.unit(),
            if (rotation.getDegrees() == 0.0) Rotation2d.fromDegrees(0.0)
            else Rotation2d.fromDegrees(rotation.getDegrees() / Math.abs(rotation.getDegrees()))
    )

    // use this one for normalizing changes in pose (changes in heading don't change when rotating)
    // the other one may be used for
    fun rotatePos(angle: Double) = Pose2d(
            this.translation.rotate(angle),
            this.rotation
    )

    fun rotate(angle: Double) = Pose2d(
            this.translation.rotate(angle),
            Rotation2d.fromDegrees((this.rotation.getDegrees() + angle) % 360)
    )

    // heading subtraction is normalized so that it picks the closest direction
    fun interpolate(other: Pose2d, position: Double) = this + (other - this) * position

    // this, relative to that pose (this is that's frame of reference
    fun relative(pose: Pose2d) = (this - pose).rotatePos(pose.rotation.inverse().getDegrees())

    // inverse of relative
    // this in absolute frame of reference given that this is currently in the frame of that
    fun anchor(pose: Pose2d) = this.rotatePos(pose.rotation.getDegrees()) + pose
    fun anchor2(pose: Pose2d) = this.relative(Pose2d.zero().relative(pose)) // alternative implementation



    companion object {
        fun zero(): Pose2d = Pose2d(Translation2d.zero(), Rotation2d.fromDegrees(0.0))
    }
}