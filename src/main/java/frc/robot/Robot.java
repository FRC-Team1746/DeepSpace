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
    hatch = new Hatch();
    ball = new Ball();
    lift = new Lift(controls, ball, hatch);
    intake = new Intake(controls, lift, ball, hatch);
    pneumatics = new Pneumatics();
    vision = new Vision();
  }


  @Override
  public void autonomousInit() {
		// TeleopDrive.setRampRate(.5);
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

    intake.update();
    pneumatics.update();
    lift.update();
    intake.update();
  }

  @Override
  public void teleopInit() {
		// TeleopDrive.setRampRate(.5);
    TeleopDrive.setBrakeMode(true);
    // TeleopDrive.resetEncoders();
    lift.setBrakeMode(true);
  }

  @Override
  public void teleopPeriodic() {
    // NEEDS TO BE AN UPDATE METHOD WITHIN VISION CLASS GarbanzoBean
    // System.out.println("Above The iffff isTargetValid: " + vision.isTargetValid());
    vision.lightOnButtonPress(controls.driver_B_Button());
    vision.PipelineOnPress(controls.driver_B_Button());
    vision.getRawSkew();
    if(controls.driver_B_Button() && vision.fetchUpdate() && vision.isTargetValid()) 
    {
    TeleopDrive.setSteer(vision.GenerateSteer());
    // System.out.println(vision.getXOffset());
    }
    else {
      // System.out.println("Fetch Update Vision: " + vision.fetchUpdate());
      TeleopDrive.teleopArcadeDrive();
    }
    // System.out.println(vision.getXOffset());
    // System.out.println("below it isTargetValid: " + vision.isTargetValid());

    System.out.println("Lift Encoders: " + lift.getLiftPosition());
    pneumatics.update();
    lift.update();
    intake.update();
    // System.out.println("Hatch Sensor: " + hatch.getSensor1());
    // System.out.println("Lift Sensor: " + lift.liftDown());
    // System.out.println("Lift Sensor Value:" + lift.getSensor());
    System.out.println("Ball Sensor: " + ball.haveBall());
  }

  @Override
  public void disabledPeriodic() {
    super.disabledPeriodic();
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
   
  }

}