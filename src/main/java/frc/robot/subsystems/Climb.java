package frc.robot.subsystems;

import frc.robot.constants.ElectricalConstants;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.constants.Constants;
import frc.robot.constants.Controls;
public class Climb{
  ElectricalConstants eConstants;
  Controls controls;
  private WPI_VictorSPX leftSuck;
  private WPI_TalonSRX rightSuck;
  private WPI_VictorSPX leftLift;
  private WPI_TalonSRX rightLift;
  private States currentState;
  private boolean suckOn;
  private PowerDistributionPanel pdp;
  private enum States {
    INIT,
    CLIMBUP,
    MANUALDOWN,
    CLIMBDOWN,
    STOP
  }

  public Climb(Controls Controls){
    controls = Controls;
    leftSuck = new WPI_VictorSPX(eConstants.SUCK_LEFT);
    rightSuck = new WPI_TalonSRX(eConstants.SUCK_RIGHT);
    leftLift = new WPI_VictorSPX(eConstants.LIFT_LEFT);
    rightLift = new WPI_TalonSRX(eConstants.LIFT_RIGHT);
    pdp = new PowerDistributionPanel();
    currentState = States.INIT;
    suckOn = false;
    leftLift.follow(rightLift);
    leftSuck.follow(rightLift);

  }

  public void update(){
    switch(currentState){
      case INIT:
        if(controls.SUCK_Button()){
          if(suckOn){
            rightSuck.set(0);
            suckOn = false;
          }else{
            rightSuck.set(1);
            suckOn = true;
          }
        }
        if(controls.LIFT_Button() && suckOn){
          currentState = States.CLIMBUP;
        }
      break;
      case CLIMBUP:
        if(controls.LIFT_Button()){
          rightLift.set(0.5);
        } if(pdp.getCurrent(eConstants.PDP_CHANNEL_6) > 0.5){
          rightLift.set(0);
          currentState = States.MANUALDOWN;
        } else {
          rightLift.set(0);
        } 
      break;
      case MANUALDOWN:
        if(controls.LIFT_Button()){
          rightLift.set(-0.5);
        } if(pdp.getCurrent(eConstants.PDP_CHANNEL_9) > 0.5){
          rightLift.set(0);
          currentState = States.CLIMBDOWN;
        }
      break;
      case CLIMBDOWN:
        if(pdp.getCurrent(eConstants.PDP_CHANNEL_6) > 0.5){
          rightLift.set(0);
          currentState = States.STOP;
        } else {
          rightLift.set(-0.5);
        }
      break;
      case STOP:
        rightLift.set(0);
      break;
    }
  }

}
