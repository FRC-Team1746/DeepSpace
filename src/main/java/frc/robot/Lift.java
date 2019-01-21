package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.lang.Math;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import com.ctre.phoenix.motorcontrol.ControlMode;
import java.util.concurrent.TimeUnit;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.*;

public class Lift {
	
	ElectricalConstants m_eConstants;
	Controls m_controls;
	Constants constants;
	
	private VictorSPX m_liftLeft;
	private WPI_TalonSRX m_liftRight;
	private double m_liftPosition;
	private boolean moving;
	
	private AnalogInput m_liftBottom;
	private DigitalOutput m_liftTestLED;
	private boolean m_UpDpadPrevious;
	private boolean m_DownDpadPrevious;
	
	StringBuilder _sb = new StringBuilder();
	
	public Lift(Controls controls) {
		m_controls = controls;
		m_eConstants =  new ElectricalConstants();
		constants = new Constants();
		m_liftLeft = new VictorSPX(m_eConstants.ELEVATOR_LEFT);
		m_liftRight = new WPI_TalonSRX(m_eConstants.ELEVATOR_RIGHT);
		m_liftBottom = new AnalogInput(m_eConstants.LIFT_BOTTOM);
		m_liftLeft.follow(m_liftRight);
		
		m_liftPosition = 13;
		moving = false;
		
		/* first choose the sensor */
		m_liftRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,constants.kPIDLoopIdx, constants.kTimeoutMs);
		m_liftRight.setSensorPhase(false);
		m_liftRight.setInverted(false);
		/* Set relevant frame periods to be at least as fast as periodic rate*/
		m_liftRight.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10,
		constants.kTimeoutMs);
		m_liftRight.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10,
		constants.kTimeoutMs);
		/* set the peak and nominal outputs */
		m_liftRight.configNominalOutputForward(0, constants.kTimeoutMs);
		m_liftRight.configNominalOutputReverse(0, constants.kTimeoutMs);
		m_liftRight.configPeakOutputForward(1, constants.kTimeoutMs);
		m_liftRight.configPeakOutputReverse(-1, constants.kTimeoutMs);
		/* set closed loop gains in slot0 - see documentation */
		m_liftRight.selectProfileSlot(constants.kSlotIdx, constants.kPIDLoopIdx);
		m_liftRight.config_kF(0, .146, Constants.kTimeoutMs);
		m_liftRight.config_kP(0, 2.5, Constants.kTimeoutMs);
		m_liftRight.config_kI(0, 0, Constants.kTimeoutMs);
		m_liftRight.config_kD(0, 25, Constants.kTimeoutMs);
		m_liftLeft.config_kF(0, .146, Constants.kTimeoutMs);
		m_liftLeft.config_kP(0, 2.5, Constants.kTimeoutMs);
		m_liftLeft.config_kI(0, 0, Constants.kTimeoutMs);
		m_liftLeft.config_kD(0, 25, Constants.kTimeoutMs);
		/* set acceleration and vcruise velocity - see documentation */
		m_liftRight.configMotionCruiseVelocity(500, constants.kTimeoutMs);
		m_liftRight.configMotionAcceleration(5000, constants.kTimeoutMs);
		/* zero the sensor */
		m_liftRight.setSelectedSensorPosition(0, constants.kPIDLoopIdx, constants.kTimeoutMs);
		m_liftRight.configOpenloopRamp(0, 0);
		m_liftRight.configClosedloopRamp(0, 0);

//		m_liftBottom = new DigitalInput(m_eConstants.LIFT_BOTTOM);
//		m_liftTestLED = new DigitalOutput(m_eConstants.LIFT_LED);

		m_UpDpadPrevious = false;
		m_DownDpadPrevious = false;
		
		resetEncoder();
	}
	
	
	
	public void resetEncoder(){
//		m_liftEncoder.reset();
		m_liftRight.setSelectedSensorPosition(0, 0, 10);
	}
	
	public void setEncoderToStart(){
		m_liftRight.setSelectedSensorPosition((int) constants.liftAutonStartPosition, 0, 10);
		m_liftPosition = constants.liftAutonStartPosition;
	}
	
	public void setRampRate(double rate){
		m_liftRight.configOpenloopRamp(rate, 5);
		m_liftLeft.configOpenloopRamp(rate, 5);
		
	}
	
	
	public void setBrakeMode(boolean brake) {
		if (brake){
			m_liftRight.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake); 
			m_liftLeft.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake); 
		}
	}
	public void setCoast(boolean coast){
		if (coast) {
			m_liftRight.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
			m_liftLeft.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
		}
	}
	
	public void update() {
		
			if ((m_liftBottom.getVoltage() >= 0.7) && !m_controls.driver_A_Button() && !m_controls.driver_X_Button() && !m_controls.driver_B_Button() && !m_controls.driver_Y_Button() && !(m_controls.driver_YR_Axis() > .15 || m_controls.driver_YR_Axis() < -.15)) {
				m_liftRight.set(0);
			}else /*if (!m_retractor.checkPosition() || (m_retractor.checkPosition() && (getLiftPosition() >= constants.liftEncoderPosition2 || getLiftPosition() <= constants.liftEncoderPosition1) )) */{
				if (m_controls.driver_YR_Axis() > .15 || m_controls.driver_YR_Axis() < -.15) {
//						m_liftRight.set(ControlMode.PercentOutput, -m_controls.oper_YR_Axis()/2);
						m_liftRight.configMotionCruiseVelocity((int) (6500+(Math.abs(m_controls.driver_YR_Axis()*1500))), constants.kTimeoutMs);
						m_liftPosition = getLiftPosition() - m_controls.driver_YR_Axis()*2.5*constants.liftEncoderPerInch;
				} else {
					if (m_controls.driver_Y_Button()) {
						m_liftRight.configMotionCruiseVelocity(9000, constants.kTimeoutMs);
						m_liftPosition = constants.liftEncoderPosition4;
						System.out.println("Y Pressed");
					}else if (m_controls.driver_B_Button()) {
						m_liftRight.configMotionCruiseVelocity(9000, constants.kTimeoutMs);
						m_liftPosition = constants.liftEncoderPosition2;
						System.out.println("B Pressed");
					}else if (m_controls.driver_X_Button()) {
						m_liftRight.configMotionCruiseVelocity(9000, constants.kTimeoutMs);
						m_liftPosition = constants.liftEncoderPosition1;
						System.out.println("X Pressed");
					}else if (m_controls.driver_A_Button()) {
						if (m_liftBottom.getVoltage() >= 0.7) {
							m_liftRight.configMotionCruiseVelocity(0, constants.kTimeoutMs);
						} else {
						m_liftRight.configMotionCruiseVelocity(7000, constants.kTimeoutMs);
						m_liftPosition = constants.liftEncoderPosition0;
						}
						System.out.println("A Pressed");
					}
					
			}
				
				m_liftRight.set(ControlMode.MotionMagic, m_liftPosition);
		/*}else {
			m_liftRight.set(0); 
			m_liftPosition = getLiftPosition();*/
		}
			
		if ((m_liftBottom.getVoltage() >= 0.7)) {
			resetEncoder();
			m_liftPosition = 0;
		}
		
		System.out.println(m_liftRight.getSelectedSensorVelocity(0));
			
	}
	
	public boolean updatePosition(int position) {
		if (!moving) {
			if (position == 3) {
				m_liftRight.configMotionCruiseVelocity(6000, constants.kTimeoutMs);
				m_liftPosition = constants.liftEncoderPosition3;
			}else if (position == 1) {
				m_liftRight.configMotionCruiseVelocity(6000, constants.kTimeoutMs);
				m_liftPosition = constants.liftEncoderPosition1;
			}else if (position == 4) {
				m_liftRight.configMotionCruiseVelocity(6000, constants.kTimeoutMs);
				m_liftPosition = constants.liftEncoderPosition4;
			}else if (position == 0) {
				if (m_liftBottom.getVoltage() >= 0.7) {
					m_liftRight.configMotionCruiseVelocity(0, constants.kTimeoutMs);
				} else {
				m_liftRight.configMotionCruiseVelocity(4000, constants.kTimeoutMs);
				m_liftPosition = constants.liftEncoderPosition0;
				}
			}
			moving = true;
			return false;
		}else {	
			m_liftRight.set(ControlMode.MotionMagic, m_liftPosition);
			}
		if ( Math.abs(getLiftPosition()-m_liftPosition) <= constants.liftEncoderTolerance) {
			moving = false;
			return true;
		}else {
			return false;
		}		
	}
	
	public double getLiftPosition(){
		return m_liftRight.getSelectedSensorPosition(constants.kPIDLoopIdx);
	}
	
	public void updateSmartDashboard(){
		SmartDashboard.putNumber("Lift position", getLiftPosition());
		SmartDashboard.putNumber("Intended", m_liftPosition);
		SmartDashboard.putNumber("Bottom Value: ", m_liftBottom.getVoltage());		
		SmartDashboard.putBoolean("Bottom Sensor: ", (m_liftBottom.getVoltage() >= 0.7));
	}
	
}