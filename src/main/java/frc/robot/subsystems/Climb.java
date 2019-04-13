package frc.robot.subsystems;

import frc.robot.constants.ElectricalConstants;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import frc.robot.constants.Constants;

public class Climb{
  ElectricalConstants eConstants;
  
  private WPI_VictorSPX leftSuck;
  private WPI_VictorSPX rightSuck;
  private WPI_VictorSPX leftLift;
  private WPI_VictorSPX rightLift;

  public Climb(){
    // leftSuck = new WPI_VictorSPX(eConstants.SUCK_LEFT);
    // rightSuck = new WPI_VictorSPX(eConstants.SUCK_LEFT);
    // leftLift = new WPI_VictorSPX(eConstants.LIFT_LEFT);
    // rightLift = new WPI_VictorSPX(eConstants.LIFT_RIGHT);
  }

  public void suck(){
  }

  public void lift(){

  }
}

