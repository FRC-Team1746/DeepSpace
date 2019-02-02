package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Hatch{

  ElectricalConstants eConstants;

  private DoubleSolenoid hatchenoid1;
  private DoubleSolenoid hatchenoid2;
  
  private DigitalInput hatchs;
 
 public Hatch(){
  eConstants = new ElectricalConstants();
  hatchs = new DigitalInput(eConstants.HATCH);
  DoubleSolenoid hatchenoid1 = new DoubleSolenoid(eConstants.HATCHENOID11, eConstants.HATCHENOID12 );
  DoubleSolenoid hatchenoid2 = new DoubleSolenoid(eConstants.HATCHENOID21, eConstants.HATCHENOID22 );

  hatchenoid1.set(DoubleSolenoid.Value.kOff);
  hatchenoid2.set(DoubleSolenoid.Value.kOff);

 }

 public void pivotUp(){
  hatchenoid1.set(DoubleSolenoid.Value.kForward);
 }

 public void pivotDown(){
  hatchenoid1.set(DoubleSolenoid.Value.kReverse);
 }

 public void rivetOut(DoubleSolenoid hatchenoid2){
   hatchenoid2.set(DoubleSolenoid.Value.kForward);
 }

 public void rivetIn(DoubleSolenoid hatchenoid2){
  hatchenoid2.set(DoubleSolenoid.Value.kReverse);
 }

 public boolean getSensor(){
   return hatchs.get();
 }
}