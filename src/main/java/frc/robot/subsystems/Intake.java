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

  boolean ballUp; 
  boolean hatchUp;
  boolean rivet;

  public Intake(Controls Controls){
    controls = Controls;
    hatch = new Hatch();
    ball = new Ball();
    lift = new Lift(controls);
  }

  public void update(){
    if(ball.getSensor() && lift.getLiftPosition() < constants.hatchPosition1){
      if(hatchUp && !ballUp){
        hatch.pivotDown();
        hatchUp = false;
      }else if(!hatchUp && !ballUp){
        hatch.pivotUp();
        hatchUp = true;
      }
    }else if(!hatchUp){
      ball.armUp();
      ballUp = true;
      if(controls.driver_RB_Button()){
        if(rivet){
          hatch.rivetIn();
          rivet = false;
        }else{
          hatch.rivetOut();
          rivet = true;
        }
      }else if(hatch.getSensor()){
        hatch.rivetIn();
        rivet = false;
      }
    }else if(controls.driver_St_Button()){
      if(ballUp){
        ball.armDown();
        ballUp = false;
      }else{
        ball.armUp();
        ballUp = true;
      }
    }else if(controls.driver_LB_Button()){
      if(hatchUp){
        hatch.pivotDown();
        hatchUp = false;
      }else{
        hatch.pivotUp();
        hatchUp = true;
      }
    }else{
      if(hatchUp && !ballUp){
        ball.armDown();
        ballUp = false;
      }else if(!hatchUp){
        ball.armUp();
        ballUp = true;
      }
    }

      if(controls.driver_L_Trigger() >= .1 ){
        ball.intakeIn(controls.driver_L_Trigger());
      }else if(controls.driver_R_Trigger() >= .1){
        ball.intakeOut(controls.driver_R_Trigger());
      }
  }
}
