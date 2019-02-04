package frc.robot.auton.trajectory;

public class SrxMotionProfile {
    public int numPoints;
    public double[][] points;

    public SrxMotionProfile() {

    }

    public SrxMotionProfile(int numPoints, double[][] points) {
        this.numPoints = numPoints;
        this.points = points;
    }
}