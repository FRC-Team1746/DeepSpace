package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.constants.ElectricalConstants;

import com.ctre.phoenix.motorcontrol.can.*;

public class Ball{

	ElectricalConstants eConstants;

  private WPI_TalonSRX ballRight;
  private WPI_TalonSRX ballLeft;
  private WPI_TalonSRX overBumper;
  private DoubleSolenoid ballenoid1;


  private DigitalInput balls;

  public Ball(){
    eConstants = new ElectricalConstants();
		ballRight = new WPI_TalonSRX(eConstants.BALL_RIGHT);
    ballLeft = new WPI_TalonSRX(eConstants.BALL_LEFT);
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
    ballLeft.set(speed);
    ballRight.set(-speed);
    overBumper.set(speed);
  }

  public void intakeOut(Double speed){
    ballLeft.set(-speed);
    ballRight.set(speed);
    overBumper.set(-speed);
  }

  public boolean getSensor(){
    return balls.get();
  }
}