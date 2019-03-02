package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.constants.Controls;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {
  
  Controls controls;
  TeleopDrive TeleopDrive;
  Vision vision;
  Lift lift;
  Intake intake;
  Ball ball;
  Hatch hatch;
  Pneumatics pneumatics;

  @Override
  public void robotInit() {
    controls = new Controls();
    TeleopDrive = new TeleopDrive(controls);
    lift = new Lift(controls);
    intake = new Intake(controls, lift, ball, hatch);
    ball = new Ball();
    hatch = new Hatch();
    vision = new Vision();
    pneumatics = new Pneumatics();
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
    lift.update();
    intake.update();
    pneumatics.update();
    System.out.println("Ball Sensor: " + ball.getSensor());
    System.out.println("Hatch Sensor 1: " + hatch.getSensor1());
    System.out.println("Hatch Sensor 1: " + hatch.getSensor2());
    System.out.println("Lift Sensor: " + lift.getSensor());


    vision.fetchUpdate();
    if (controls.driver_B_Button()) {
      
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

  public void updateLog(){
    // System.out.println("Ball Sensor: " + ball.getSensor());
    // System.out.println("Hatch Sensor 1: " + hatch.getSensor1());
    // System.out.println("Hatch Sensor 1: " + hatch.getSensor2());
    System.out.println("Lift Sensor: " + lift.getSensor());
  }

  @Override
  public void testPeriodic() {
  }
}