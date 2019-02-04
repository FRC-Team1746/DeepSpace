package frc.robot.auton.trajectory;

import java.math.BigDecimal;

import frc.robot.auton.team254.lib.trajectory.Path;
import frc.robot.auton.team254.lib.trajectory.Trajectory;

public class SrxTranslator {

    public SrxTrajectory getSrxMotionProfileFromChezyPath(Path chezyPath, SrxTranslatorConfig config) {

        double[][] srxWaypoints = extractSrxPointsFromChezyTraj(chezyPath.getTrajectory(), config.wheelDiaInch, config.scaleFactor, config.encoderTicksPerRev);
        SrxMotionProfile center = new SrxMotionProfile(srxWaypoints.length, srxWaypoints);

        return new SrxTrajectory(center);
    }

    public double[][] extractSrxPointsFromChezyTraj(Trajectory traj, double wheelDiaInch, double scaleFactor, int encoderTicksPerRev) {
        // create an array of points for SRX from CheezyTraj
        double[][] points = new double[traj.getSegments().length][4];
        double lastHeading = 0;
        double continuousHeading = 0;
        // Fill that array up
        for (int i = 0; i < traj.getSegments().length; i++) {
            // translate from feet to encoderTicks
            points[i][0] = convertFeetToEncoderTicks(traj.getSegment(i).pos, wheelDiaInch, scaleFactor, encoderTicksPerRev);
            // translate fps to rpm
            points[i][1] = convertFpsToEncoderTicksPer100ms(traj.getSegment(i).vel, wheelDiaInch, scaleFactor, encoderTicksPerRev);
            // translate from seconds to milliseconds
            points[i][2] = traj.getSegment(i).dt * 1000;
            double nextHeading = new BigDecimal(Math.toDegrees(traj.getSegment(i).heading)).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
            if (nextHeading > 180) {
                nextHeading -= 360;
            }
            if (i != 0) {
                double headingDifference = nextHeading - lastHeading;
                if (headingDifference >= 360) {
                    headingDifference -= 360;
                }
                continuousHeading += headingDifference;
            }
            else {
                if (nextHeading >= 360) {
                    nextHeading -= 360;
                }
                continuousHeading = nextHeading;
            }
            points[i][3] = continuousHeading;
            lastHeading = continuousHeading;
        }
        return points;
    }
    public double[][] extractSrxPointsFromChezyTraj(Trajectory traj, double wheelDiaInch, double scaleFactor) {
        // Create an array for the SRX
        double[][] points = new double[traj.getSegments().length][3];
        // Fill array up with SRX points
        for (int i=0; i < traj.getSegments().length; i++) {
            // translate feet to rotations
            points[i][0] = convertFeetToEncoderRotations(traj.getSegment(i).pos, wheelDiaInch, scaleFactor);
            // translate fps to rpm
            points[i][1] = convertFpsToRpm(traj.getSegment(i).vel, wheelDiaInch, scaleFactor);
            // translate seconds to milliseconds
            points[i][2] = traj.getSegment(i).dt * 1000;
        }

        return points;
    }
    // Convert a 254 feet into SRX encoder ticks
    public double convertFeetToEncoderTicks(double feet, double wheelDiaInch, double scaleFactor, int encoderTicksPerRev) {
        double wheelRotations = feet * 12 / (Math.PI / wheelDiaInch);  
        
        double encoderRotations = wheelRotations * scaleFactor;
        double encoderTicks = encoderRotations * encoderTicksPerRev;
        return encoderTicks;
    }

    public double convertFeetToEncoderRotations(double feet, double wheelDiaInch, double scaleFactor) {
        double wheelRotations = feet * 12 / (Math.PI / wheelDiaInch);

        double encoderRotations = wheelRotations * scaleFactor;
        return encoderRotations;
    }

    public double convertFpsToEncoderTicksPer100ms(double fps, double wheelDiaInch, double scaleFactor, int encoderTicksPerRev) {
        double fpm = fps * 60;
        // Convert fpm to rpm
        double rpm = fpm * 12 / (Math.PI * wheelDiaInch);
        // rpm to encoder rpm
        double encoderRpm = rpm * scaleFactor;
        // ticks for each millisecond
        double ticksPer100ms = encoderRpm * encoderTicksPerRev / 600.0;

        return ticksPer100ms;
    }

    public double convertFpsToRpm(double fps, double wheelDiaInch, double scaleFactor) {
        // Convert minutes to seconds
        double fpm = fps * 60;
        // Convert feet to rotations
        double rpm = fpm * 12 / (Math.PI * wheelDiaInch);
        // rpm to encoderTicks
        double encoderRpm = rpm * scaleFactor;
        return encoderRpm;
    }
}