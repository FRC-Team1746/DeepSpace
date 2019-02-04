package frc.robot.auton.follower;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.sensors.PigeonIMU;

import frc.robot.ElectricalConstants;

public class AutonDriveTrain implements FollowsArc {
    private ElectricalConstants eConstants;
    private TalonSRXConfiguration srxConfig = new TalonSRXConfiguration();
    private TalonSRX rightTalon;
    private VictorSPX rightFollowerA;
	private VictorSPX rightFollowerB;
    private TalonSRX leftTalon;
    private PigeonIMU gyro;
    private TalonSRX gyroTalon;
	private VictorSPX leftFollowerA;
    private VictorSPX leftFollowerB;
    
    public AutonDriveTrain() {
        eConstants = new ElectricalConstants();
        rightTalon = new TalonSRX(eConstants.MOTOR_DRIVE_RIGHT_MASTER);
        rightFollowerA = new VictorSPX(eConstants.MOTOR_DRIVE_RIGHT_FOLLOWER_A);
        rightFollowerB = new VictorSPX(eConstants.MOTOR_DRIVE_RIGHT_FOLLOWER_B);
        leftTalon = new TalonSRX(eConstants.MOTOR_DRIVE_LEFT_MASTER);
        leftFollowerA = new VictorSPX(eConstants.MOTOR_DRIVE_LEFT_FOLLOWER_A);
        leftFollowerB = new VictorSPX(eConstants.MOTOR_DRIVE_LEFT_FOLLOWER_B);
        gyroTalon = new TalonSRX(eConstants.GYRO);
        gyro = new PigeonIMU(gyroTalon);

        srxConfig.slot0.kF = Constants.kGains_MotProf.kF;
        srxConfig.slot0.kP = Constants.kGains_MotProf.kP;
        srxConfig.slot0.kI = Constants.kGains_MotProf.kI;
        srxConfig.slot0.kD = Constants.kGains_MotProf.kD;
        srxConfig.slot0.integralZone = (int) Constants.kGains_MotProf.kIzone;
        srxConfig.slot0.closedLoopPeakOutput = Constants.kGains_MotProf.kPeakOutput;

        srxConfig.slot1.kF = Constants.kGains_MotProf.kF;
        srxConfig.slot1.kP = Constants.kGains_MotProf.kP;
        srxConfig.slot1.kI = Constants.kGains_MotProf.kI;
        srxConfig.slot1.kD = Constants.kGains_MotProf.kD;
        srxConfig.slot1.integralZone = (int) Constants.kGains_MotProf.kIzone;
        srxConfig.slot1.closedLoopPeakOutput = Constants.kGains_MotProf.kPeakOutput;

        rightTalon.configAllSettings(srxConfig);
        rightTalon.setInverted(true);
        rightTalon.setSensorPhase(false);
        rightFollowerA.setInverted(InvertType.FollowMaster);
        rightFollowerB.setInverted(InvertType.FollowMaster);  

        /* speed up polling so trajectory points can be loaded faster */
        leftTalon.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, 0);

        /* Configuring Remote Sensors */
        leftTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        rightTalon.configRemoteFeedbackFilter(leftTalon.getDeviceID(), RemoteSensorSource.TalonSRX_SelectedSensor, 0, 0);
        rightTalon.configRemoteFeedbackFilter(gyro.getDeviceID(), RemoteSensorSource.GadgeteerPigeon_Yaw, 1, 0);
        rightTalon.configSensorTerm(SensorTerm.Sum0, FeedbackDevice.RemoteSensor0, 0);
        rightTalon.configSensorTerm(SensorTerm.Sum1, FeedbackDevice.QuadEncoder, 0);
        rightTalon.configSelectedFeedbackSensor(FeedbackDevice.SensorSum, 0, 0);
        rightTalon.configSelectedFeedbackCoefficient(0.5, 0, 0);

        rightTalon.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor1, 1, 0);
        rightTalon.configSelectedFeedbackCoefficient((3600.0 / 2000.0), 1, 0);

        rightFollowerA.follow(rightTalon);
        rightFollowerB.follow(rightTalon);
        leftFollowerA.follow(leftTalon);
        leftFollowerB.follow(leftTalon);

    }

    public TalonSRX getRight() {
        return rightTalon;
    }

    public TalonSRX getLeft() {
        return leftTalon;
    }

    public PigeonIMU getGyro() {
        return gyro;
    }

    public double getAngle() {
        double[] ypr_deg = new double[3];
        gyro.getYawPitchRoll(ypr_deg);
        return ypr_deg[0];
    }

    public double getDistance() {
        return rightTalon.getSelectedSensorPosition();
    }
}