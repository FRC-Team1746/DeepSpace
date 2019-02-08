package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.constants.Controls;
import frc.robot.subsystems.*;
public class Robot extends TimedRobot {
  
  Controls controls;
  TeleopDrive teleopDrive;
  Lift lift;
  Intake intake;

  @Override
  public void robotInit() {
    controls = new Controls();
    teleopDrive = new TeleopDrive(controls);
    lift = new Lift(controls);
    intake = new Intake(controls);
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
  }

  @Override
  public void testPeriodic() {
  }
}