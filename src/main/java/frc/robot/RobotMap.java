/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	//Set hardware IDs

	public int leftFrontMotor = 5;
	public int leftRearMotor = 1;
	public int rightFrontMotor = 7;
	public int rightRearMotor = 3;

	public int pressureSensorID = 3;
	
	public int intakeLeftRoller = 6;
	public int intakeRightRoller = 8;
	public int intakeLiftMotor = 2;
	public int intakeArm = 4;
	
	public int dumperMotor = 4;
	public int PCMID = 15; 
	
	// Pneumatics
	public int[] solenoidIDs = new int[] {0, 1, 2, 3};	
	
}
