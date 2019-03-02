package frc.robot.subsystems;

import frc.robot.constants.ElectricalConstants;
import frc.robot.constants.Controls;
import frc.robot.constants.Constants;

public class Intake{
  //#region Instantiate objects and class variables
	ElectricalConstants eConstants;
	Controls controls;
  Constants constants;
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
  }

  public void update(){
    //If we have a ball, and the lift is below the first hatch scoring position:
    if(ball.getSensor() && lift.getLiftPosition() < constants.hatchPosition1)
    {
      //if the hatch is up and the OTB intake is deployed: pivot the hatch mech down
      if(hatchUp && !ballUp)
      {
        hatch.pivotDown();
        hatchUp = false;
      }
      //Else if the hatch mech is down and the OTB intake is deployed: pivot the hatch mech up
      else if(!hatchUp && !ballUp)
      {
        hatch.pivotUp();
        hatchUp = true;
      }
  
    }
    //Otherwhise if the hatch mechanism is not deployed, retract the OTB intake  
    else if(!hatchUp)
    {
      ball.armUp();
      ballUp = true;
      //When the driver presses the RB button, check for the state of the hatch rivet, and switch the state
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
      }
      //If the driver doesn't press the button, but the hatch sensor triggers, set the hatch rivet to the "in" state
      else if(hatch.getSensor())
      {
        hatch.rivetIn();
        rivet = false;
      }
    }
    //Otherwhise, if the driver presses the start button, read the otb intake state and switch states
    else if(controls.driver_St_Button())
    {
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

    //If the driver presses the LB button:
    //Read the state of the hatch pivot, and switch the state
    else if(controls.driver_LB_Button())
    {
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
    //Check for the hatch pivot state and the OTB intake state, set the state of the OTB intake. 
    else
    {
      if(hatchUp && !ballUp)
      {
        ball.armDown();
        ballUp = false;
      }
      else if(!hatchUp)
      {
        ball.armUp();
        ballUp = true;
      }
    }

      //Get the L and R triggers and control the OTB intake roller
      if(controls.driver_L_Trigger() >= .1 )
      {
        ball.intakeIn(controls.driver_L_Trigger());
      }
      else if(controls.driver_R_Trigger() >= .1)
      {
        ball.intakeOut(controls.driver_R_Trigger());
      }
  }
}
