package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

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
	// Left Axis
	public double driver_X_Axis(){
		x_axisSquared = xbox_driver.getRawAxis(0);
		x_axisSquared = x_axisSquared * x_axisSquared;
		if (xbox_driver.getRawAxis(0) < 0) {
			x_axisSquared = x_axisSquared * -1;
		}
		return x_axisSquared;
		//return xbox_driver.getRawAxis(0);
	}

	public double driver_Y_Axis(){
		return xbox_driver.getRawAxis(1);
		
	}
}