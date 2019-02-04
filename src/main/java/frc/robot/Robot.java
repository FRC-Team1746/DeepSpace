package frc.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import frc.robot.commands.autons.MiddleStartRightSwitchMP;
import frc.robot.commands.driveTrain.ArcadeDrive;
import frc.robot.commands.driveTrain.DriveToEncoderSetpoint;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team1746.common.DriveTrain;
import team1746.common.transforms.ITransform;
import team1746.common.transforms.SlowTransform;
import team1746.common.transforms.SquaredInputTransform;
import team1746.motion_profiling.PathFollower;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.constants.Controls;
import frc.robot.subsystems.TeleopDrive;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;

  public static OI oi;
	public static RobotMap robotMap = new RobotMap();

  public static DriveTrain driveTrain;
  public static ITransform arcadeDriveTransform;
	public static ITransform slowTransform;

	public static Command arcadeDrive;

	public static Command driveToEncoderSetpoint;
  
  Controls controls;
  TeleopDrive TeleopDrive;

  @Override
  public void robotInit() {
    initializeAll();
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


private void initializeAll() {

  driveTrain = new DriveTrain(robotMap.leftRearMotor, robotMap.leftFrontMotor, robotMap.rightRearMotor, robotMap.rightFrontMotor, robotMap.pressureSensorID);
  loadTrajectories();

  arcadeDriveTransform = new SquaredInputTransform();
  slowTransform = new SlowTransform();

  arcadeDrive = new ArcadeDrive(driveTrain, arcadeDriveTransform,
      oi.driverJoystick, oi.AXIS_LEFT_STICK_Y, oi.AXIS_RIGHT_STICK_X, oi.AXIS_LEFT_TRIGGER, oi.AXIS_RIGHT_TRIGGER ,slowTransform);
  driveTrain.setCommandDefault(arcadeDrive);

}


private static HashMap<String, PathFollower> pathFollowers = new HashMap<String, PathFollower>();

private ArrayList<String> pathNames = new ArrayList<String>();


private void loadTrajectories() {

  pathNames.add("centerRight");
  pathNames.add("centerLeft");
  pathNames.add("rightScaleFixed");
  pathNames.add("leftScaleFixed");

  try {

    importTrajectories();

  } catch (FileNotFoundException e) {

    DriverStation.reportError("!!!!!!!!!!!!!!!!!!!!!!!!!!Could not find trajectory!!!!!!!!!!!!!!!!!!!!!!!!!! " + e.getMessage(), true);

  }

}

private void importTrajectories() throws FileNotFoundException {

  for (int i = 0; i < pathNames.size(); i++) {

    String pathName = pathNames.get(i);

    File rightTraj = new File("/home/lvuser/" + pathName + "_right_detailed.csv");
    File leftTraj = new File("/home/lvuser/" + pathName + "_left_detailed.csv");

    pathFollowers.put(pathName, new PathFollower(driveTrain, leftTraj, rightTraj));
  }
}

public static Map<String, Command> autonMap() {

  Map<String, Command> autonCommands = new HashMap<String, Command>();
  autonCommands.put("MiddleStartRightSwitch", new MiddleStartRightSwitchMP(pathFollowers));
  autonCommands.put("GoStraight", new DriveToEncoderSetpoint(driveTrain, 100, -.5, 10));
  
  return autonCommands;

}

}
