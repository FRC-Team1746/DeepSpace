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
  Climb climb;
  Pneumatics pneumatics;
  Vision vision;

  @Override
  public void robotInit() {
    controls = new Controls();
    TeleopDrive = new TeleopDrive(controls);
    hatch = new Hatch();
    ball = new Ball();
    lift = new Lift(controls, ball, hatch);
    climb = new Climb(controls);
    intake = new Intake(controls, lift, ball, hatch, climb);
    pneumatics = new Pneumatics();
    vision = new Vision();
  }


  @Override
  public void autonomousInit() {
    TeleopDrive.setBrakeMode(true);
    lift.setBrakeMode(true);
  }


  @Override
  public void autonomousPeriodic() {
    vision.lightOnButtonPress(controls.driver_B_Button());
    vision.PipelineOnPress(controls.driver_B_Button());
    if(controls.driver_B_Button() && vision.fetchUpdate() && vision.isTargetValid()) 
    {
    TeleopDrive.setSteer(vision.GenerateSteer());
    }
    else {
      TeleopDrive.teleopArcadeDrive();
    }

    pneumatics.update();
    lift.update();
    intake.update();
  }

  @Override
  public void teleopInit() {
    TeleopDrive.setBrakeMode(true);
    lift.setBrakeMode(true);
  }

  @Override
  public void teleopPeriodic() {
    vision.lightOnButtonPress(controls.driver_B_Button());
    vision.PipelineOnPress(controls.driver_B_Button());
    if(controls.driver_B_Button() && vision.fetchUpdate() && vision.isTargetValid()) 
    {
    TeleopDrive.setSteer(vision.GenerateSteer());
    }
    else {
      TeleopDrive.teleopArcadeDrive();
    }

    pneumatics.update();
    lift.update();
    intake.update();
    climb.update();
    System.out.println("Lift Encoders: " + lift.getLiftPosition());
    // System.out.println("Hatch Sensor: " + hatch.getSensor1());
    // System.out.println("Lift Sensor: " + lift.liftDown());
    System.out.println("Lift Sensor Value:" + lift.getSensor());
    // System.out.println("Ball Sensor: " + ball.haveBall());
  }

  @Override
  public void disabledPeriodic() {
    controls.setRumble(0);
    super.disabledPeriodic();
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
   
  }

}