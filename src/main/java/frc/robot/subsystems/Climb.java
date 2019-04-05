package frc.robot.subsystems;

import frc.robot.constants.ElectricalConstants;
import frc.robot.constants.Controls;
import frc.robot.constants.Constants;
import edu.wpi.first.wpilibj.Solenoid;

public class Climb{
  ElectricalConstants eConstants;
  
  private Solenoid climbenoid;
  
  public Climb(){
    climbenoid = new Solenoid(1);
  }

  public void engageClimb(){
    climbenoid.set(true);
  }
}

