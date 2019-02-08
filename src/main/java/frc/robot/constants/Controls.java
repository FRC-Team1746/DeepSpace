package frc.robot.constants;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.constants.ElectricalConstants;

public class Controls {
	ElectricalConstants electricalConstants = new ElectricalConstants();
	
	double x_axisSquared;
	Joystick xbox_driver;
	Joystick xbox_oper;
	
	public Controls()
	{
		xbox_driver = new Joystick(electricalConstants.JOYSTICK_DRIVER);
		xbox_oper = new Joystick(electricalConstants.JOYSTICK_OPERATOR);
	}	

	//AXIS
	public double driver_XL_Axis(){
		x_axisSquared = xbox_driver.getRawAxis(0);
		x_axisSquared = x_axisSquared * x_axisSquared;
		if (xbox_driver.getRawAxis(0) < 0) {
			x_axisSquared = x_axisSquared * -1;
		}
		return x_axisSquared;
		//return xbox_driver.getRawAxis(0);
	}

	public double driver_YL_Axis(){
		return -xbox_driver.getRawAxis(1);
	}

	public double driver_XR_Axis(){
		return xbox_driver.getRawAxis(4);
	}
	
	public double driver_YR_Axis(){
		return xbox_driver.getRawAxis(5);
	}

	public double driver_L_Trigger(){
		return xbox_driver.getRawAxis(2);
	}

	public double driver_R_Trigger(){
		return xbox_driver.getRawAxis(3);
	}

	//BUTTONS
	
	public boolean driver_A_Button(){
		return xbox_driver.getRawButton(1);	
	}	

	public boolean driver_B_Button(){
		return xbox_driver.getRawButton(2);
	}

	public boolean driver_X_Button(){
		return xbox_driver.getRawButton(3);
	}

	public boolean driver_Y_Button(){
		return xbox_driver.getRawButton(4);
	}

	public boolean driver_LB_Button(){
		return xbox_driver.getRawButton(5);
	}

	public boolean driver_RB_Button(){
		return xbox_driver.getRawButton(6);
	}

	public boolean driver_Se_Button(){
		return xbox_driver.getRawButton(7);
	}

	public boolean driver_St_Button(){
		return xbox_driver.getRawButton(8);
	}

	public boolean driver_LA_Button(){
		return xbox_driver.getRawButton(9);
	}

	public boolean driver_RA_Button(){
		return xbox_driver.getRawButton(10);
	}

	//DPAD
	public boolean driver_UP_DPAD(){
		if ((xbox_oper.getPOV()) == 0) {
			return true;
		}else {
			return false;
		}
	}
	public boolean driver_DOWN_DPAD(){
		if ((xbox_oper.getPOV()) == 180) {
			return true;
		}else {
			return false;
		}
	}
	public boolean driver_LEFT_DPAD(){
		if ((xbox_oper.getPOV()) == 270) {
			return true;
		}else {
			return false;
		}
	}
	public boolean driver_RIGHT_DPAD(){
		if ((xbox_oper.getPOV()) == 90) {
			return true;
		}else {
			return false;
		}
	}
}

/*********************************************************************************/	
/* 
 * Xbox Controller Layout
 * 
 *  Axis
 *    0   - Left Stick X        - driver forward and backward
 *    1   - Left Stick Y        - driver left and right
 *    2   - Left Trigger        - driver
 *    3   - Right Trigger       - driver
 *    4   - Right Stick X       - driver
 *    5   - Right Stick Y       - driver
 * 
 * 	Buttons
 *    1   - A                   - driver
 *    2   - B                   - driver
 *    3   - X                   - driver
 *    4   - Y                   - driver
 *    5   - Left Bumper         - driver
 *    6   - Right Bumper        - driver
 *    7   - Select              - driver
 *    8   - Start               - driver
 *    9   - Left Analog Button  - driver
 *    10  - Right Analog Button - driver
 *  
 *  POV (DPAD)
 *    0   - DPAD Up             - driver
 *    45  - DPAD Up/Right       - driver
 *    90  - DPAD Right          - driver
 *    135 - DPAD Down/Right     - driver
 *    180 - DPAD Down           - driver
 *    225 - DPAD Down/Left      - driver
 *    270 - DPAD Left           - driver 
 *    315 - DPAD Up/Left        - driver
 *    
*/
/*********************************************************************************/