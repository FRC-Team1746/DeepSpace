package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.constants.Controls;
import frc.robot.subsystems.TeleopDrive;

public class Robot extends TimedRobot {
  
  Controls controls;
  TeleopDrive TeleopDrive;

  @Override
  public void robotInit() {
    controls = new Controls();
    TeleopDrive = new TeleopDrive(controls);
  }


  @Override
  public void autonomousInit() {
    
  }


  @Override
  public void autonomousPeriodic() {
    
  }

  @Override
  public void teleopInit() {
		TeleopDrive.setRampRate(.5);
		TeleopDrive.setBrakeMode(false);

  }

  @Override
  public void teleopPeriodic() {
		TeleopDrive.teleopArcadeDrive();
  }

  @Override
  public void testPeriodic() {
  }
}