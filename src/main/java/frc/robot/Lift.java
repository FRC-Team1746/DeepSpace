package frc.robot;

import java.lang.Math;
<<<<<<< HEAD
import edu.wpi.first.wpilibj.DigitalInput;
=======
// import edu.wpi.first.wpilibj.VictorSP;
// import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.AnalogInput;
// import edu.wpi.first.wpilibj.DigitalInput;
// import edu.wpi.first.wpilibj.DigitalOutput;
>>>>>>> parent of d321df3... Limit Switch
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.*;
public class Lift{

	ElectricalConstants eConstants;
	Controls controls;
	Constants constants;

	private VictorSPX m_liftLeft;
	private WPI_TalonSRX m_liftRight;
	private double m_liftPosition;
	private boolean moving;

<<<<<<< HEAD
	private DigitalInput liftBottom;
  private DigitalInput liftTop;
  private DigitalInput hatch;
  private DigitalInput ball;

	public Lift(Controls Controls) {
    controls = Controls;
		eConstants = new ElectricalConstants();
		constants = new Constants();
		liftLeft = new VictorSPX(eConstants.ELEVATOR_LEFT);
		liftRight = new WPI_TalonSRX(eConstants.ELEVATOR_RIGHT);
		liftBottom = new DigitalInput(eConstants.LIFT_BOTTOM);
    liftTop = new DigitalInput(eConstants.LIFT_TOP);
    hatch = new DigitalInput(eConstants.HATCH);
    ball = new DigitalInput(eConstants.BALL);

    liftLeft.follow(liftRight);

    /* first choose the sensor */
		liftRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, constants.kPIDLoopIdx,
    constants.kTimeoutMs);
    liftRight.setSensorPhase(false);
    liftRight.setInverted(false);
    /* Set relevant frame periods to be at least as fast as periodic rate */
    liftRight.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, constants.kTimeoutMs);
    liftRight.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, constants.kTimeoutMs);
    /* set the peak and nominal outputs */
    liftRight.configNominalOutputForward(0, constants.kTimeoutMs);
    liftRight.configNominalOutputReverse(0, constants.kTimeoutMs);
    liftRight.configPeakOutputForward(1, constants.kTimeoutMs);
    liftRight.configPeakOutputReverse(-1, constants.kTimeoutMs);
    /* set closed loop gains in slot0 - see documentation */
    liftRight.selectProfileSlot(constants.kSlotIdx, constants.kPIDLoopIdx);
    liftRight.config_kF(0, .146, constants.kTimeoutMs);
    liftRight.config_kP(0, 2.5, constants.kTimeoutMs);
    liftRight.config_kI(0, 0, constants.kTimeoutMs);
    liftRight.config_kD(0, 25, constants.kTimeoutMs);
    liftLeft.config_kF(0, .146, constants.kTimeoutMs);
    liftLeft.config_kP(0, 2.5, constants.kTimeoutMs);
    liftLeft.config_kI(0, 0, constants.kTimeoutMs);
    liftLeft.config_kD(0, 25, constants.kTimeoutMs);
    /* set acceleration and vcruise velocity - see documentation */
    liftRight.configMotionCruiseVelocity(500, constants.kTimeoutMs);
    liftRight.configMotionAcceleration(5000, constants.kTimeoutMs);
    /* zero the sensor */
    liftRight.setSelectedSensorPosition(0, constants.kPIDLoopIdx, constants.kTimeoutMs);
    liftRight.configOpenloopRamp(0, 0);
    liftRight.configClosedloopRamp(0, 0);

    resetEncoder();
  }

	public void resetEncoder() {
		liftRight.setSelectedSensorPosition(0, 0, 10);
	}

	public void setRampRate(double rate) {
		liftRight.configOpenloopRamp(rate, 5);
		liftLeft.configOpenloopRamp(rate, 5);
=======
	private AnalogInput m_liftBottom;
	private boolean m_UpDpadPrevious;
	private boolean m_DownDpadPrevious;

	StringBuilder _sb = new StringBuilder();

	public Lift(Controls controls) {
		m_controls = controls;
		m_eConstants = new ElectricalConstants();
		Constants = new Constants();
		m_liftLeft = new VictorSPX(m_eConstants.ELEVATOR_LEFT);
		m_liftRight = new WPI_TalonSRX(m_eConstants.ELEVATOR_RIGHT);
		m_liftBottom = new AnalogInput(m_eConstants.LIFT_BOTTOM);
		m_liftLeft.follow(m_liftRight);

		m_liftPosition = 13;
		moving = false;

		/* first choose the sensor */
		m_liftRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx,
				Constants.kTimeoutMs);
		m_liftRight.setSensorPhase(false);
		m_liftRight.setInverted(false);
		/* Set relevant frame periods to be at least as fast as periodic rate */
		m_liftRight.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
		m_liftRight.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);
		/* set the peak and nominal outputs */
		m_liftRight.configNominalOutputForward(0, Constants.kTimeoutMs);
		m_liftRight.configNominalOutputReverse(0, Constants.kTimeoutMs);
		m_liftRight.configPeakOutputForward(1, Constants.kTimeoutMs);
		m_liftRight.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		/* set closed loop gains in slot0 - see documentation */
		m_liftRight.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
		m_liftRight.config_kF(0, .146, Constants.kTimeoutMs);
		m_liftRight.config_kP(0, 2.5, Constants.kTimeoutMs);
		m_liftRight.config_kI(0, 0, Constants.kTimeoutMs);
		m_liftRight.config_kD(0, 25, Constants.kTimeoutMs);
		m_liftLeft.config_kF(0, .146, Constants.kTimeoutMs);
		m_liftLeft.config_kP(0, 2.5, Constants.kTimeoutMs);
		m_liftLeft.config_kI(0, 0, Constants.kTimeoutMs);
		m_liftLeft.config_kD(0, 25, Constants.kTimeoutMs);
		/* set acceleration and vcruise velocity - see documentation */
		m_liftRight.configMotionCruiseVelocity(500, Constants.kTimeoutMs);
		m_liftRight.configMotionAcceleration(5000, Constants.kTimeoutMs);
		/* zero the sensor */
		m_liftRight.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		m_liftRight.configOpenloopRamp(0, 0);
		m_liftRight.configClosedloopRamp(0, 0);

		// m_liftBottom = new DigitalInput(m_eConstants.LIFT_BOTTOM);
		// m_liftTestLED = new DigitalOutput(m_eConstants.LIFT_LED);

		m_UpDpadPrevious = false;
		m_DownDpadPrevious = false;

		resetEncoder();
	}

	public void resetEncoder() {
		// m_liftEncoder.reset();
		m_liftRight.setSelectedSensorPosition(0, 0, 10);
	}

	public void setEncoderToStart() {
		m_liftRight.setSelectedSensorPosition((int) Constants.liftAutonStartPosition, 0, 10);
		m_liftPosition = Constants.liftAutonStartPosition;
	}

	public void setRampRate(double rate) {
		m_liftRight.configOpenloopRamp(rate, 5);
		m_liftLeft.configOpenloopRamp(rate, 5);

>>>>>>> parent of d321df3... Limit Switch
	}

	public void setBrakeMode(boolean brake) {
		if (brake) {
			m_liftRight.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
			m_liftLeft.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		}
	}

	public void setCoast(boolean coast) {
		if (coast) {
<<<<<<< HEAD
			liftRight.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
			liftLeft.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
    }
  }
    
  public void update(){
    if(liftBottom.get() && !controls.driver_A_Button() && !controls.driver_X_Button() && !controls.driver_B_Button()){
      liftRight.set(0);
    }else if(liftTop.get()){
      liftRight.set(0);
    }else{
      if(controls.driver_YR_Axis() > .15 || controls.driver_YR_Axis() < -.15){
				liftRight.configMotionCruiseVelocity((int) (6500 + (Math.abs(controls.driver_YR_Axis() * 1500))),
						Constants.kTimeoutMs);
				liftPosition = getLiftPosition() - controls.driver_YR_Axis() * 2.5 * Constants.liftEncoderPerInch;
      }else if(hatch.get()){
        if(controls.driver_A_Button()){
					liftRight.configMotionCruiseVelocity(9000, Constants.kTimeoutMs);
					liftPosition = Constants.hatchPosition1;
					System.out.println("A Pressed");
        }else if(controls.driver_X_Button()){
					liftRight.configMotionCruiseVelocity(9000, Constants.kTimeoutMs);
					liftPosition = Constants.hatchPosition2;
					System.out.println("X Pressed");
        }else if(controls.driver_Y_Button()){
					liftRight.configMotionCruiseVelocity(9000, Constants.kTimeoutMs);
					liftPosition = Constants.hatchPosition2;
					System.out.println("Y Pressed");
        }
      }else if(ball.get()){
        if(controls.driver_A_Button()){
					liftRight.configMotionCruiseVelocity(9000, Constants.kTimeoutMs);
					liftPosition = Constants.ballPosition1;
					System.out.println("A Pressed");
        }else if(controls.driver_X_Button()){
					liftRight.configMotionCruiseVelocity(9000, Constants.kTimeoutMs);
					liftPosition = Constants.ballPosition2;
					System.out.println("X Pressed");
        }else if(controls.driver_Y_Button()){
					liftRight.configMotionCruiseVelocity(9000, Constants.kTimeoutMs);
					liftPosition = Constants.ballPosition3;
					System.out.println("Y Pressed");
        }
      }else if(controls.driver_A_Button()){
        if (liftBottom.get()) {
          liftRight.configMotionCruiseVelocity(0, Constants.kTimeoutMs);
        } else {
          liftRight.configMotionCruiseVelocity(7000, Constants.kTimeoutMs);
          liftPosition = Constants.liftEncoderPosition0;
        }
        System.out.println("A Pressed");
      }
    }

    if (liftBottom.get()) {
=======
			m_liftRight.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
			m_liftLeft.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
		}
	}

	public void update() {

		if ((m_liftBottom.getVoltage() >= 0.7) && !m_controls.driver_A_Button() && !m_controls.driver_X_Button()
				&& !m_controls.driver_B_Button() && !m_controls.driver_Y_Button()
				&& !(m_controls.driver_YR_Axis() > .15 || m_controls.driver_YR_Axis() < -.15)) {
			m_liftRight.set(0);
		} else /*
				 * if (!m_retractor.checkPosition() || (m_retractor.checkPosition() &&
				 * (getLiftPosition() >= Constants.liftEncoderPosition2 || getLiftPosition() <=
				 * Constants.liftEncoderPosition1) ))
				 */ {
			if (m_controls.driver_YR_Axis() > .15 || m_controls.driver_YR_Axis() < -.15) {
				// m_liftRight.set(ControlMode.PercentOutput, -m_controls.oper_YR_Axis()/2);
				m_liftRight.configMotionCruiseVelocity((int) (6500 + (Math.abs(m_controls.driver_YR_Axis() * 1500))),
						Constants.kTimeoutMs);
				m_liftPosition = getLiftPosition() - m_controls.driver_YR_Axis() * 2.5 * Constants.liftEncoderPerInch;
			} else {
				if (m_controls.driver_Y_Button()) {
					m_liftRight.configMotionCruiseVelocity(9000, Constants.kTimeoutMs);
					m_liftPosition = Constants.liftEncoderPosition4;
					System.out.println("Y Pressed");
				} else if (m_controls.driver_B_Button()) {
					m_liftRight.configMotionCruiseVelocity(9000, Constants.kTimeoutMs);
					m_liftPosition = Constants.liftEncoderPosition2;
					System.out.println("B Pressed");
				} else if (m_controls.driver_X_Button()) {
					m_liftRight.configMotionCruiseVelocity(9000, Constants.kTimeoutMs);
					m_liftPosition = Constants.liftEncoderPosition1;
					System.out.println("X Pressed");
				} else if (m_controls.driver_A_Button()) {
					if (m_liftBottom.getVoltage() >= 0.7) {
						m_liftRight.configMotionCruiseVelocity(0, Constants.kTimeoutMs);
					} else {
						m_liftRight.configMotionCruiseVelocity(7000, Constants.kTimeoutMs);
						m_liftPosition = Constants.liftEncoderPosition0;
					}
					System.out.println("A Pressed");
				}

			}

			m_liftRight.set(ControlMode.MotionMagic, m_liftPosition);
			/*
			 * }else { m_liftRight.set(0); m_liftPosition = getLiftPosition();
			 */
		}

		if ((m_liftBottom.getVoltage() >= 0.7)) {
>>>>>>> parent of d321df3... Limit Switch
			resetEncoder();
			m_liftPosition = 0;
		}

<<<<<<< HEAD
  }

  public double getLiftPosition() {
		return liftRight.getSelectedSensorPosition(Constants.kPIDLoopIdx);
  }
  
=======
		System.out.println(m_liftRight.getSelectedSensorVelocity(0));

	}

	public boolean updatePosition(int position) {
		if (!moving) {
			if (position == 3) {
				m_liftRight.configMotionCruiseVelocity(6000, Constants.kTimeoutMs);
				m_liftPosition = Constants.liftEncoderPosition3;
			} else if (position == 1) {
				m_liftRight.configMotionCruiseVelocity(6000, Constants.kTimeoutMs);
				m_liftPosition = Constants.liftEncoderPosition1;
			} else if (position == 4) {
				m_liftRight.configMotionCruiseVelocity(6000, Constants.kTimeoutMs);
				m_liftPosition = Constants.liftEncoderPosition4;
			} else if (position == 0) {
				if (m_liftBottom.getVoltage() >= 0.7) {
					m_liftRight.configMotionCruiseVelocity(0, Constants.kTimeoutMs);
				} else {
					m_liftRight.configMotionCruiseVelocity(4000, Constants.kTimeoutMs);
					m_liftPosition = Constants.liftEncoderPosition0;
				}
			}
			moving = true;
			return false;
		} else {
			m_liftRight.set(ControlMode.MotionMagic, m_liftPosition);
		}
		if (Math.abs(getLiftPosition() - m_liftPosition) <= Constants.liftEncoderTolerance) {
			moving = false;
			return true;
		} else {
			return false;
		}
	}

	public double getLiftPosition() {
		return m_liftRight.getSelectedSensorPosition(Constants.kPIDLoopIdx);
	}

	public static final int kTimeoutMs = 10;

	public void updateSmartDashboard() {
		SmartDashboard.putNumber("Lift position", getLiftPosition());
		SmartDashboard.putNumber("Intended", m_liftPosition);
		SmartDashboard.putNumber("Bottom Value: ", m_liftBottom.getVoltage());
		SmartDashboard.putBoolean("Bottom Sensor: ", (m_liftBottom.getVoltage() >= 0.7));
	}
>>>>>>> parent of d321df3... Limit Switch

}
