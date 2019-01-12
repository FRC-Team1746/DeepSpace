package frc.auton.follower;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Notifier;
import frc.auton.trajectory.SrxMotionProfile;
import frc.auton.trajectory.SrxTrajectory;

public class FollowArc {
    // Test
    private int distancePidSlot = 0;
    private int rotationPidSlot = 1;
    private int kMinPointsInTalon = 5;
    private boolean isFinished = false;
    private SrxTrajectory trajectoryToFollow = null;
    private MotionProfileStatus status = new MotionProfileStatus();
    private boolean hasPathStarted;
    private SetValueMotionProfile setValue = SetValueMotionProfile.Disable;

    private class BufferLoader implements java.lang.Runnable {
        private int lastPointSent = 0;
        private boolean flipped;
        private TalonSRX talon;
        private SrxMotionProfile prof;
        private double startPosition = 0;

        public BufferLoader(TalonSRX talon, SrxMotionProfile prof, boolean flipped, double startPosition) {
            this.talon = talon;
            this.prof = prof;
            this.flipped = flipped;
            this.startPosition = startPosition;
        }

        public void run() {
            talon.processMotionProfileBuffer();

            if(lastPointSent >= prof.numPoints) {
                return;
            }

            if(!talon.isMotionProfileTopLevelBufferFull() && lastPointSent < prof.numPoints) {
                TrajectoryPoint point = new TrajectoryPoint();
                /* Fill out point based on Talon API */
                point.position = prof.points[lastPointSent][0] + startPosition;
                point.velocity = prof.points[lastPointSent][1];
                point.timeDur = TrajectoryDuration.Trajectory_Duration_10ms;
                point.auxiliaryPos = (flipped ? -1 : 1) * 10 * (prof.points[lastPointSent][3]);
                point.profileSlotSelect0 = distancePidSlot;
                point.profileSlotSelect1 = rotationPidSlot;
                point.zeroPos = false;
                point.isLastPoint = false;
                if ((lastPointSent + 1) == prof.numPoints) {
                    point.isLastPoint = true;
                }

                talon.pushMotionProfileTrajectory(point);
                lastPointSent++;
                hasPathStarted = true;
            }
        }

    }

    private Notifier buffer;
    private FollowsArc drivetrain;
    private TalonSRX rightTalon;
    private TalonSRX leftTalon;

    public FollowArc(FollowsArc drivetrain, SrxTrajectory trajectoryToFollow) {
        this.drivetrain = drivetrain;
        this.trajectoryToFollow = trajectoryToFollow;

        rightTalon = drivetrain.getRight();
        leftTalon = drivetrain.getLeft();
    }

}