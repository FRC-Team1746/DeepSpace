package frc.auton.follower;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Notifier;
import frc.auton.follower.SrxMotionProfile;;

public class FollowArc {
    // Test
    private int distancePidSlot = 0;
    private int rotationPidSlot = 1;
    private int kMinPointsInTalon = 5;
    private boolean isFinished = false;
    private SrxTrajectory trajectoryToFollow = null;
    private MotionProfileStatus status = new MotionProfileStatus();
    private boolean hasPathStarted;
    private SetValueMotionProfile setValue = SetValueMotionProfile.Enable;

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
                point.timeDur = 10;
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
    private AutonDriveTrain drivetrain;
    private TalonSRX rightTalon;
    private TalonSRX leftTalon;

    public FollowArc(AutonDriveTrain drivetrain, SrxTrajectory trajectoryToFollow) {
        this.drivetrain = drivetrain;
        this.trajectoryToFollow = trajectoryToFollow;

        rightTalon = drivetrain.getRightTalon();
        leftTalon = drivetrain.getLeftTalon();
    }

    public void init() {
        setUpTalon(rightTalon);
        setUpTalon(leftTalon);

        setValue = SetValueMotionProfile.Disable;

        rightTalon.set(ControlMode.MotionProfile, setValue.value);
        leftTalon.follow(rightTalon, FollowerType.AuxOutput1);
        buffer = new Notifier(new BufferLoader(rightTalon, trajectoryToFollow.centerProfile, trajectoryToFollow.flipped, drivetrain.getDistance()));
        buffer.startPeriodic(0.005);
    }

    public void run() {
        rightTalon.getMotionProfileStatus(status);

        if (status.isUnderrun) {
            // The MP has underrun 
            setValue = SetValueMotionProfile.Disable;
        } else if (status.btmBufferCnt > kMinPointsInTalon) {
            // If there are enough waypoints, go
            setValue = SetValueMotionProfile.Enable;
        } else if (status.activePointValid && status.isLast) {
            // If the point is valid and last, hold
            setValue = SetValueMotionProfile.Hold;
        }
        System.out.println("Status of Talon: " + setValue.value);
        rightTalon.set(ControlMode.MotionProfile, setValue.value);

    }

    public boolean isFinished() {
        if (!hasPathStarted) {
            return false;
        }
        boolean leftComplete = status.activePointValid && status.isLast;
        boolean trajectoryComplete = leftComplete;
        return trajectoryComplete || isFinished;
    }

    public void end() {
        buffer.stop();
        resetTalon(rightTalon, ControlMode.PercentOutput, 0);
        resetTalon(leftTalon, ControlMode.PercentOutput, 0);
    }

    private void setUpTalon(TalonSRX talon) {
        talon.clearMotionProfileTrajectories();
        talon.changeMotionControlFramePeriod(5);
        talon.clearMotionProfileHasUnderrun(10);
    }

    private void resetTalon(TalonSRX talon, ControlMode controlMode, double setValue) {
		talon.clearMotionProfileTrajectories();
		talon.clearMotionProfileHasUnderrun(10);
		talon.changeMotionControlFramePeriod(10);
		talon.set(controlMode, setValue);
	}

}