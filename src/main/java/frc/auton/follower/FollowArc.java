package frc.auton.follower;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.auton.follower.SrxMotionProfile;;

public class FollowArc {
    private boolean isFinished = false;
    private SrxTrajectory trajectoryToFollow = null;
    private MotionProfileStatus status = new MotionProfileStatus();
    private boolean hasPathStarted;
    public int i = 0;
    BufferedTrajectoryPointStream bufferedStream = new BufferedTrajectoryPointStream();
    private class BufferLoader {
        private int lastPointSent = 0;
        private boolean flipped;
        private double startPosition = 0;
        private SrxMotionProfile prof;

        public BufferLoader(SrxMotionProfile prof, boolean flipped, double startPosition) {
            this.prof = prof;
            this.flipped = flipped;
            this.startPosition = startPosition;
        }

        public void init() {
            bufferedStream.Clear();

            if(lastPointSent >= prof.numPoints) {
                return;
            }

            if(lastPointSent < prof.numPoints) {
                TrajectoryPoint point = new TrajectoryPoint();
                /* Fill out point based on Talon API */
                point.position = prof.points[lastPointSent][0] + startPosition;
                point.velocity = prof.points[lastPointSent][1];
                point.timeDur = 10;
                point.auxiliaryPos = (flipped ? -1 : 1) * 10 * (prof.points[lastPointSent][3]);
                point.profileSlotSelect0 = Constants.kPrimaryPIDSlot;
                point.profileSlotSelect1 = Constants.kAuxPIDSlot;
                point.zeroPos = false;
                point.isLastPoint = false;
                point.useAuxPID = true;
                if ((lastPointSent + 1) == prof.numPoints) {
                    point.isLastPoint = true;
                }

                bufferedStream.Write(point);
                lastPointSent++;
            }
        }

    }

    private BufferLoader buffer;
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

        leftTalon.follow(rightTalon, FollowerType.AuxOutput1);
        buffer = new BufferLoader(trajectoryToFollow.centerProfile, trajectoryToFollow.flipped, drivetrain.getDistance());
        buffer.init();
    }

    public void run() {
        rightTalon.startMotionProfile(bufferedStream, 10, ControlMode.MotionProfile);
        rightTalon.getMotionProfileStatus(status);

        if(rightTalon.isMotionProfileFinished() && isFinished()) {
            end();
        }
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