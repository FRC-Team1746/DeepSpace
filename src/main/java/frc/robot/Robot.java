package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.constants.Controls;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {
  
  Controls controls;
  TeleopDrive TeleopDrive;
<<<<<<< HEAD
  Vision vision;
=======
  Lift lift;
  Intake intake;
  Ball ball;
  Hatch hatch;
>>>>>>> TeleopDrive

  @Override
  public void robotInit() {
    controls = new Controls();
    TeleopDrive = new TeleopDrive(controls);
<<<<<<< HEAD
    vision = new Vision();
=======
    lift = new Lift(controls);
    intake = new Intake(controls);
    ball = new Ball();
    hatch = new Hatch();
>>>>>>> TeleopDrive
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
<<<<<<< HEAD
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
=======
    TeleopDrive.teleopArcadeDrive();
    lift.update();
    intake.update();
    System.out.println("Ball Sensor: " + ball.getSensor());
    System.out.println("Hatch Sensor: " + hatch.getSensor());
    System.out.println("Lift Sensor: " + lift.getSensor());
>>>>>>> TeleopDrive
  }

  @Override
  public void testPeriodic() {
  }
}