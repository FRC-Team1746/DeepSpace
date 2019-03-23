package frc.robot.auton.follower;

import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import frc.robot.auton.follower.SrxMotionProfile;

public class FollowArc {
    private int kPrimaryPIDSlot = 0;
    private int kAuxPIDSlot = 1;
    public int i = 0;
    public int j = 0;
    BufferedTrajectoryPointStream bufferedStream = new BufferedTrajectoryPointStream();
    private class BufferLoader {
        private boolean flipped;
        private boolean flipRobot;
        private int startPosition = 0;
        private int direction;
        private SrxMotionProfile prof;

        public BufferLoader(SrxMotionProfile prof, boolean flipped, int startPosition, boolean flipRobot) {
            this.prof = prof;
            this.flipped = flipped;
            this.startPosition = startPosition;
            this.flipRobot = flipRobot;
        }

        public void init() {
            bufferedStream.Clear();
            direction = flipRobot ? -1 : 1;

            for(int lastPointSent = 0; lastPointSent < prof.numPoints; lastPointSent++) {
                TrajectoryPoint point = new TrajectoryPoint();
                /* Fill out point based on Talon API */
                point.timeDur = (int) prof.points[lastPointSent][2];

                /* Drive Part */
                point.position = direction * prof.points[lastPointSent][0] + startPosition;
                point.velocity = direction * prof.points[lastPointSent][1];

                /* Turn Part */
                point.auxiliaryPos = (flipped ? -1 : 1) * 10 * (prof.points[lastPointSent][3]);
                point.auxiliaryVel = 0;
                point.auxiliaryArbFeedFwd = 0;

                point.profileSlotSelect0 = kPrimaryPIDSlot;
                point.profileSlotSelect1 = kAuxPIDSlot;
                point.useAuxPID = true;
                point.isLastPoint = ((lastPointSent + 1) == prof.numPoints);

                bufferedStream.Write(point);
            }
        }
    }

    private BufferLoader buffer;
    private AutonDriveTrain drivetrain;
    private TalonSRX rightTalon;
    private TalonSRX leftTalon;
    private PigeonIMU gyro;
    private ArrayList<Double> errList = new ArrayList<Double>();
    StringBuilder sb = new StringBuilder();
    private double err;
    private SrxTrajectory trajectoryToFollow;
    private boolean flipRobot;


    public FollowArc(AutonDriveTrain drivetrain, SrxTrajectory trajectoryToFollow) {
        this(drivetrain, trajectoryToFollow, false);
    }

    public FollowArc(AutonDriveTrain drivetrain, SrxTrajectory trajectoryToFollow, boolean flipRobot) {
        this.drivetrain = drivetrain;
        this.trajectoryToFollow = trajectoryToFollow;
        this.flipRobot = flipRobot;

        rightTalon = drivetrain.getRight();
        leftTalon = drivetrain.getLeft();
        gyro = drivetrain.getGyro();
    }

    public void init() {
        setUpTalon(rightTalon);
        setUpTalon(leftTalon);
        setUpGyro(gyro);

        leftTalon.follow(rightTalon, FollowerType.AuxOutput1);
        buffer = new BufferLoader(trajectoryToFollow.centerProfile, trajectoryToFollow.flipped, 
        drivetrain.getDistance(), flipRobot);
        buffer.init();
        rightTalon.startMotionProfile(bufferedStream, 10, ControlMode.MotionProfileArc);
    }

    public void run() {
        if(i < trajectoryToFollow.centerProfile.numPoints) {
            StringBuilder sb = new StringBuilder();
            double acVel = Math.abs((rightTalon.getSelectedSensorPosition()) + 
            leftTalon.getSelectedSensorPosition())*0.5;
            double expeVel = rightTalon.getActiveTrajectoryPosition();
            double err1 = acVel - expeVel;
            errList.add(err1);
            String line = "";
            line += i+','+err1 + "\n";
            sb.append(line);
            System.out.println(line);
            i++;
        } else {
            try (PrintWriter pw = new PrintWriter(new File("C:\\Users\\ayush\\Desktop\\FRC\\error.csv"))) {
                System.out.println("error csv created");
                pw.write(sb.toString());
            } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
            }
        }
        if(rightTalon.isMotionProfileFinished()) {
            end();
        }
    }

    public void end() {
        resetTalon(rightTalon, ControlMode.PercentOutput, 0);
        resetTalon(leftTalon, ControlMode.PercentOutput, 0);
        if(j==0) {
            for(double e : errList) {
                err += e;
            }
            j++;
        }
        double eAvg = err / errList.size();
        System.out.println("AVERAGE ERROR: " + eAvg);
    }

    private void setUpTalon(TalonSRX talon) {
        talon.clearMotionProfileTrajectories();
        talon.changeMotionControlFramePeriod(5);
        talon.clearMotionProfileHasUnderrun(10);
        talon.setSelectedSensorPosition(0, 0, 100);
        talon.setSelectedSensorPosition(0, 1, 100);
        talon.getSensorCollection().setQuadraturePosition(0, 100);
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