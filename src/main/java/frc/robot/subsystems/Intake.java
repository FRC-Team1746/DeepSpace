package frc.robot.subsystems;

import frc.robot.constants.ElectricalConstants;
import frc.robot.constants.Controls;
import frc.robot.constants.Constants;

public class Intake{
  //#region Instantiate objects and class variables
	ElectricalConstants eConstants;
	Controls controls;
  Hatch hatch;
  Ball ball;
  Lift lift;
  Climb climb;

  boolean ballUp; 
  boolean hatchUp;
  boolean rivet;
  //#endregion
  
  public Intake(Controls Controls, Lift Lift, Ball Ball, Hatch Hatch, Climb Climb){
    controls = Controls;
    lift = Lift;
    hatch = Hatch;
    ball = Ball;
    climb = Climb;
    rivet = true;
    hatchUp = true;
    ballUp = true;
  }

  public void update(){
    if(controls.driver_RB_Button()){
      if(rivet){
       //Is rivet in engaged or disengaged?
        hatch.rivetIn();
        rivet = false;
      }
      else{
       hatch.rivetOut();
       rivet = true;
      }
    }else if(controls.driver_St_Button()){
      if(ballUp){
        ball.armDown();
        ballUp = false;
      }
      else{
        ball.armUp();
        ballUp = true;
      }
    }
    else if(controls.driver_LB_Button()){
      if(hatchUp){
        hatch.pivotDown();
        hatchUp = false;
      }
      else{
        hatch.pivotUp();
        hatchUp = true;
      }
    }else if(lift.getLiftPosition() > (Constants.ballPosition1 - 60)){
      ball.armUp();
      System.out.println("Ball Has Come up due to lift");
      ballUp = true;
    }else if(controls.driver_RIGHT_DPAD()){
      climb.engageClimb();
    }
    ball.intakeControl(controls.driver_L_Trigger() - controls.driver_R_Trigger());
  
    //If we have a ball, and the lift is below the first hatch scoring position:
    // if(ball.haveBall() && lift.getLiftPosition() < Constants.ballPosition1)
    // {
    //   ball.armDown();
    //   hatch.pivotUp();  
    // }
    
  }
}

