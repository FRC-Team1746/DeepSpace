package frc.robot.constants;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import frc.robot.constants.ElectricalConstants;

public class Controls {
	ElectricalConstants electricalConstants = new ElectricalConstants();
	double x_axisSquared;
	double y_axisSquared;
	double x_axis;
	double y_axis;
	double yo_axis;
	boolean lbt = false;
	boolean lbp = false;
	boolean rbt = false;
	boolean rbp = false;
	boolean stt = false;
	boolean stp = false;
	boolean sett = false;
	boolean setp = false;
	boolean lst = false;
	boolean lsp = false;
	boolean ast = false;
	boolean abt = false;
	boolean bst = false;
	boolean bpt = false;
	NetworkTableEntry autoBallOn;
	Joystick xbox_driver;
	Joystick xbox_oper;
	
	public Controls()
	{
		xbox_driver = new Joystick(electricalConstants.JOYSTICK_DRIVER);
		xbox_oper = new Joystick(electricalConstants.JOYSTICK_OPERATOR);
		NetworkTableInstance inst = NetworkTableInstance.getDefault();
		NetworkTable table = inst.getTable("automationChecks");	
		autoBallOn = table.getEntry("autoBallON");
	}	

	//AXIS
	public double driver_XL_Axis(){
		x_axis = xbox_driver.getRawAxis(0);
		if(Math.abs(x_axis) < .1){
			x_axis = 0;
			return x_axis;
		}else{
			x_axisSquared = x_axis * x_axis;
			if (x_axis < 0) {
				x_axisSquared = x_axisSquared * -1;
			}
			return x_axisSquared;
		}
		//return xbox_driver.getRawAxis(0);
	}

	public double driver_YL_Axis(){
		y_axis = xbox_driver.getRawAxis(1);
		if(Math.abs(y_axis) < .1){
			y_axis = 0;
			return y_axis;
		}
		return y_axis;
		// else{
		// 	y_axisSquared = y_axis * y_axis;
		// 	if (y_axis < 0) {
		// 		y_axisSquared = y_axisSquared * -1;
		// 	}
		// 	return y_axisSquared;
		// }
	}

	public double oper_YL_Axis(){
		yo_axis = xbox_oper.getRawAxis(1);
		if(Math.abs(yo_axis) < .1){
			yo_axis = 0;
			return yo_axis;
		}
		return yo_axis;
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
		if(xbox_driver.getRawButton(5) && !lbp) {
			lbt = true;
		} 
		else {
			lbt = false;
		}
		lbp = xbox_driver.getRawButton(5);
		return lbt;
	}

	public boolean driver_RB_Button(){
		if(xbox_driver.getRawButton(6) && !rbp) {
			rbt = true;
		} 
		else {
			rbt = false;
		}
		rbp = xbox_driver.getRawButton(6);
		return rbt;
	}

	public boolean driver_Se_Button(){
		if(xbox_driver.getRawButton(7) && !setp) {
			sett = true;
		} else {
			sett = false;
		}
		setp = xbox_driver.getRawButton(7);
		autoBallOn.setBoolean(sett);
		return sett;
	}

	public boolean driver_St_Button(){
		if(xbox_driver.getRawButton(8) && !stp) {
			stt = true;
		} 
		else {
			stt = false;
		}
		stp = xbox_driver.getRawButton(8);
		return stt;
	}

	public boolean driver_LA_Button(){
		return xbox_driver.getRawButton(9);
	}

	public boolean driver_RA_Button(){
		return xbox_driver.getRawButton(10);
	}

	//DPAD
	public boolean driver_UP_DPAD(){
		if ((xbox_driver.getPOV()) == 0) {
			return true;
		}else {
			return false;
		}
	}
	public boolean driver_DOWN_DPAD(){
		if ((xbox_driver.getPOV()) == 180) {
			return true;
		}else {
			return false;
		}
	}
	public boolean driver_LEFT_DPAD(){
		if ((xbox_driver.getPOV()) == 270) {
			return true;
		}else {
			return false;
		}
	}
	public boolean driver_RIGHT_DPAD(){
		if ((xbox_driver.getPOV()) == 90) {
			return true;
		}else {
			return false;
		}
	}

	public boolean SUCK_Button(){
		if(xbox_oper.getRawButton(2) && !lsp) {
			lst = true;
		} else {
			lst = false;
		}
		lsp = xbox_oper.getRawButton(2);
		return lst;
	}

	public boolean LIFT_Button(){
		return xbox_oper.getRawButton(1);
	}

	public boolean oper_A_Button() {
		if(xbox_oper.getRawButton(1) && !bpt) {
			ast = true;
		} else {
			ast = false;
		}
		abt = xbox_oper.getRawButton(1);
		return ast;
	}

	public boolean oper_B_Button() {
		if(xbox_oper.getRawButton(2) && !bpt) {
			bst = true;
		} else {
			bst = false;
		}
		bpt = xbox_oper.getRawButton(2);
		return bst;
	}

	public void setRumble(double value) {
		xbox_driver.setRumble(RumbleType.kLeftRumble, value);
		xbox_driver.setRumble(RumbleType.kRightRumble, value);
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