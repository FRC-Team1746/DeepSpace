package frc.robot;


// import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
// import java.lang.Math;

// import javax.swing.tree.DefaultMutableTreeNode;

// import com.ctre.phoenix.motorcontrol.ControlMode;
// import com.ctre.phoenix.motorcontrol.FeedbackDevice;
// import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
// import com.ctre.phoenix.motorcontrol.SensorTerm;
// import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.sensors.PigeonIMU;

public class TeleopDrive {
    private Controls m_controls;
    public WPI_TalonSRX m_LeftMaster;
    private PigeonIMU gyro;
    private WPI_VictorSPX m_LeftFollowerA;
    private WPI_VictorSPX m_LeftFollowerB;
    public WPI_TalonSRX m_RightMaster;
    private WPI_VictorSPX m_RightFollowerA;
    private WPI_VictorSPX m_RightFollowerB;
    ElectricalConstants eConstants;
    // Encoder m_encoderLeft;
    // Encoder m_encoderRight;
    Gyro m_Gyro;
    DifferentialDrive myRobot;


public TeleopDrive(Controls controls) {
    m_controls = controls;
    eConstants = new ElectricalConstants();
    
    m_LeftMaster = new WPI_TalonSRX(eConstants.MOTOR_DRIVE_LEFT_MASTER);
    m_LeftFollowerA = new WPI_VictorSPX(eConstants.MOTOR_DRIVE_LEFT_FOLLOWER_A);
    m_LeftFollowerB = new WPI_VictorSPX(eConstants.MOTOR_DRIVE_LEFT_FOLLOWER_B);
    m_RightMaster = new WPI_TalonSRX(eConstants.MOTOR_DRIVE_RIGHT_MASTER);
    m_RightFollowerA = new WPI_VictorSPX(eConstants.MOTOR_DRIVE_RIGHT_FOLLOWER_A);
    m_RightFollowerB = new WPI_VictorSPX(eConstants.MOTOR_DRIVE_RIGHT_FOLLOWER_B);
    gyro = new PigeonIMU(eConstants.GYRO);
    
    m_RightMaster.setInverted(true);

    m_LeftFollowerA.follow(m_LeftMaster);
    m_LeftFollowerB.follow(m_LeftMaster);
    m_RightFollowerA.follow(m_RightMaster);
    m_RightFollowerB.follow(m_RightMaster);	

    myRobot = new DifferentialDrive(m_LeftMaster, m_RightMaster);

	// m_encoderLeft = new Encoder(eConstants.ENCODER_DRIVE_LEFT_A, eConstants.ENCODER_DRIVE_LEFT_B, false, Encoder.EncodingType.k1X);
	// m_encoderRight = new Encoder(eConstants.ENCODER_DRIVE_RIGHT_A, eConstants.ENCODER_DRIVE_RIGHT_B, false, Encoder.EncodingType.k1X);
    m_Gyro = new ADXRS450_Gyro();
    m_Gyro.reset();

    
}


public void teleopArcadeDrive(){
    // if (!m_controls.driver_A_Button()) {
    myRobot.arcadeDrive(-m_controls.driver_YL_Axis(), m_controls.driver_XL_Axis()/10*6);
    System.out.println("Left Encoder: " + getEncoderLeft());
    System.out.println("Right Encoder: " + getEncoderRight());
    System.out.println("Gyro Value: " + getAngle());
    // }else {
    // myRobot.arcadeDrive(-m_controls.driver_Y_Axis(), m_controls.driver_X_Axis());
    // }
    setRampRate(0.0);
//		setCoast(true);		
}


public int getEncoderLeft(){
    return m_LeftMaster.getSelectedSensorPosition(Constants.kPIDLoopIdx);
}

public int getEncoderRight(){
    return m_RightMaster.getSelectedSensorPosition(Constants.kPIDLoopIdx);
}

// public double getEncoderLeftInches(){
//     return m_LeftMaster.getSelectedSensorPosition(Constants.kPIDLoopIdx) / AutonConstants.ticksPerInch;
// }

// public double getEncoderRightInches(){
//     return m_RightMaster.getSelectedSensorPosition(Constants.kPIDLoopIdx) / AutonConstants.ticksPerInch;
// }

public int getEncoderLeftVelocity(){
    return m_LeftMaster.getSelectedSensorVelocity(Constants.kPIDLoopIdx);
}

public int getEncoderRightVelocity(){
    return m_RightMaster.getSelectedSensorVelocity(Constants.kPIDLoopIdx);
}

public void resetEncoders(){
    m_RightMaster.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    m_LeftMaster.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
}

public double getHeading(){
    return m_Gyro.getAngle();
}
	
public void resetGyro(){
    m_Gyro.reset();
}

public double getAngle() {
    double[] ypr_deg = new double[3];
    gyro.getYawPitchRoll(ypr_deg);
    return ypr_deg[2];
}

public void setRampRate(double rate){
    m_LeftMaster.configOpenloopRamp(rate, 10);
    m_RightMaster.configOpenloopRamp(rate, 10);

}

public void setBrakeMode(boolean brake) {
    if (brake){
        m_LeftMaster.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake); 
        m_RightMaster.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake); 
    }else{
        m_LeftMaster.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
        m_RightMaster.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
    }
}

}