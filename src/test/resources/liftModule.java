import frc.robot.constants.Constants;

// Purpose of this class? Actually have a "lift" which is unit-testable >:}
public class liftModule {
    public boolean haveBall;
    public boolean driver_A_Button;
    public boolean driver_X_Button;
    public boolean driver_UP_DPAD;
    public boolean driver_Y_Button;
    public boolean driver_Se_Button;
    public boolean setp, sett, autoOn;
    public boolean liftDown;
    public double driver_YR_Axis;
    public double liftPosition;
    public double[] liftTarget;

    public liftModule() {
        this.haveBall = false;
        this.driver_A_Button = false;
        this.driver_X_Button = false;
        this.driver_UP_DPAD = false;
        this.driver_Y_Button = false;
        this.driver_Se_Button = false;
        this.setp = false;
        this.sett = false;
        this.autoOn = false;
        this.liftDown = false;
        this.driver_YR_Axis = 0;
        this.liftPosition = 0;
        this.liftTarget = new double[2]; // Structure: [TargetVelocity, TargetPosition], -1 Vel means reset, -1 Pos means no Pos Specified
    }

    public double[] update() {
        if (!driver_A_Button && !driver_X_Button && !driver_Y_Button && !driver_UP_DPAD) {
          // System.out.println("Manual Case/DeAct");
          if (driver_YR_Axis > .15 || driver_YR_Axis < -.15) {
            // System.out.println("CASE HIT");
            liftPosition = getLiftPosition() - driver_YR_Axis * 2.5 * Constants.liftEncoderPerInch;
            liftTarget[0] = 800;
            liftTarget[1] = liftPosition;
          } else if(haveBall && driver_Se_Button() && getLiftPosition() < Constants.ballPosition1) {
            if(autoOn) {
                liftPosition = Constants.ballPosition1;
                liftTarget[0] = 800;
                liftTarget[1] = liftPosition;
                autoOn = false;
            } else {
                liftTarget[0] = 0;
                liftTarget[1] = 0;
                autoOn = true;
            }
          } else {
            liftTarget[0] = -1;
            liftTarget[1] = -1;
            if (liftDown) {
              // System.out.println("RESET !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
              liftTarget[0] = -1;
              liftTarget[1] = 0;

            }
          }
        } else if (!haveBall) {
          System.out.println("No ball case");
          if (driver_A_Button) {
            if (liftDown) {
              liftTarget[0] = 0;
              liftTarget[1] = -1;
            } else if (getLiftPosition() <= Constants.liftEncoderPosition0) {
              liftPosition = 0;
              liftTarget[0] = 0;
              liftTarget[1] = liftPosition;
            } else {
              liftPosition = Constants.liftEncoderPosition0;
              liftTarget[0] = 175;
              liftTarget[1] = liftPosition;
            }
            System.out.println("A Pressed Read");
          } else if (driver_X_Button) {
            liftPosition = Constants.hatchPosition2;
            liftTarget[0] = 800;
            liftTarget[1] = liftPosition;
            System.out.println("X Pressed Read");
          } else if (driver_Y_Button) {
            liftPosition = Constants.hatchPosition3;
            liftTarget[0] = 800;
            liftTarget[1] = liftPosition;
            System.out.println("Y Pressed Read");
          }
        } else if (haveBall) {
          System.out.println("Ball Case");
          if (driver_X_Button) {
            liftPosition = Constants.ballPositionCargo;
            liftTarget[0] = 800;
            liftTarget[1] = liftPosition;
            System.out.println("X Pressed Read");
          } else if (driver_Y_Button) {
            liftPosition = Constants.ballPosition2;
            liftTarget[0] = 800;
            liftTarget[1] = liftPosition;
            System.out.println("Y Pressed Read");
          } else if(driver_UP_DPAD){
            liftPosition = Constants.ballPosition3;
            liftTarget[0] = 800;
            liftTarget[1] = liftPosition;
            System.out.println("UP DPAD Pressed Read");
          } else if (driver_A_Button) {
            if (liftDown) {
              liftTarget[0] = 0;
              liftTarget[1] = -1;
            } else if (getLiftPosition() <= Constants.liftEncoderPosition0) {
              liftPosition = 0;
              liftTarget[0] = 0;
              liftTarget[1] = liftPosition;
            } else {
              liftPosition = Constants.liftEncoderPosition0;
              liftTarget[0] = 132;
              liftTarget[1] = liftPosition;
            }
            System.out.println("A Pressed Read");
          }
        } else if (liftDown) {
          System.out.println("RESET !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
          liftTarget[0] = -1;
          liftTarget[1] = 0;
        }
        return liftTarget;
      }

      public void setHaveBall(boolean state) {
          haveBall = state;
      }

      public void setDriver_A_Button(boolean state) {
          driver_A_Button = state;
      }

      public void setDriver_X_Button(boolean state) {
          driver_X_Button = state;
      }

      public void setDriver_Y_Button(boolean state) {
          driver_Y_Button = state;
      }

      public void setDriver_UP_DPAD(boolean state) {
          driver_UP_DPAD = state;
      }

      public void setDriver_Se_Button(boolean state) {
          driver_Se_Button = state;
      }

      public boolean driver_Se_Button(){
		if(driver_Se_Button && !setp) {
			sett = true;
		} else {
			sett = false;
		}
		setp = driver_Se_Button;
		return sett;
	}

      public void setLiftDown(boolean state) {
          liftDown = state;
      }

      public void setDriver_YR_Axis(double value) {
          driver_YR_Axis = value;
      }

      public double getLiftPosition() {
          liftPosition += liftTarget[1];
          return liftPosition;
      }
    

}