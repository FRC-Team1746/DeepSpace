package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.constants.Controls;
import frc.robot.subsystems.*;
public class Robot extends TimedRobot {
  
  Controls controls;
  TeleopDrive teleopDrive;
  Lift lift;
  Intake intake;
  Ball ball;
  Hatch hatch;

  @Override
  public void robotInit() {
    controls = new Controls();
    teleopDrive = new TeleopDrive(controls);
    lift = new Lift(controls);
    intake = new Intake(controls);
    ball = new Ball();
    hatch = new Hatch();
  }


  @Override
  public void autonomousInit() {
    
  }


  @Override
  public void autonomousPeriodic() {
    
  }

  @Override
  public void teleopInit() {
		teleopDrive.setRampRate(.5);
		teleopDrive.setBrakeMode(false);

  }

  @Override
  public void teleopPeriodic() {
    teleopDrive.teleopArcadeDrive();
    lift.update();
    intake.update();
    System.out.println("Ball Sensor: " + ball.getSensor());
    System.out.println("Hatch Sensor: " + hatch.getSensor());
    System.out.println("Lift Sensor: " + lift.getSensor());
  }

  @Override
  public void testPeriodic() {
  }
}