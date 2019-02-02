package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Hatch{

  ElectricalConstants eConstants;
  
  private DigitalInput hatchs;
 
 public Hatch(){
  eConstants = new ElectricalConstants();
  hatchs = new DigitalInput(eConstants.HATCH);
  DoubleSolenoid hatcheniod1 = new DoubleSolenoid(eConstants.HATCHENIOD11, eConstants.HATCHENIOD12 );
  DoubleSolenoid hatcheniod2 = new DoubleSolenoid(eConstants.HATCHENIOD21, eConstants.HATCHENIOD22 );

  hatcheniod1.set(DoubleSolenoid.Value.kOff);
  hatcheniod2.set(DoubleSolenoid.Value.kOff);

 }

 public void pivotUp(DoubleSolenoid hatcheniod1){
  hatcheniod1.set(DoubleSolenoid.Value.kForward);
 }

 public void pivotDown(DoubleSolenoid hatcheniod1){
  hatcheniod1.set(DoubleSolenoid.Value.kReverse);
 }

 public void rivetOut(DoubleSolenoid hatcheniod2){
   hatcheniod2.set(DoubleSolenoid.Value.kForward);
 }

 public void rivetIn(DoubleSolenoid hatcheniod2){
  hatcheniod2.set(DoubleSolenoid.Value.kReverse);
 }

 public boolean getSensor(){
   return hatchs.get();
 }
}