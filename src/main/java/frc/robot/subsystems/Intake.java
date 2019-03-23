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

  boolean ballUp; 
  boolean hatchUp;
  boolean rivet;
  //#endregion
  
  public Intake(Controls Controls, Lift Lift, Ball Ball, Hatch Hatch)
  {
    controls = Controls;
    lift = Lift;
    hatch = Hatch;
    ball = Ball;
    rivet = true;
    hatchUp = true;
    ballUp = true;
  }

  public void update(){
    System.out.println("Intake Update");
    if(controls.driver_RB_Button())
    {
      if(rivet)
      {
       hatch.rivetIn();
       rivet = false;
      }
      else
      {
       hatch.rivetOut();
       rivet = true;
      }
    }else if(controls.driver_St_Button())
    {
      System.out.println("Over The Bumper");
      System.out.println("BALLUP: " + ballUp);
      if(ballUp)
      {
        ball.armDown();
         ballUp = false;
      }
      else
      {
        ball.armUp();
        ballUp = true;
      }
    }
    else if(controls.driver_LB_Button())
    {
      System.out.println("Pivot Has Been Hit");
      if(hatchUp)
      {
        hatch.pivotDown();
        hatchUp = false;
      }
      else
      {
        hatch.pivotUp();
        hatchUp = true;
      }
    }
    ball.intakeControl(controls.driver_L_Trigger() - controls.driver_R_Trigger());
    




    //If we have a ball, and the lift is below the first hatch scoring position:
    // if(ball.getSensor() <= 1.3 && lift.getLiftPosition() < Constants.ballPosition1)
    // {
    //   ball.armDown();
    //   hatch.pivotUp();  
    // }
    
  }
}

