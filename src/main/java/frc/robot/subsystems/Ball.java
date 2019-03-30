package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
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
  private DigitalInput balls;

  private int ballSenseCounter = 0;
  private boolean haveBall = false;

  private boolean stalling;
  

  public Ball(){
    eConstants = new ElectricalConstants();
		ballRight = new VictorSPX(eConstants.BALL_RIGHT);
    ballLeft = new VictorSPX(eConstants.BALL_LEFT);
    overBumper = new WPI_TalonSRX(eConstants.OVER_BUMPER);
    balls = new DigitalInput(eConstants.BALLS);
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
      ballLeft.set(ControlMode.PercentOutput, control); 
      ballRight.set(ControlMode.PercentOutput, -control);//left //neg pract
      overBumper.set(ControlMode.PercentOutput, control/4*3); //neg pract
    }
    else if(haveBall()){
      stalling = true;
      ballLeft.set(ControlMode.PercentOutput, 0.07);
      ballRight.set(ControlMode.PercentOutput, -0.07); //neg pract
      overBumper.set(ControlMode.PercentOutput, 0); // as of 3/24/2019 the practice robot has the right ball intake wired to what the over the bumber is supposed to be connected to (the talon) thus they are reversed --see next-->
    }          // after wireing is fixed set over the bumber stalling to 0
                
    else
    {
      stalling = false;
      ballLeft.set(ControlMode.PercentOutput, 0);
      ballRight.set(ControlMode.PercentOutput, 0);
      overBumper.set(ControlMode.PercentOutput, 0);
    } 
  }

  public void testBall () {
    overBumper.set(ControlMode.PercentOutput, 0.25);
  }

  public boolean haveBall(){
    return !balls.get();
  }

  public boolean isStalling() {
    return stalling;
  }

}