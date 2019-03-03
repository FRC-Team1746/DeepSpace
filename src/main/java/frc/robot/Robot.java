package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.constants.Controls;
import frc.robot.subsystems.*;
public class Robot extends TimedRobot {
  
  Controls controls;
  TeleopDrive TeleopDrive;
  Lift lift;
  Intake intake;
  Ball ball;
  Hatch hatch;
  Pneumatics pneumatics;
  Vision vision;

  @Override
  public void robotInit() {
    controls = new Controls();
    TeleopDrive = new TeleopDrive(controls);
    lift = new Lift(controls);
    hatch = new Hatch();
    ball = new Ball();
    intake = new Intake(controls, lift, ball, hatch);
    pneumatics = new Pneumatics();
    vision = new Vision();
  }


  @Override
  public void autonomousInit() {
		TeleopDrive.setRampRate(.5);
		TeleopDrive.setBrakeMode(true);
    
  }


  @Override
  public void autonomousPeriodic() {
    TeleopDrive.teleopArcadeDrive();
    hatch.rivetIn();
    hatch.pivotUp();
  }

  @Override
  public void teleopInit() {
		TeleopDrive.setRampRate(.5);
		TeleopDrive.setBrakeMode(true);

    hatch.rivetIn();
    hatch.pivotUp();
  }

  @Override
  public void teleopPeriodic() {
/*NEEDS TO BE AN UPDATE METHOD WITHIN VISION CLASS
    // if(controls.driver_B_Button()) {
    //   vision.fetchUpdate();
    //   TeleopDrive.setDriveAndSteer(vision.GenerateDrive(), vision.GenerateSteer());
    //   System.out.println(vision.getXOffset());
    // } else {
    //   TeleopDrive.teleopArcadeDrive();
    // }
*/
    TeleopDrive.teleopArcadeDrive();
    pneumatics.update();
    // lift.update();
    intake.update();
    // System.out.println("Ball Sensor: " + ball.getSensor());
    // System.out.println("Hatch Sensor: " + hatch.getSensor());
    System.out.println("Lift Sensor: " + lift.getSensor());
  }

  @Override
  public void disabledPeriodic() {
    super.disabledPeriodic();
  }

  @Override
  public void testPeriodic() {
  }
}