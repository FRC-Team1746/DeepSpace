package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.constants.ElectricalConstants;

import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.ControlMode;


public class Ball{

	ElectricalConstants eConstants;

  private VictorSPX ballRight;
  private VictorSPX ballLeft;
  private WPI_TalonSRX overBumper;
  private DoubleSolenoid ballenoid1;


  private DigitalInput balls;

  public Ball(){
    eConstants = new ElectricalConstants();
		ballRight = new VictorSPX(eConstants.BALL_RIGHT);
    ballLeft = new VictorSPX(eConstants.BALL_LEFT);
    overBumper = new WPI_TalonSRX(eConstants.OVER_BUMPER);
    balls = new DigitalInput(eConstants.BALLS);
    ballenoid1 = new DoubleSolenoid(eConstants.BALLENOID11, eConstants.BALLENOID12 );

    ballenoid1.set(DoubleSolenoid.Value.kOff);

  }

  public void armUp(){
    ballenoid1.set(DoubleSolenoid.Value.kForward);
  }

  public void armDown(){
    ballenoid1.set(DoubleSolenoid.Value.kReverse);
  }

  public void intakeIn(Double speed){
    ballLeft.set(ControlMode.PercentOutput, speed/3*4);
    ballRight.set(ControlMode.PercentOutput, -speed/3*4);
    overBumper.set(ControlMode.PercentOutput, speed/3*4);
  }

  public void intakeOut(Double speed){
    ballLeft.set(ControlMode.PercentOutput, -speed/3*4);
    ballRight.set(ControlMode.PercentOutput, speed/3*4);
    overBumper.set(-speed/3*4);
  }

  public boolean getSensor(){
    return balls.get();
  }
}