package frc.auton.follower;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import frc.robot.ElectricalConstants;

public class AutonDriveTrain {
    private ElectricalConstants eConstants;
    private TalonSRXConfiguration srxConfig = new TalonSRXConfiguration();
    private TalonSRX rightTalon;
    private VictorSPX rightFollowerA;
	private VictorSPX rightFollowerB;
    private TalonSRX leftTalon;
    private ADXRS450_Gyro gyro;
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
        gyro = new ADXRS450_Gyro();

        /* remote 0 will look at gyro */
        srxConfig.remoteFilter0.remoteSensorDeviceID = (int) gyro.getAngle();
        srxConfig.remoteFilter0.remoteSensorSource = RemoteSensorSource.GadgeteerPigeon_Yaw;
        /* remote 1 will capture leftTalon */
        srxConfig.remoteFilter1.remoteSensorDeviceID = leftTalon.getDeviceID();
        srxConfig.remoteFilter1.remoteSensorSource = RemoteSensorSource.TalonSRX_SelectedSensor;
        /* setting differences for PID calculation */ 
        srxConfig.diff0Term = FeedbackDevice.QuadEncoder;
        srxConfig.diff1Term = FeedbackDevice.RemoteSensor1;
        srxConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.SensorDifference;
        srxConfig.primaryPID.selectedFeedbackCoefficient = 0.5; /* divide by 2 so we servo sensor-average, intead of sum */
        /* turn position will come from the gyro */
        srxConfig.auxiliaryPID.selectedFeedbackSensor = FeedbackDevice.RemoteSensor0;
        /* PID for each slot */
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
        /* speed up polling so trajectory points can be loaded faster */
        leftTalon.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, 0);

        leftTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        rightTalon.setInverted(InvertType.InvertMotorOutput);
        rightFollowerA.follow(rightTalon);
        rightFollowerB.follow(rightTalon);
        leftFollowerA.follow(leftTalon);
        leftFollowerB.follow(leftTalon);

    }

    public TalonSRX getRightTalon() {
        return rightTalon;
    }

    public TalonSRX getLeftTalon() {
        return leftTalon;
    }

    public double getDistance() {
        return rightTalon.getSelectedSensorPosition();
    }
}