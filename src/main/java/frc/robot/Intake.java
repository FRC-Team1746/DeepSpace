package frc.robot;

// import edu.wpi.first.wpilibj.DigitalInput;
// import com.ctre.phoenix.motorcontrol.can.*;

public class Intake{

	ElectricalConstants eConstants;
	Controls controls;
  Constants constants;
  Hatch hatch;
  Ball ball;
  Lift lift;

  public Intake(Controls Controls){
    controls = Controls;
    hatch = new Hatch();
    ball = new Ball();
    lift = new Lift(controls);
  }

  public void update(){
    
  }
}
