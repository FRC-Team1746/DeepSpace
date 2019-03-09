package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import frc.robot.constants.ElectricalConstants;
import frc.robot.constants.Controls;

public class TeleopDrive {
    private Controls m_controls;
    public WPI_TalonSRX m_LeftMaster;
    private WPI_VictorSPX m_LeftFollowerA;
    private WPI_VictorSPX m_LeftFollowerB;
    public WPI_TalonSRX m_RightMaster;
    private WPI_VictorSPX m_RightFollowerA;
    private WPI_VictorSPX m_RightFollowerB;
    ElectricalConstants eConstants;

    public TeleopDrive(Controls controls) {
        m_controls = controls;
        eConstants = new ElectricalConstants();
        
        m_LeftMaster = new WPI_TalonSRX(eConstants.MOTOR_DRIVE_LEFT_MASTER);
        m_LeftFollowerA = new WPI_VictorSPX(eConstants.MOTOR_DRIVE_LEFT_FOLLOWER_A);
        m_LeftFollowerB = new WPI_VictorSPX(eConstants.MOTOR_DRIVE_LEFT_FOLLOWER_B);
        m_RightMaster = new WPI_TalonSRX(eConstants.MOTOR_DRIVE_RIGHT_MASTER);
        m_RightFollowerA = new WPI_VictorSPX(eConstants.MOTOR_DRIVE_RIGHT_FOLLOWER_A);
        m_RightFollowerB = new WPI_VictorSPX(eConstants.MOTOR_DRIVE_RIGHT_FOLLOWER_B);
        
        m_RightMaster.setInverted(true);
        m_RightFollowerA.setInverted(true);
        m_RightFollowerB.setInverted(true);

        m_LeftFollowerA.follow(m_LeftMaster);
        m_LeftFollowerB.follow(m_LeftMaster);
        m_RightFollowerA.follow(m_RightMaster);
        m_RightFollowerB.follow(m_RightMaster);	
    }


    public void teleopArcadeDrive(){
        setRampRate(0.0);	
        if(Math.abs(m_controls.driver_YL_Axis()) > Math.abs(m_controls.driver_XL_Axis())) 
        {
            m_RightMaster.set(-(m_controls.driver_YL_Axis()/10*9) - (m_controls.driver_XL_Axis()/10*7.5));
            m_LeftMaster.set(-(m_controls.driver_YL_Axis()/10*9) + (m_controls.driver_XL_Axis()/10*7)); 
        } 
        else 
        {
            m_RightMaster.set(-(m_controls.driver_YL_Axis()/10*9) - (m_controls.driver_XL_Axis()/10*8.5));
            m_LeftMaster.set(-(m_controls.driver_YL_Axis()/10*9) + (m_controls.driver_XL_Axis()/10*8)); 
        }
    }

    public void setDriveAndSteer(double driveCmd, double steerCmd) {
        m_RightMaster.set(-driveCmd - steerCmd);
        m_LeftMaster.set(-driveCmd + steerCmd);
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

    public void Test(int Ta)
    {
        //System.out.println("Encoder: ");
        //System.out.println(m_RightMaster.getSelectedSensorPosition());
        switch(Ta)
        {
            case 1:
                m_RightMaster.set(-0.5);
                break;
            case 2:
                m_RightFollowerA.set(-0.25);
                break;
            case 3:
                m_RightFollowerB.set(-0.25);
                break;
            default:
                m_RightMaster.set(0);
                m_RightFollowerA.set(0);
                m_RightFollowerB.set(0);
            break;

        }
    }
}