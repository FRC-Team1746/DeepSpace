package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.constants.Controls;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {
  
  Controls controls;
  TeleopDrive TeleopDrive;
  Vision vision;

  @Override
  public void robotInit() {
    controls = new Controls();
    TeleopDrive = new TeleopDrive(controls);
    vision = new Vision();
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
    vision.fetchUpdate();
    if (controls.driver_X_Button()) {
      
      TeleopDrive.setDriveAndSteer(vision.GenerateDrive(), vision.GenerateSteer());
    } else {
      TeleopDrive.teleopArcadeDrive();
    }
    System.out.print("Skew:");
    System.out.println(vision.getSkew());
    // vision.setLedMode(0);
    // vision.track();
    // vision.fetchUpdate();
    // System.out.println(vision.toString());
  }

  @Override
  public void testPeriodic() {
  }
}