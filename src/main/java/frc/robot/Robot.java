/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

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
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team1746.common.DriveTrain;
import team1746.common.transforms.ITransform;
import team1746.common.transforms.SlowTransform;
import team1746.common.transforms.SquaredInputTransform;
import team1746.motion_profiling.PathFollower;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends IterativeRobot {
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

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    initializeAll();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  /**
   * This function is called periodically during test mode.
   */
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
