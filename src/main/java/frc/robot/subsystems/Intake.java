package frc.robot.subsystems;

import frc.robot.constants.ElectricalConstants;
import frc.robot.constants.Controls;
import frc.robot.constants.Constants;

public class Intake{

	ElectricalConstants eConstants;
	Controls controls;
  Constants constants;
  Hatch hatch;
  Ball ball;
  Lift lift;

  public Intake(Controls Controls){
    controls = Controls;
    hatch = new Hatch();
    ball = new Ball();
    lift = new Lift(controls);
  }

  public void update(){
    
  }
}
