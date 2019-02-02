package frc.auton.follower;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import frc.auton.follower.SrxMotionProfile;;

public class FollowArc {
    private SrxTrajectory trajectoryToFollow = null;
    private MotionProfileStatus status = new MotionProfileStatus();
    public int i = 0;
    BufferedTrajectoryPointStream bufferedStream = new BufferedTrajectoryPointStream();
    private class BufferLoader {
        private boolean flipped;
        private double startPosition = 0;
        private double gyroPosition = 0;
        private SrxMotionProfile prof;

        public BufferLoader(SrxMotionProfile prof, boolean flipped, double startPosition, double gyroPosition) {
            this.prof = prof;
            this.flipped = flipped;
            this.startPosition = startPosition;
            this.gyroPosition = gyroPosition;
        }

        public void init() {
            bufferedStream.Clear();

            for(int lastPointSent = 0; lastPointSent < prof.numPoints; lastPointSent++) {
                TrajectoryPoint point = new TrajectoryPoint();
                /* Fill out point based on Talon API */
                point.position = prof.points[lastPointSent][0] + startPosition;
                point.velocity = prof.points[lastPointSent][1];
                point.timeDur = (int) prof.points[lastPointSent][2];
                point.auxiliaryPos = (flipped ? -1 : 1) * (prof.points[lastPointSent][3] + gyroPosition);
                point.profileSlotSelect0 = Constants.kPrimaryPIDSlot;
                point.profileSlotSelect1 = Constants.kAuxPIDSlot;
                point.isLastPoint = false;
                point.useAuxPID = true;
                if ((lastPointSent + 1) == prof.numPoints) {
                    point.isLastPoint = true;
                }

                bufferedStream.Write(point);
                // System.out.println("Last Point was: " + lastPointSent);
                // System.out.println("Number of Points: " + prof.numPoints);
            }
        }

    }

    private BufferLoader buffer;
    private AutonDriveTrain drivetrain;
    private TalonSRX rightTalon;
    private TalonSRX leftTalon;
    private PigeonIMU gyro;

    public FollowArc(AutonDriveTrain drivetrain, SrxTrajectory trajectoryToFollow) {
        this.drivetrain = drivetrain;
        this.trajectoryToFollow = trajectoryToFollow;

        rightTalon = drivetrain.getRight();
        leftTalon = drivetrain.getLeft();
        gyro = drivetrain.getGyro();
    }

    public void init() {
        setUpTalon(rightTalon);
        setUpTalon(leftTalon);
        setUpGyro(gyro);
        // if(rightTalon.getSelectedSensorPosition() != 0) {
        //     rightTalon.setSelectedSensorPosition(0, 0, 10);
        //     rightTalon.getSensorCollection().setQuadraturePosition(0, 10);
        //     i++;
        // }
        // System.out.println("Loops Completed: " + i);
        System.out.println("EncoderRight count: " + rightTalon.getSelectedSensorPosition());
        System.out.println("EncoderLeft count: " + leftTalon.getSelectedSensorPosition());
        leftTalon.follow(rightTalon, FollowerType.AuxOutput1);
        buffer = new BufferLoader(trajectoryToFollow.centerProfile, trajectoryToFollow.flipped, drivetrain.getDistance(), drivetrain.getAngle());
        buffer.init();
        rightTalon.startMotionProfile(bufferedStream, 10, ControlMode.MotionProfileArc);

    }

    public void run() {
        rightTalon.getMotionProfileStatus(status);

        String line = "";
        line += "  rightencoderCount: " + rightTalon.getSelectedSensorPosition() + "\n";
        line += "  leftencoderCount: " + leftTalon.getSelectedSensorPosition() + "\n";
        // line += "  topBufferRem: " + status.topBufferRem + "\n";
        // line += "  topBufferCnt: " + status.topBufferCnt + "\n";
        // line += "  btmBufferCnt: " + status.btmBufferCnt + "\n";
        // line += "  hasUnderrun: " + status.hasUnderrun + "\n";
        // line += "  isUnderrun: " + status.isUnderrun + "\n";
        // line += "  activePointValid: " + status.activePointValid + "\n";
        // line += "  isLast: " + status.isLast + "\n";
        // line += "  profileSlotSelect0: " + status.profileSlotSelect + "\n";
        // line += "  profileSlotSelect1: " + status.profileSlotSelect1 + "\n";
        // line += "  outputEnable: " + status.outputEnable.toString() + "\n";
        // line += "  timeDurMs: " + status.timeDurMs + "\n";
        System.out.println(line);
        
        if(rightTalon.isMotionProfileFinished()) {
            end();
        }
    }

    public void end() {
        System.out.println("TAKE A STEP BACK!!!!!!!!");
        resetTalon(rightTalon, ControlMode.PercentOutput, 0);
        resetTalon(leftTalon, ControlMode.PercentOutput, 0);
    }

    private void setUpTalon(TalonSRX talon) {
        talon.clearMotionProfileTrajectories();
        talon.changeMotionControlFramePeriod(5);
        talon.clearMotionProfileHasUnderrun(10);
        talon.getSensorCollection().setQuadraturePosition(0, 15);
        talon.setSelectedSensorPosition(0, 0, 15);
    }

    private void setUpGyro(PigeonIMU gyro) {
        gyro.setYaw(0.0);
        gyro.setFusedHeading(0.0);
    }

    private void resetTalon(TalonSRX talon, ControlMode controlMode, double setValue) {
		talon.clearMotionProfileTrajectories();
		talon.clearMotionProfileHasUnderrun(10);
		talon.changeMotionControlFramePeriod(10);
		talon.set(controlMode, setValue);
	}

}