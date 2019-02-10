package frc.robot.subsystems;

import java.lang.Math;
import edu.wpi.first.wpilibj.DigitalInput;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.*;
import frc.robot.constants.ElectricalConstants;
import frc.robot.constants.Controls;
import frc.robot.constants.Constants;

public class Lift{

	ElectricalConstants eConstants;
	Controls controls;
	

	private VictorSPX liftLeft;
	private WPI_TalonSRX liftRight;
	private double liftPosition;
	private boolean moving;

	private DigitalInput liftBottom;
  private DigitalInput liftTop;
  private DigitalInput hatchs;
  private DigitalInput balls;

	public Lift(Controls Controls) {
    controls = Controls;
		eConstants = new ElectricalConstants();
		liftLeft = new VictorSPX(eConstants.ELEVATOR_LEFT);
		liftRight = new WPI_TalonSRX(eConstants.ELEVATOR_RIGHT);
		liftBottom = new DigitalInput(eConstants.LIFT_BOTTOM);
    liftTop = new DigitalInput(eConstants.LIFT_TOP);
    hatchs = new DigitalInput(eConstants.HATCH);
    balls = new DigitalInput(eConstants.BALLS);

    liftLeft.follow(liftRight);

    /* first choose the sensor */
		liftRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx,
    Constants.kTimeoutMs);
    liftRight.setSensorPhase(false);
    liftRight.setInverted(false);
    /* Set relevant frame periods to be at least as fast as periodic rate */
    liftRight.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
    liftRight.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);
    /* set the peak and nominal outputs */
    liftRight.configNominalOutputForward(0, Constants.kTimeoutMs);
    liftRight.configNominalOutputReverse(0, Constants.kTimeoutMs);
    liftRight.configPeakOutputForward(1, Constants.kTimeoutMs);
    liftRight.configPeakOutputReverse(-1, Constants.kTimeoutMs);
    /* set closed loop gains in slot0 - see documentation */
    liftRight.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
    liftRight.config_kF(0, .146, Constants.kTimeoutMs);
    liftRight.config_kP(0, 2.5, Constants.kTimeoutMs);
    liftRight.config_kI(0, 0, Constants.kTimeoutMs);
    liftRight.config_kD(0, 25, Constants.kTimeoutMs);
    liftLeft.config_kF(0, .146, Constants.kTimeoutMs);
    liftLeft.config_kP(0, 2.5, Constants.kTimeoutMs);
    liftLeft.config_kI(0, 0, Constants.kTimeoutMs);
    liftLeft.config_kD(0, 25, Constants.kTimeoutMs);
    /* set acceleration and vcruise velocity - see documentation */
    liftRight.configMotionCruiseVelocity(500, Constants.kTimeoutMs);
    liftRight.configMotionAcceleration(5000, Constants.kTimeoutMs);
    /* zero the sensor */
    liftRight.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
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
	}

	public void setBrakeMode(boolean brake) {
		if (brake) {
			liftRight.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
			liftLeft.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		}
	}

	public void setCoast(boolean coast) {
		if (coast) {
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
      }else if(hatchs.get()){
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
      }else if(balls.get()){
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
			resetEncoder();
			liftPosition = 0;
		}

  }

  public double getLiftPosition() {
		return liftRight.getSelectedSensorPosition(Constants.kPIDLoopIdx);
  }
  

}
