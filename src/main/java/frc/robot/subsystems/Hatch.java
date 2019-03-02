package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.constants.ElectricalConstants;;

public class Hatch{

  ElectricalConstants eConstants;

  private DoubleSolenoid hatchenoid1;
  private DoubleSolenoid hatchenoid2;
  
  private DigitalInput hatch1;
  private DigitalInput hatch2
 
 public Hatch(){
  eConstants = new ElectricalConstants();
  hatch1 = new DigitalInput(eConstants.HATCH);
  hatchenoid1 = new DoubleSolenoid(eConstants.HATCHENOID11, eConstants.HATCHENOID12 );
  hatchenoid2 = new DoubleSolenoid(eConstants.HATCHENOID21, eConstants.HATCHENOID22 );

  hatchenoid1.set(DoubleSolenoid.Value.kOff);
  hatchenoid2.set(DoubleSolenoid.Value.kOff);

 }

 public void pivotUp(){
  hatchenoid1.set(DoubleSolenoid.Value.kForward);
 }

 public void pivotDown(){
  hatchenoid1.set(DoubleSolenoid.Value.kReverse);
 }

 public void rivetOut(){
   hatchenoid2.set(DoubleSolenoid.Value.kForward);
 }

 public void rivetIn(){
  hatchenoid2.set(DoubleSolenoid.Value.kReverse);
 }

 public boolean getSensor(){
   return hatch1.get();
 }
}