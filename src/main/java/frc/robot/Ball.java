package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import com.ctre.phoenix.motorcontrol.can.*;


public class Ball{

	ElectricalConstants eConstants;

  private double liftPosition;
  private WPI_TalonSRX ballRight;
  private WPI_TalonSRX ballLeft;


  private DigitalInput ball;

  public Ball(){
    eConstants = new ElectricalConstants();
		ballRight = new WPI_TalonSRX(eConstants.BALL_RIGHT);
    ballLeft = new WPI_TalonSRX(eConstants.BALL_LEFT);
    ball = new DigitalInput(eConstants.BALL);
    DoubleSolenoid balleniod1 = new DoubleSolenoid(eConstants.BALLENIOD11, eConstants.BALLENIOD12 );
    DoubleSolenoid balleniod2 = new DoubleSolenoid(eConstants.BALLENIOD21, eConstants.BALLENIOD22 );

    balleniod1.set(DoubleSolenoid.Value.kOff);
    balleniod2.set(DoubleSolenoid.Value.kOff);

  }

  public void armUp(DoubleSolenoid balleniod1, DoubleSolenoid balleniod2){
    balleniod1.set(DoubleSolenoid.Value.kForward);
    balleniod2.set(DoubleSolenoid.Value.kForward);
  }

  public void armDown(DoubleSolenoid balleniod1, DoubleSolenoid balleniod2){
    balleniod1.set(DoubleSolenoid.Value.kReverse);
    balleniod2.set(DoubleSolenoid.Value.kReverse);
  }

  public void intakeIn(){
    
  }

  public void intakeOut(){

  }

  public boolean getSensor(){
    return ball.get();
  }
}