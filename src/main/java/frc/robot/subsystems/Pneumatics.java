package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import frc.robot.constants.ElectricalConstants;

public class Pneumatics{

  ElectricalConstants eConstants;
  Compressor sensor;
  Relay power;

  public Pneumatics(){
    sensor = new Compressor();
    power = new Relay(0);
  }

  public void update(){
    // System.out.println(sensor.getPressureSwitchValue());
    if(!sensor.getPressureSwitchValue()){
      power.set(Value.kForward);
    }
    else{
      power.set(Value.kOff);
    }
  }
}
