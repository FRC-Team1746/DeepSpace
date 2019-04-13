package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

// import java.lang.Math;

import edu.wpi.first.wpilibj.DigitalInput;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;

import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.*;
import frc.robot.constants.ElectricalConstants;
import frc.robot.constants.Controls;
import frc.robot.constants.Constants;

public class Lift {

  ElectricalConstants eConstants;
  Controls controls;
  Ball ball;
  Hatch hatch;
  NetworkTableInstance inst;
  NetworkTable table;
  NetworkTableEntry isAutoOn;

  private VictorSPX liftLeft;
  private WPI_TalonSRX liftRight;
  private DigitalInput liftBottom;
  private Relay led;

  private double liftPosition;
  private int ledTimer;
  private int rumbleTimer;
  private int ledOffTimer;
  private boolean autoOn;
  private double feedFoward;

  public Lift(Controls Controls, Ball Ball, Hatch Hatch) {

    eConstants = new ElectricalConstants();
    liftLeft = new VictorSPX(eConstants.ELEVATOR_VICTOR);
    liftRight = new WPI_TalonSRX(eConstants.ELEVATOR_TALON); // Positive = Up
    liftBottom = new DigitalInput(eConstants.LIFT_BOTTOM); // Positive = Up
    led = new Relay(eConstants.LED);
    inst = NetworkTableInstance.getDefault();
    table = inst.getTable("automationToggle");
    isAutoOn = table.getEntry("ballAutomation");


    controls = Controls;
    ball = Ball;
    hatch = Hatch;
    feedFoward = 0.2;
    ledTimer = 0;
    rumbleTimer = 0;
    ledOffTimer = 0;
    autoOn = false;

    liftLeft.follow(liftRight);
    /* first choose the sensor */
    liftRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    liftRight.setSensorPhase(false);
    liftRight.setInverted(false);
    /* Set relevant frame periods to be at least as fast as periodic rate */
    liftRight.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
    liftRight.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);
    /* set the peak and nominal outputs */
    liftRight.configNominalOutputForward(0, Constants.kTimeoutMs);
    liftRight.configNominalOutputReverse(0, Constants.kTimeoutMs);
    liftRight.configPeakOutputForward(1, Constants.kTimeoutMs);
    liftRight.configPeakOutputReverse(-1, Constants.kTimeoutMs);
    /* set closed loop gains in slot0 - see documentation */
    liftRight.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
    // liftRight.config_kF(0, .146, Constants.kTimeoutMs);
    // liftRight.config_kP(0, 2.5, Constants.kTimeoutMs);
    // liftRight.config_kI(0, 0, Constants.kTimeoutMs);
    // liftRight.config_kD(0, 25, Constants.kTimeoutMs);
    /* set acceleration and vcruise velocity - see documentation */
    liftRight.configMotionCruiseVelocity(500, Constants.kTimeoutMs);
    liftRight.configMotionAcceleration(2000, Constants.kTimeoutMs);
    /* zero the sensor */
    liftRight.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    liftRight.configOpenloopRamp(0, 0);
    liftRight.configClosedloopRamp(0, 0);

    // #endregion
    resetEncoder();
  }

  public void resetEncoder() {
    liftRight.setSelectedSensorPosition(0, 0, 10);
  }

  public void setRampRate(double rate) {
    liftRight.configOpenloopRamp(rate, 5);
    liftLeft.configOpenloopRamp(rate, 5);
  }

  public void setBrakeMode(boolean brake) {
    if (brake) {
      liftRight.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
      liftLeft.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
    }
  }

  public void setCoast(boolean coast) {
    if (coast) {
      liftRight.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
      liftLeft.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
    }
  }

  public void update() {
    if (!controls.driver_A_Button() && !controls.driver_X_Button() && !controls.driver_Y_Button() && !controls.driver_UP_DPAD() && !controls.driver_DOWN_DPAD()) {
      if (controls.driver_YR_Axis() > .15 || controls.driver_YR_Axis() < -.15) {
        liftRight.configMotionCruiseVelocity(800, Constants.kTimeoutMs);
        liftPosition = getLiftPosition() - controls.driver_YR_Axis() * 2.5 * Constants.liftEncoderPerInch;
      }else if((getLiftPosition() <= 100)){
        System.out.println("DROPPPPPPPPPPPPPPPPP");
        if(getLiftPosition() <= 10){
          liftRight.configMotionCruiseVelocity(100, Constants.kTimeoutMs);

        }else{
          liftRight.configMotionCruiseVelocity(50, Constants.kTimeoutMs);
        }
        liftPosition = 0;
        if (!liftBottom.get()) {
          resetEncoder();
          liftPosition = 0;
        }      
      }/*if(controls.driver_Se_Button()) {
        autoOn = autoOn ? false : true;
        System.out.println("AutomationToggled");
        if(autoOn) {
          isAutoOn.setString("ENABLED!!!!!!!!!!!!!!!!!!!!!!!!!!");
        } else {
          isAutoOn.setString("");
        }
      }*/ /*if(autoOn) {
          rumbleTimer++;
          if(rumbleTimer >= 30) {
            System.out.println("Rumble Timer: " + rumbleTimer);
            rumbleTimer = 0;
            controls.setRumble(0);
          } else {
            controls.setRumble(0.33);
          }
      } */
      /*if(ball.haveBall() && autoOn && getLiftPosition() < Constants.ballPosition1) {
        liftRight.configMotionCruiseVelocity(800, Constants.kTimeoutMs);
        liftPosition = Constants.ballPosition1;
      }*/ else {
        liftRight.set(0);
        if (!liftBottom.get()) {
          resetEncoder();
          liftPosition = 0;
        }
        // if(autoOn && !ball.haveBall()) {
        //   System.out.println("Toggle LEDs");
        //   led.set(Value.kForward);
        //   if(ledTimer++ >= 100) {
        //     System.out.println("Timer 0");
        //     led.set(Value.kReverse);
        //     if(ledOffTimer++ >= 100) {
        //       ledOffTimer = 0;
        //       ledTimer = 0;
        //     }
        //   }
        // }
        if(ball.haveBall()){
          led.set(Value.kForward);
        }else if(!ball.haveBall()){
          led.set(Value.kReverse);
        }      
     }
    } else if (!ball.haveBall()) {
      led.set(Value.kReverse);
      System.out.println("No ball case");
      if (controls.driver_A_Button()) {
        if (!liftBottom.get()) {
          liftRight.configMotionCruiseVelocity(0, Constants.kTimeoutMs);
        } else if (getLiftPosition() <= Constants.liftEncoderPosition0) {
          liftRight.configMotionCruiseVelocity(0, Constants.kTimeoutMs);
          liftPosition = 0;
        } else {
          liftRight.configMotionCruiseVelocity(132, Constants.kTimeoutMs);
          liftPosition = Constants.liftEncoderPosition0;
        }
        System.out.println("A Pressed Read");
      } else if (controls.driver_X_Button()) {
        liftRight.configMotionCruiseVelocity(800, Constants.kTimeoutMs);
        liftPosition = Constants.hatchPosition2;
        System.out.println("X Pressed Read");
      } else if (controls.driver_Y_Button()) {
        liftRight.configMotionCruiseVelocity(800, Constants.kTimeoutMs);
        liftPosition = Constants.hatchPosition3;
        System.out.println("Y Pressed Read");
      }
    } else if (ball.haveBall()) {
      led.set(Value.kForward);
      System.out.println("Ball Case");
      if (controls.driver_X_Button()) {
        liftRight.configMotionCruiseVelocity(800, Constants.kTimeoutMs);
        liftPosition = Constants.ballPosition1;
        System.out.println("X Pressed Read");
      } else if (controls.driver_Y_Button()) {
        liftRight.configMotionCruiseVelocity(800, Constants.kTimeoutMs);
        liftPosition = Constants.ballPosition2;
        System.out.println("Y Pressed Read");
      } else if(controls.driver_UP_DPAD()){
        liftRight.configMotionCruiseVelocity(800, Constants.kTimeoutMs);
        liftPosition = Constants.ballPositionCargo;
        System.out.println("UP DPAD Pressed Read");
      } else if(controls.driver_DOWN_DPAD()){
        liftRight.configMotionCruiseVelocity(800, Constants.kTimeoutMs);
        liftPosition = Constants.ballPosition3;
        System.out.println("DOWN DPAD Pressed Read");
      }else if (controls.driver_A_Button()) {
        if (!liftBottom.get()) {
          liftRight.configMotionCruiseVelocity(0, Constants.kTimeoutMs);
        } else if (getLiftPosition() <= Constants.liftEncoderPosition0) {
          liftRight.configMotionCruiseVelocity(0, Constants.kTimeoutMs);
          liftPosition = 0;
        } else {
          liftRight.configMotionCruiseVelocity(132, Constants.kTimeoutMs);
          liftPosition = Constants.liftEncoderPosition0;
        }
        System.out.println("A Pressed Read");
      }
    } else if (!liftBottom.get()) {
      resetEncoder();
      liftPosition = 0;
    }
    if(liftPosition == 0){
      liftRight.set(ControlMode.MotionMagic, liftPosition);
    }else{
      liftRight.set(ControlMode.MotionMagic, liftPosition, DemandType.ArbitraryFeedForward, feedFoward);
    }
    
  }

  public double getLiftPosition() {
    return liftRight.getSelectedSensorPosition();
  }

  public boolean liftDown() {
    return liftBottom.get(); //< for the practice bot
  }

  public boolean getSensor() {
    return liftBottom.get();
  }

  // public void setElevator(double ticks)
  // {
  // double error = ticks - this.getLiftPosition();
  // //double deltaError;

  // }

}
  