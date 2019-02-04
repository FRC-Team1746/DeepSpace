package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.auton.arcs.DistanceScalingArc;
import frc.robot.auton.follower.AutonDriveTrain;
import frc.robot.auton.follower.FollowArc;
import frc.robot.constants.Controls;
import frc.robot.subsystems.TeleopDrive;

public class Robot extends TimedRobot {
  private FollowArc auton;
  
  Controls controls;
  TeleopDrive TeleopDrive;

  @Override
  public void robotInit() {
    auton = new FollowArc(new AutonDriveTrain(), new DistanceScalingArc());
    controls = new Controls();
    TeleopDrive = new TeleopDrive(controls);
  }


  @Override
  public void autonomousInit() {
    auton.init();
  }


  @Override
  public void autonomousPeriodic() {
    auton.run();
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