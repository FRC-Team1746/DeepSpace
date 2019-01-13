package frc.robot.commands.driveTrain;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import team1746.common.DriveTrain;
import team1746.common.transforms.ITransform;

/**
 *
 */
public class ArcadeDrive extends Command {

	private final int THROTTLE_AXIS;
	private final int TURN_AXIS;
	private final int SLOW_BUTTON;
	private final int FAST_BUTTON;
	private final double DEADBAND = 0.1;

	private DriveTrain driveTrain;
	private Joystick driverJoystick;
	private final ITransform squaredTransform;
	private final ITransform slowTransform;

	public ArcadeDrive(DriveTrain driveTrain, ITransform squaredTransform, Joystick driverJoystick,  int throttleId, int turnId, int slowId, int fastId, ITransform slowTransform) {

		this.THROTTLE_AXIS = throttleId;
		this.TURN_AXIS = turnId;
		this.SLOW_BUTTON = slowId;
		this.FAST_BUTTON = fastId;
		this.driveTrain = driveTrain;
		this.driverJoystick = driverJoystick;
		this.squaredTransform = squaredTransform;
		this.slowTransform = slowTransform;

		requires(this.driveTrain);

	}

	// Called just before this Command runs the first time
	protected void initialize() {

//		driveTrain.defaultDirection();

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		double driverJoystickThrottleAxis = driverJoystick.getRawAxis(THROTTLE_AXIS);
		double driverJoystickTurnAxis = driverJoystick.getRawAxis(TURN_AXIS);

		double throttlePower = -driverJoystickThrottleAxis; 
		double turnPower = driverJoystickTurnAxis;
		
		if (driverJoystick.getRawAxis(SLOW_BUTTON) > 0.25) {

			throttlePower = slowTransform.transform(throttlePower);
			turnPower = slowTransform.transform(turnPower);
		}

		if (!(driverJoystick.getRawAxis(FAST_BUTTON) > 0.25)) {

			throttlePower = 0.8*throttlePower;
			turnPower = 0.8*turnPower;

		}

		driveTrain.arcadeDrive(throttlePower, turnPower, DEADBAND, squaredTransform);

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		driveTrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
