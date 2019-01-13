package team1746.common;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import team1746.common.transforms.ITransform;

/**
 *
 */
public class DriveTrain extends Subsystem {

	private Command defaultCommand;

	private WPI_TalonSRX leftMaster;
	private WPI_VictorSPX leftFollower;
	private WPI_TalonSRX rightMaster;
	private WPI_VictorSPX rightFollower;

	private AHRS navX;

	private AnalogInput pressureSensor;

	private boolean defaultDirection;

	public DriveTrain(int leftMasterCanId, int leftFollowerCanId, int rightMasterCanId, int rightFollowerCanId, int pressureSensorID) {

		leftMaster = new WPI_TalonSRX(leftMasterCanId);
		leftFollower = new WPI_VictorSPX(leftFollowerCanId);
		rightMaster = new WPI_TalonSRX(rightMasterCanId);
		rightFollower = new WPI_VictorSPX(rightFollowerCanId);

		pressureSensor = new AnalogInput(pressureSensorID);
		
		leftFollower.follow(leftMaster);
		rightFollower.follow(rightMaster);

		leftMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 5, 10);
		rightMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 5, 10);

		navX = new AHRS(SPI.Port.kMXP.kMXP, (byte)200);

	}

	public void setCommandDefault(Command defaultCommand) {
		this.defaultCommand = defaultCommand;
		initDefaultCommand();
	}

	public void initDefaultCommand() {
		setDefaultCommand(this.defaultCommand);
	}

	public void defaultDirection() {
		leftMaster.setSensorPhase(false);
		rightMaster.setSensorPhase(false);
		
		leftMaster.setInverted(false);
		leftFollower.setInverted(false);
		rightMaster.setInverted(true);
		rightFollower.setInverted(true);

		defaultDirection = true;
	}

	public void reverseDirection() {
		leftMaster.setSensorPhase(true);
		rightMaster.setSensorPhase(true);

		leftMaster.setInverted(true);
		leftFollower.setInverted(true);
		rightMaster.setInverted(false);
		rightFollower.setInverted(false);

		defaultDirection = false;
	}

	public void arcadeDrive(double throttlePower, double turnPower, double deadband, ITransform transform) {
		double leftMotorOutput;
		double rightMotorOutput;

		throttlePower = transform.transform(throttlePower);
		turnPower = transform.transform(turnPower);

		double maxInput = Math.copySign(Math.max(Math.abs(throttlePower), Math.abs(turnPower)), throttlePower);

		if (throttlePower >= 0.0) {
			// First quadrant, else second quadrant
			if (turnPower >= 0.0) {
				leftMotorOutput = maxInput;
				rightMotorOutput = throttlePower - turnPower;
			} else {
				leftMotorOutput = throttlePower + turnPower;
				rightMotorOutput = maxInput;
			}
		} else {
			// Third quadrant, else fourth quadrant
			if (turnPower >= 0.0) {
				leftMotorOutput = throttlePower + turnPower;
				rightMotorOutput = maxInput;
			} else {
				leftMotorOutput = maxInput;
				rightMotorOutput = throttlePower - turnPower;
			}
		}

		throttlePower = limit(throttlePower);
		throttlePower = applyDeadband(throttlePower, deadband); // set throttle deadband here

		turnPower = limit(turnPower);
		turnPower = applyDeadband(turnPower, deadband); // set rotate deadband here

		leftMaster.set(ControlMode.PercentOutput, leftMotorOutput);
		rightMaster.set(ControlMode.PercentOutput, rightMotorOutput);

	}

	public void setLeftDrivePower(double power) {
		leftMaster.set(ControlMode.PercentOutput, limit(power));
	}

	public void setRightDrivePower(double power) {
		rightMaster.set(ControlMode.PercentOutput, limit(power));
	}

	public void setLeftRightDrivePower(double leftPower, double rightPower) {
		//System.out.println("Drivetrain left = " + leftPower);
		//System.out.println("Drivetrain right = " + rightPower);
		setLeftDrivePower(leftPower);
		setRightDrivePower(rightPower);
	}

	public int getLeftEncoderPosition() {

		if (defaultDirection) {
			return -leftMaster.getSensorCollection().getQuadraturePosition();

		}
		
		return leftMaster.getSensorCollection().getQuadraturePosition();

	}

	public int getRightEncoderPosition() {
		
		if (defaultDirection) {
			return rightMaster.getSensorCollection().getQuadraturePosition();

		}
		
		return -rightMaster.getSensorCollection().getQuadraturePosition();
	}

	public int getLeftEncoderVelocity() {
		return -leftMaster.getSensorCollection().getQuadratureVelocity();
	}

	public int getRightEncoderVelocity() {
		return rightMaster.getSensorCollection().getQuadratureVelocity();
	}

	public double getLeftVoltage() {
		return leftMaster.getMotorOutputVoltage();
	}

	public double getRightVoltage() {
		return rightMaster.getMotorOutputVoltage();
	}

	public double getLeftPercentOutput() {
		return leftMaster.getMotorOutputPercent();
	}

	public double getRightPercentOutput() {
		return rightMaster.getMotorOutputPercent();
	}

	public double getPressure() {

		int pressure = (int)(250*pressureSensor.getVoltage()/5.0-25);
		return pressure;
		
	}

	public void stop() {

		leftMaster.stopMotor();
		rightMaster.stopMotor();

	}

	public TalonSRX getRightMaster() {
		return rightMaster;
	}

	public TalonSRX getLeftMaster() {
		return leftMaster;
	}

	public void zeroEncoders() {
		rightMaster.getSensorCollection().setQuadraturePosition(0, 10);
		leftMaster.getSensorCollection().setQuadraturePosition(0, 10);

	}

	public double getAngle() {
		return navX.getAngle();
	}

	public double getYaw() {
		return navX.getYaw();
	}

	public void zeroAngle() {
		navX.reset();
	}

	public boolean isCalibrating() {
		return navX.isCalibrating();
	}

	public boolean isConnected() {
		return navX.isConnected();
	}

	private double limit(double value) {
		if (value > 1.0) {
			return 1.0;
		}
		if (value < -1.0) {
			return -1.0;
		}
		return value;
	}

	private double applyDeadband(double value, double deadband) {
		if (Math.abs(value) > deadband) {
			if (value > 0.0) {
				return (value - deadband) / (1.0 - deadband);
			} else {
				return (value + deadband) / (1.0 - deadband);
			}
		} else {
			return 0.0;
		}
	}

}
