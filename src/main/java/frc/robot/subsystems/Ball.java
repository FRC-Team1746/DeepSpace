package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.constants.ElectricalConstants;

import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.ControlMode;


public class Ball{

	ElectricalConstants eConstants;

  private VictorSPX ballRight;
  private VictorSPX ballLeft;
  private WPI_TalonSRX overBumper;
  private Solenoid ballenoid;
  private AnalogInput balls;

  private boolean stalling;

  public Ball(){
    eConstants = new ElectricalConstants();
		ballRight = new VictorSPX(eConstants.BALL_RIGHT);
    ballLeft = new VictorSPX(eConstants.BALL_LEFT);
    overBumper = new WPI_TalonSRX(eConstants.OVER_BUMPER);
    balls = new AnalogInput(eConstants.BALLS);
    ballenoid = new Solenoid(eConstants.BALLENOID); //NO BALLENOID!
  }

  public void armUp(){
    ballenoid.set(false);
  }

  public void armDown(){
    ballenoid.set(true);
  }

  public void intakeControl(double control)
  {
    if (Math.abs(control) > 0.2)
    {
      stalling = false;
      ballLeft.set(ControlMode.PercentOutput, -control/4*3);
      ballRight.set(ControlMode.PercentOutput, control/4*3);
      overBumper.set(ControlMode.PercentOutput, -control/4*3);
    }
    else if(balls.getVoltage() < 1.33){
      stalling = true;
      ballLeft.set(ControlMode.PercentOutput, 0.1);
      ballRight.set(ControlMode.PercentOutput, 0.1);
      overBumper.set(ControlMode.PercentOutput, 0);
    }
    else
    {
      stalling = false;
      ballLeft.set(ControlMode.PercentOutput, 0);
      ballRight.set(ControlMode.PercentOutput, 0);
      overBumper.set(ControlMode.PercentOutput, 0);
    } 
  }

  public double getSensor(){
    return balls.getVoltage();
  }

  public boolean isStalling() {
    return stalling;
  }
  
}