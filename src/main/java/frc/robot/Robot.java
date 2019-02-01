
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {
  
  Controls m_controls;
  TeleopDrive m_TeleopDrive;

  @Override
  public void robotInit() {
    m_controls = new Controls();
    m_TeleopDrive = new TeleopDrive(m_controls);
    m_TeleopDrive.resetGyro();
    m_TeleopDrive.resetEncoders();
  }


  @Override
  public void autonomousInit() {
    
  }


  @Override
  public void autonomousPeriodic() {
    
  }

  @Override
  public void teleopInit() {
		m_TeleopDrive.setRampRate(.5);
		m_TeleopDrive.setBrakeMode(false);
  }

  @Override
  public void teleopPeriodic() {
		m_TeleopDrive.teleopArcadeDrive();
		// updateSmartDashboard();
  }

  @Override
  public void testPeriodic() {
  }
}
