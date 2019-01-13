package frc.robot.commands.driveTrain;

import edu.wpi.first.wpilibj.command.Command;
import team1746.common.DriveTrain;

/**
 *
 */
public class TurnToAngle extends Command {

	private DriveTrain driveTrain;
	private double kP = 0.00555;
	private double turnAngle;
	private double currentAngle;
	private double startingAngle = 0;
	private double turnPower = -.4;
	private double angleCompensation = 12.5;
	private double angleError;
	
	private double slowDown = 15;
	private double cutoff = 25;

	// This command will turn to an angle +/-180 degress.
	// Negative degress turn counter clockwise
	// Positive degrees turn clockwise.

	//  All drivetrain angles are offest 180 degress, so crossing the 
	//  360/0 degree mark doesn't mess up math.  This offset is the reason
	//  turns can't be 180 or more degrees.

	//  When this command is ran, it's assumed the bot is at rest.
	//  Since this command might start executing while the bot is coasting
	//  from a previous command, we need to handle the case were, after zeroing 
	//  the drivetrain angle, the bot turns left changing our angle in the 
	//  350 range, or drifting too far to the fight. If we drift left, we'll
	//  add 180 to a bearing for 359.  If we drift 3 degrees right, we'll
	//  add 180 to 3.   So any starting angle of >183 is assumed to have drifted
	//  after zero.  So keep trying to re-zero until we get an drivetrain angle < 3.

	public TurnToAngle(DriveTrain driveTrain, double turnAngle) {

		this.turnAngle = turnAngle;
		if (this.turnAngle < 0)
			this.turnPower *= -1;
	
		this.driveTrain = driveTrain;
		this.angleError = turnAngle - currentAngle;
		requires(this.driveTrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {    
		driveTrain.zeroAngle();
		startingAngle = driveTrain.getAngle() + 180;

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		// Robot is coasting to the left or far right, re-zero. 
		if (startingAngle > 183)
		{
			driveTrain.zeroAngle();
			startingAngle = driveTrain.getAngle() + 180;
			return;
		}

		// Starting angle is good, start turning.
		currentAngle = driveTrain.getAngle() + 180;

		/*
    	double power = kP * (angle - driveTrain.getAngle());
    	System.out.println("Drivetrain angle:" + driveTrain.getAngle());
    	System.out.println("Angle error:" + (angle - driveTrain.getAngle()));
    	System.out.println(power);
    	System.out.println("LeftPower:" + driveTrain.getLeftPercentOutput() + "RightPower: " + driveTrain.getRightPercentOutput());

    	if (power <= 0.35) {
    		power = 0.35;

    	}
		 */

		/*if (angleError < slowDown) {
			turnPower = .3;
		}*/
	

	driveTrain.setLeftRightDrivePower(-turnPower, turnPower);

}

// Make this return true when this Command no longer needs to run execute()
protected boolean isFinished() {
	//return (Math.abs(angle - driveTrain.getAngle()) < tolerance)

	boolean isFinished = (Math.abs(currentAngle - startingAngle) >= Math.abs(turnAngle) - cutoff);
	boolean isCoasting = (startingAngle > 183);

	/*System.out.println("------------------------------------");
	System.out.println("Target Angle: " + turnAngle);
	System.out.println("Current Angle:" + currentAngle);
	System.out.println("Starting angle:" + startingAngle);
	System.out.println("isFinished: " + isFinished );
	System.out.println("isCoasting:" + isCoasting);*/
	return isFinished && !isCoasting;
}

// Called once after isFinished returns true
protected void end() {
	driveTrain.stop();
}

// Called when another command which requires one or more of the same
// subsystems is scheduled to run
protected void interrupted() {
}
}