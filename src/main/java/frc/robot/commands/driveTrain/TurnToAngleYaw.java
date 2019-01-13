package frc.robot.commands.driveTrain;

import edu.wpi.first.wpilibj.command.Command;
import team1746.common.DriveTrain;

/**
 *
 */
public class TurnToAngleYaw extends Command {

	private DriveTrain driveTrain;
	private double angleTarget;
	private double currentAngle;
	private double turnPower = -0.4;
	private double tolerance = 5;
//TODO: FIX OVERSHOOT
	public TurnToAngleYaw(DriveTrain driveTrain, double angleTarget) {

		System.out.println("turn init");
		this.angleTarget = angleTarget;
		if (this.angleTarget < 0) {
			this.turnPower *= -1;
		}

		this.driveTrain = driveTrain;

		requires(this.driveTrain);
        
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		driveTrain.zeroAngle();
		this.currentAngle = 0;


		driveTrain.setLeftRightDrivePower(-turnPower, turnPower);
		//System.out.println(angleTarget);
		//System.out.println(currentAngle);
		//System.out.println(driveTrain.getYaw());
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		currentAngle = driveTrain.getYaw();
		//System.out.println("execute : " + currentAngle);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		boolean isFinished = ((Math.abs(angleTarget) - Math.abs(currentAngle)) < tolerance);
		/*System.out.println(Math.abs(angleTarget));
		System.out.println(Math.abs(currentAngle));
		System.out.println(isFinished);*/
		return isFinished;
	}

	// Called once after isFinished returns true
	protected void end() {
		System.out.println("TURN END");
		driveTrain.stop();
		driveTrain.zeroAngle();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
