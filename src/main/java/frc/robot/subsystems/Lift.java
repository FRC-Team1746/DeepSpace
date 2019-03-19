package frc.robot.subsystems;

import java.lang.Math;

import edu.wpi.first.wpilibj.AnalogInput;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.*;
import frc.robot.constants.ElectricalConstants;
import frc.robot.constants.Controls;
import frc.robot.constants.Constants;

public class Lift{

	ElectricalConstants eConstants;
	Controls controls;
  Constants constants;
  Ball ball;
  Hatch hatch;

	private VictorSPX liftLeft;
	private WPI_TalonSRX liftRight;
	private double liftPosition;
	private boolean moving;

  private AnalogInput liftBottom;

  private double feedFoward;


  public Lift(Controls Controls, Ball Ball, Hatch Hatch) 
  {
    
		eConstants =  new ElectricalConstants();
    constants = new Constants();
		liftLeft = new VictorSPX(eConstants.ELEVATOR_VICTOR);
		liftRight = new WPI_TalonSRX(eConstants.ELEVATOR_TALON); // Positive = Up
    liftBottom = new AnalogInput(eConstants.LIFT_BOTTOM); // Positive = Up
    controls = Controls; 
    ball = Ball;
    hatch = Hatch;
    feedFoward = 0.1;

    liftLeft.follow(liftRight);
    /* first choose the sensor */
		liftRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, constants.kPIDLoopIdx,
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
    // liftRight.config_kF(0, .146, constants.kTimeoutMs);
    // liftRight.config_kP(0, 2.5, constants.kTimeoutMs);
    // liftRight.config_kI(0, 0, constants.kTimeoutMs);
    // liftRight.config_kD(0, 25, constants.kTimeoutMs);
    /* set acceleration and vcruise velocity - see documentation */
    liftRight.configMotionCruiseVelocity(500, constants.kTimeoutMs);
    liftRight.configMotionAcceleration(2000, constants.kTimeoutMs);
    /* zero the sensor */
    liftRight.setSelectedSensorPosition(0, constants.kPIDLoopIdx, constants.kTimeoutMs);
    liftRight.configOpenloopRamp(0, 0);
    liftRight.configClosedloopRamp(0, 0);


    //#endregion
    resetEncoder();
  }

  public void resetEncoder() 
  {
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
    
  public void update()
  {
    if(!controls.driver_A_Button() && !controls.driver_X_Button() && !controls.driver_B_Button())
    {
      System.out.println("Manual Case/DeAct");
      if(controls.driver_YR_Axis() > .15 || controls.driver_YR_Axis() < -.15)
      {
        System.out.println("CASE HIT");
        liftRight.configMotionCruiseVelocity(1000,Constants.kTimeoutMs);
        liftPosition = getLiftPosition() - controls.driver_YR_Axis() * 2.5 * Constants.liftEncoderPerInch;
      }
      else
      {
        liftRight.set(0);
      }
    }
    else if(ball.getSensor()>=1.3)
    {
      if(controls.driver_A_Button())
      {
        if (liftBottom.getVoltage()<1.33) 
        {
          liftRight.configMotionCruiseVelocity(0, Constants.kTimeoutMs);
        } 
        else if(getLiftPosition() <= Constants.liftEncoderPosition0)
        {
          liftRight.configMotionCruiseVelocity(0, Constants.kTimeoutMs);
          liftPosition = 0;
        }
        else 
        {
          liftRight.configMotionCruiseVelocity(132, Constants.kTimeoutMs);
          liftPosition = Constants.liftEncoderPosition0;
        }
        System.out.println("A Pressed");
      }
      else if(controls.driver_X_Button())
      {
			  liftRight.configMotionCruiseVelocity(1000, Constants.kTimeoutMs);
				liftPosition = Constants.hatchPosition2;
				System.out.println("X Pressed");
      }
      else if(controls.driver_Y_Button())
      {
				liftRight.configMotionCruiseVelocity(1000, Constants.kTimeoutMs);
				liftPosition = Constants.hatchPosition3;
				System.out.println("Y Pressed");
      }
    }
    else if(ball.getSensor() < 1.3)
    {
      System.out.println("Ball Case");
      // if(controls.driver_X_Button()){
			// 	liftRight.configMotionCruiseVelocity(1000, Constants.kTimeoutMs);
			// 	liftPosition = Constants.ballPosition1;
			// 	System.out.println("A Pressed");
      // }
      if(controls.driver_X_Button())
      {
				liftRight.configMotionCruiseVelocity(1000, Constants.kTimeoutMs);
				liftPosition = Constants.ballPosition2;
				System.out.println("X Pressed");
      }
      else if(controls.driver_Y_Button())
      {
			  liftRight.configMotionCruiseVelocity(1000, Constants.kTimeoutMs);
				liftPosition = Constants.ballPosition3;
				System.out.println("Y Pressed");
      }
      else if(controls.driver_A_Button())
      {
        if (liftBottom.getVoltage()<1.33) 
        {
          liftRight.configMotionCruiseVelocity(0, Constants.kTimeoutMs);
        } 
        else if(getLiftPosition() <= Constants.liftEncoderPosition0)
        {
          liftRight.configMotionCruiseVelocity(0, Constants.kTimeoutMs);
          liftPosition = 0;
        }
        else 
        {
          liftRight.configMotionCruiseVelocity(132, Constants.kTimeoutMs);
          liftPosition = Constants.liftEncoderPosition0;
        }
        System.out.println("A Pressed");
      }
    }
    else if (liftBottom.getVoltage()<1.33) 
    {
      resetEncoder();
      liftPosition = 0;
    }
    liftRight.set(ControlMode.MotionMagic, liftPosition);
    }

  public void test() {
    if(controls.driver_YR_Axis() > .15 || controls.driver_YR_Axis() < -.15)
    {
    // Comp Bot:  liftRight.set(ControlMode.PercentOutput, -controls.driver_YR_Axis()/10*5);
    liftRight.set(ControlMode.PercentOutput, controls.driver_YR_Axis()/10*5);
    }
    else
    {
      liftRight.set(ControlMode.PercentOutput, 0);
    }
  }

  public double getLiftPosition() 
  {
		return liftRight.getSelectedSensorPosition();
  }

  public double getSensor(){
    return liftBottom.getVoltage();
  }

  public void setElevator(double ticks)
  {
    double error = ticks - this.getLiftPosition();
    //double deltaError;

  }

  

}
