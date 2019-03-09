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

  public Ball(){
    eConstants = new ElectricalConstants();
		ballRight = new VictorSPX(eConstants.BALL_RIGHT);
    ballLeft = new VictorSPX(eConstants.BALL_LEFT);
    overBumper = new WPI_TalonSRX(eConstants.OVER_BUMPER);
    balls = new DigitalInput(eConstants.BALLS);
    ballenoid = new Solenoid(eConstants.BALLENOID);

  }

  public void armUp(){
    ballenoid.set(false);
  }

  public void armDown(){
    ballenoid.set(true);





















































  }

  public void intakeIn(double controller){
    if(controller > .1){
      ballLeft.set(ControlMode.PercentOutput, -controller/3*4);
      ballRight.set(ControlMode.PercentOutput, controller/3*4);
      overBumper.set(ControlMode.PercentOutput, controller/3*4);
    }else { //Stops
			ballLeft.set(ControlMode.PercentOutput, 0);
			ballRight.set(ControlMode.PercentOutput, 0);
      overBumper.set(ControlMode.PercentOutput, 0);;
		}
  }

  public void intakeOut(double controller){
    if(controller > .1){
      ballLeft.set(ControlMode.PercentOutput, -controller/3*4);
      ballRight.set(ControlMode.PercentOutput, controller/3*4);
    }else { //Stops
			ballLeft.set(ControlMode.PercentOutput, 0);
			ballRight.set(ControlMode.PercentOutput, 0);
		}
  }

  public void intakeControl(double control)
  {
    if (Math.abs(control) > 0.2)
    {
      ballLeft.set(ControlMode.PercentOutput, -control/3*4);
      ballRight.set(ControlMode.PercentOutput, control/3*4);
      overBumper.set(ControlMode.PercentOutput, -control/3*4);
      if(balls.get())
      {
        ballLeft.set(ControlMode.PercentOutput, -control/3*4);
        ballRight.set(ControlMode.PercentOutput, control/3*4);
      }
    }
    else
    {
      ballLeft.set(ControlMode.PercentOutput, 0);
      ballRight.set(ControlMode.PercentOutput, 0);
      overBumper.set(ControlMode.PercentOutput, 0);
    }
    
  }

  public boolean getSensor(){
    return balls.get();
  }
}